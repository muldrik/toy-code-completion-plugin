package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

//The kotlin way would be to create an object, however this is required by plugin.xml
class TxtcFileType private constructor() : LanguageFileType(TxtcLanguage.INSTANCE) {
    override fun getName(): String {
        return "Txtc File"
    }

    override fun getDescription(): String {
        return "Txtc language file"
    }

    override fun getDefaultExtension(): String {
        return "txtc"
    }

    override fun getIcon(): Icon {
        return TxtcIcons.FILE
    }

    companion object {
        val INSTANCE = TxtcFileType()
    }
}