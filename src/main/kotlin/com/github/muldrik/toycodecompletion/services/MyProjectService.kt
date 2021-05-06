package com.github.muldrik.toycodecompletion.services

import com.github.muldrik.toycodecompletion.completion.MyDictionary
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    /**
     * Locate dictionary file and call MyDictionary.load() on it
     */
    private fun loadDictionary() {
        val current = Thread.currentThread().contextClassLoader //Load dictionary file
        try {
            Thread.currentThread().contextClassLoader = this.javaClass.classLoader
            val path = this.javaClass.getResourceAsStream("/dictionary.txt")
            MyDictionary.loadWords(path)
        } finally {
            Thread.currentThread().contextClassLoader = current
        }
    }

    init {
        loadDictionary()
    }
}
