package com.github.muldrik.toycodecompletion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.fileTypes.PlainTextLikeFileType
import com.intellij.openapi.fileTypes.PlainTextParserDefinition
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PlainTextTokenTypes
import com.intellij.psi.tree.IElementType

class Contributor : CompletionContributor() {
    init {
        val kek = IElementType("SIMPLE", SimpleLanguage.INSTANCE);
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(PlainTextTokenTypes.PLAIN_TEXT),
            Provider(false)
        )
    }
}