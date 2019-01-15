package com.chris.gradle.json.io

import org.apache.commons.io.FileUtils

import java.nio.charset.StandardCharsets

class FileWriter {

    private final File outputDirectory

    FileWriter(File outputDir) {
        if (!outputDir.exists()){
            outputDir.mkdirs()
        } else if (!outputDir.isDirectory()){
            outputDirectory=null
            throw new RuntimeException("Output directory file already exists, but is not a directory")
        }
        outputDirectory = outputDir
    }

    def writeJson(String filename, String json) {
        File file = new File(outputDirectory.path + "/" + filename)
        FileUtils.writeStringToFile(file, json, StandardCharsets.UTF_8)
    }
}
