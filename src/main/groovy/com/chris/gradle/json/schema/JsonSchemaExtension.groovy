package com.chris.gradle.json.schema

import org.gradle.api.Project
import org.gradle.api.internal.artifacts.configurations.DefaultConfiguration

class JsonSchemaExtension {
    private Project project

    private DefaultConfiguration compileClasspath
    private boolean pretty = false
    private List<String> exclude = new ArrayList<>()
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

    List<String> getExclude() {
        return exclude
    }

    void setExclude(String... exclude) {
        this.exclude.addAll(exclude)
    }

    void exclude(String... exclude) {
        this.exclude.addAll(exclude)
    }

    String getInclude() {
        return include
    }

    void setInclude(String include) {
        this.include = include
    }
}