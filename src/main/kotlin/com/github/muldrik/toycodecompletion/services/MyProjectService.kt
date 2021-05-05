package com.github.muldrik.toycodecompletion.services

import com.github.muldrik.toycodecompletion.MyBundle
import com.github.muldrik.toycodecompletion.MyDictionary
import com.intellij.openapi.project.Project
import java.io.File

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
        println("i'm kekking")

    }
}
