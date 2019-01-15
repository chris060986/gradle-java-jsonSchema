package com.chris.gradle.json.schema

import com.chris.gradle.json.io.FileWriter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.jsonSchema.JsonSchema
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator
import org.apache.commons.lang3.StringUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.bundling.Zip

class JsonSchemaPlugin implements Plugin<Project> {

    void apply(Project project) {
        JsonSchemaExtension extension = project.getExtensions().create("jsonSchema", JsonSchemaExtension.class, project)

        Task task = project.task('generateJsonSchema') {
            def generatedFileDir = project.file(project.buildDir.path + "/json/schemas")
            outputs.dir generatedFileDir

            doLast {

                FileTree classFileTree = getProject().getTasks().getByName("compileJava").getOutputs().files.asFileTree
                Set<String> filesToGenerate = new HashSet<String>()

                Set<File> classFiles = classFileTree.matching {
                    exclude extension.exclude
                    include extension.include
                }.files
                println "Files included in generation"
                println classFiles

                classFiles.forEach {
                    file -> filesToGenerate.add(parseJavaClassNameFromClassFile(project, file))
                }

                Set<File> compileClasspath = extension.compileClasspath.getFiles()
                Set<File> jars = getProject().getTasks().getByName("jar").getOutputs().files.asFileTree.files

                Set<File> mergedClasspathFiles = new HashSet<>()
                mergedClasspathFiles.addAll(jars)
                mergedClasspathFiles.addAll(compileClasspath)

                ObjectMapper mapper = new ObjectMapper()
                JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper)

                def classLoader = new URLClassLoader(mergedClasspathFiles.collect {
                    it.toURI().toURL()
                } as URL[], Thread.currentThread().getContextClassLoader())

                try {
                    FileWriter writer = new FileWriter(generatedFileDir)

                    filesToGenerate.parallelStream().forEach {
                        fileName ->
                            def clazz = classLoader.loadClass(fileName)
                            JsonSchema schema = jsonSchemaGenerator.generateSchema(clazz)
                            String jsonString
                            if(extension.pretty){
                                jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema)
                            } else {
                                jsonString = mapper.writeValueAsString(schema)
                            }
                            writer.writeJson(getJsonFileName(fileName), jsonString)
                    }

                } finally {
                    classLoader.close()
                }

            }
        }
        task.setGroup("json")
        task.dependsOn project.getTasks().getByName("jar")


        Zip schemaZip = project.task('jsonSchemaZip', type:Zip) {
            group = 'json'
            description = 'Bundles the json schema files in a zip file'
            archiveName = project.name + "-" + project.version + "-jsonSchemas.zip"
            destinationDir = project.file(project.buildDir.path + "/libs")

            from (task){
                include "**/*.json"
            }
        }
        schemaZip.dependsOn task
        schemaZip.mustRunAfter task
    }

    private static String parseJavaClassNameFromClassFile(Project project, File classFile){

        String name = classFile.absolutePath
        name = StringUtils.removeStart(name, project.buildDir.path + "/classes/java/main/")
        name = StringUtils.removeEnd(name,".class" )
        name = StringUtils.replace(name, "/", ".")
        return name
    }

    private static String getJsonFileName(String qualifiedName){
        return StringUtils.substringAfterLast(qualifiedName, ".") + ".json"
    }
}
