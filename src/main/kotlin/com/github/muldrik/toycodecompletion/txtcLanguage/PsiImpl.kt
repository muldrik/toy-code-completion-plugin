package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.psi.PlainTextTokenTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.tree.OwnBufferLeafPsiElement

class PsiImpl (text: CharSequence) :
    OwnBufferLeafPsiElement(TxtcTokenTypes.TXTC_TEXT, text), PsiElement {
    override fun accept(visitor: PsiElementVisitor) {
        visitor.visitElement(this)
    }

    override fun toString(): String {
        return "PsiTxtc"
    }
}