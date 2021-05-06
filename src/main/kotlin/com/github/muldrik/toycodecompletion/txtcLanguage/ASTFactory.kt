package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.codeInsight.completion.PlainPrefixMatcher
import com.intellij.lang.ASTFactory
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.tree.IElementType
import com.intellij.spellchecker.inspections.PlainTextSplitter

class TxtcASTFactory : ASTFactory() {
    override fun createLeaf(type: IElementType, text: CharSequence): LeafElement? {
        println("MyLeaf")
        return if (type == TxtcTokenTypes.TXTC_TEXT) {
            PsiImpl(text)
        } else null
    }
}