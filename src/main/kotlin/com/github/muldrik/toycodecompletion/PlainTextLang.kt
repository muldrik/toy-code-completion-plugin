package com.github.muldrik.toycodecompletion

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon
import com.intellij.openapi.util.IconLoader




object SimpleLanguage : Language("Simple")

object SimpleIcons {
    val FILE = IconLoader.getIcon("/icons/ileasile3.png")
}

class SimpleFileType private constructor() : LanguageFileType(SimpleLanguage) {
    override fun getName(): String {
        return "Simple File"
    }

    override fun getDescription(): String {
        return "Simple language file"
    }

    override fun getDefaultExtension(): String {
        return "simple"
    }

    override fun getIcon(): Icon {
        return SimpleIcons.FILE
    }

    companion object {
        val INSTANCE = SimpleFileType()
    }
}