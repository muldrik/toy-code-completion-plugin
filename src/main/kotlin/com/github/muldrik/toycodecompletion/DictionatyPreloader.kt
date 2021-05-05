package com.github.muldrik.toycodecompletion

import com.intellij.openapi.progress.ProgressIndicator

import com.intellij.openapi.application.PreloadingActivity


class MyPreloadingActivity : PreloadingActivity() {
    override fun preload(indicator: ProgressIndicator) {
        val current = Thread.currentThread().contextClassLoader
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
}