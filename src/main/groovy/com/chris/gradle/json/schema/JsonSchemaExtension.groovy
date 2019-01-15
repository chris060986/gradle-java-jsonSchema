package com.chris.gradle.json.schema

import org.gradle.api.Project
import org.gradle.api.internal.artifacts.configurations.DefaultConfiguration

class JsonSchemaExtension {
    private Project project

    private DefaultConfiguration compileClasspath
    private boolean pretty = false
    private String exclude = ""
    private String include = "**"

    JsonSchemaExtension(Project project) {
        this.project = project
    }

    DefaultConfiguration getCompileClasspath() {
        return compileClasspath
    }

    void setCompileClasspath(DefaultConfiguration compileClasspath) {
        this.compileClasspath = compileClasspath
    }

    boolean getPretty() {
        return pretty
    }

    void setPretty(boolean pretty) {
        this.pretty = pretty
    }

    String getExclude() {
        return exclude
    }

    void setExclude(String exclude) {
        this.exclude = exclude
    }

    String getInclude() {
        return include
    }

    void setInclude(String include) {
        this.include = include
    }
}