# gradle-java-jsonSchema
[![Build Status](https://travis-ci.org/chris060986/gradle-java-jsonSchema.svg?branch=master)](https://travis-ci.org/chris060986/gradle-java-jsonSchema)

Gradle plugin that allows to generate json schema from java classes and build a zip file containing all the schemas. For all classes which are compiled to _buildDir/java/classes/main_ a json schema will be generated. Plugin allows to include/exclude class files. See plugin parameters.

## How to use

### Build
At the moment the plugin is not uploaded to any public repository. That means you have to build it yourself. Just run 
```
$ ./gradlew build
```
to generate the jar or 
```
$ ./gradlew publishToMavenLocal
```
to push the jar to your local maven repository.

### Add to your project
The project setup should look like a normal java project. That means _src/main/java_ contains the java files which you want to use to build json schemas and all dependecies needed to compile are specified in the gradle build file.  
After you have pushed the plugin to your repository, update the build.gradle. First add the dependency to buildscript section and apply the plugin:
```Gradle
buildscript {

    repositories {
        mavenLocal()
        jcenter()
        maven {
            url 'https://plugins.gradle.org/m2/'
        }
    }
    dependencies {
        classpath 'com.chris.gradle.json:gradle-java-jsonSchema:0.1.6'
    }
}
apply plugin: 'java'
apply plugin: 'com.chris.json.schema'
```
It's necessary to apply the java plugin, because this plugin depends on java build tasks to compile class files from java code.

### Configure plugin
The plugin has some simple configuration mechanism to control the generated json schema. 
```Gradle
jsonSchema {
    pretty true
    include '**/*Seq.class', '**/*List.class'
    exclude '**/*Support.class', '**/*Communication*'
}
```
#### Plugin Parameters  
**compileClasspath**: The configuration where gradle-java-jsonSchema-plugin can find the jars needed to compile java files. In a normal java project it should set to _sourceSets.main.compileClasspath_.  
**pretty**: Defines if the json schemas should be generated in compact or in human readable way. Default is false (compact).  
**exclude**: Exclude class files from schema generation.  
**include**: Include class files in schema generation.


### Generate schemas or zip-file
The plugin contains two tasks. _generateJsonSchema_ to just generate the schema files and _jsonSchemaZip_ to build a zip file containing all the schemas. Of course _jsonSchemaZip_ depends on _generateJsonSchema_.
Execute the tasks like:
```Shell
$ ./gradlew generateJsonSchema
# or
$ ./gradlew jsonSchemaZip
```

