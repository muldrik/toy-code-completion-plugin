package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

object TxtcTokenTypes {
    val TXTC_TEXT_FILE: IElementType = object : IFileElementType("TXTC_FILE", TxtcLanguage) {
        override fun parseContents(chameleon: ASTNode): ASTNode {
            return ASTFactory.leaf(TXTC_TEXT, chameleon.chars)
        }
    }
    val TXTC_TEXT = IElementType("TXTC_TEXT", TxtcLanguage)
}