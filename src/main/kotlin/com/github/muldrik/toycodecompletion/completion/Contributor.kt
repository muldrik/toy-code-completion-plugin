package com.github.muldrik.toycodecompletion.completion

import com.github.muldrik.toycodecompletion.txtcLanguage.TxtcTokenTypes
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns


/**
 * Completion Contributor for Txtc language
 */
class Contributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(TxtcTokenTypes.TXTC_TEXT),
            Provider()
        )

    }
}