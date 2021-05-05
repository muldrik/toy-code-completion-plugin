package com.github.muldrik.toycodecompletion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.openapi.application.PreloadingActivity
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PlainTextTokenTypes



/**
 * Preloading activity to load the dictionary at startup.
 *
 * @author jansorg
 */


class Contributor : CompletionContributor() {
    init {
        // completions for plain text files
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(PlainTextTokenTypes.PLAIN_TEXT),
            Provider(false)
        )
    }
}