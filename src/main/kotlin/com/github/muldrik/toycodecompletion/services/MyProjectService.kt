package com.github.muldrik.toycodecompletion.services

import com.github.muldrik.toycodecompletion.MyDictionary
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {
    private fun loadDictionary() {
        val current = Thread.currentThread().contextClassLoader //Load dictionary file
        try {
            Thread.currentThread().contextClassLoader = this.javaClass.classLoader
            println(current.definedPackages)
            val path = this.javaClass.getResourceAsStream("/dictionary.txt")
            MyDictionary.loadWords(path)
            println("not dead")
        } finally {
            Thread.currentThread().contextClassLoader = current
        }
    }

    init {
        loadDictionary()
    }
}
