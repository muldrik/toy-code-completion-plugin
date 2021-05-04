package com.github.muldrik.toycodecompletionplugin.services

import com.github.muldrik.toycodecompletionplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
