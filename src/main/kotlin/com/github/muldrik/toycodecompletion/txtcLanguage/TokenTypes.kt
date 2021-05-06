package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.psi.impl.source.tree.PlainTextASTFactory
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType



object TxtcTokenTypes {
    val TXTC_TEXT_FILE: IElementType = object : IFileElementType("TXTC_FILE", TxtcLanguage) {
        override fun parseContents(chameleon: ASTNode): ASTNode {
            println("C")
            println(chameleon.chars)
            val res = ASTFactory.leaf(TXTC_TEXT, chameleon.chars)
            println(res.chars)
            return res
        }
    }
    val TXTC_TEXT = IElementType("TXTC_TEXT", TxtcLanguage)
}