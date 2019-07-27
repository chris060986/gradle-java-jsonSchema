package com.chris.gradle.json.schema

import org.gradle.api.Project

class JsonSchemaExtension {
    private Project project

    private Set<File> compileClasspath = []
    private boolean pretty = false
    private List<String> exclude = []
    private List<String> include = []

    JsonSchemaExtension(Project project) {
        this.project = project
    }

    Set<File> getCompileClasspath() {
        return compileClasspath
    }

    void setCompileClasspath(Set<File> compileClasspath) {
        this.compileClasspath = compileClasspath
    }

    void compileClasspath(Collection<File> files) {
        this.compileClasspath.addAll(files)
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

    void setExclude(List<String> exclude) {
        this.exclude = exclude
    }

    void exclude(String... exclude) {
        this.exclude.addAll(exclude)
    }

    List<String> getInclude() {
        return include
    }

    void setInclude(List<String> include) {
        this.include = include
    }

    void include(String... include) {
        this.include.addAll(include)
    }
}