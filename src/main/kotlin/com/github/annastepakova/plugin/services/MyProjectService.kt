package com.github.annastepakova.plugin.services

import com.intellij.openapi.project.Project
import com.github.annastepakova.plugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
