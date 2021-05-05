package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object TxtcFileType : LanguageFileType(TxtcLanguage) {
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

    /*companion object {
        val INSTANCE = TxtcFileType()
    }*/
}