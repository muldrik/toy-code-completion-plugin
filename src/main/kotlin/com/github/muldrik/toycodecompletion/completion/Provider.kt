package com.github.muldrik.toycodecompletion.completion

import com.github.muldrik.toycodecompletion.completion.MyDictionary.filterByPrefix
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.LookupElementBuilder

import com.intellij.openapi.progress.ProgressManager





internal class Provider(private val onlyManual: Boolean) :
    CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {


        // return early if we're not supposed to show items
        // in the automatic popup
        if (parameters.isAutoPopup && onlyManual) {
            return
        }

        // return early when there's not prefix and completion was not called manually
        var prefix = result.prefixMatcher.prefix
        if (prefix.isEmpty() && parameters.isAutoPopup) {
            return
        }

        // make sure that our prefix is the last word
        // (In plain text files, the prefix initially contains the whole
        // file up to the cursor. We don't want that, as we're only
        // completing a single word.)
        val dictResult: CompletionResultSet
        val lastSpace = prefix.lastIndexOf(' ')
        println(prefix)
        println(lastSpace)
        if (lastSpace >= 0 && lastSpace < prefix.length - 1) {
            prefix = prefix.substring(lastSpace + 1)
            dictResult = result.withPrefixMatcher(prefix)
        } else {
            dictResult = result
        }
        if (prefix == "and/or") {
            println(prefix)
        }

        val words = filterByPrefix(prefix.toLowerCase(), 30)
        dictResult.restartCompletionOnAnyPrefixChange()
        val total = words.sumByDouble { it.count.toDouble() }
        for (entry in words) {
            ProgressManager.checkCanceled()
            val word = if (prefix.isNotEmpty() && prefix.first().isUpperCase()) entry.word.capitalize() else entry.word
            val relevance = (entry.count.toDouble() / total) * 100
            val element = LookupElementBuilder
                .create(word)
                .withTypeText(String.format("%.2f", relevance))
                .withInsertHandler { localContext, _ ->
                    localContext.document.insertString(localContext.tailOffset, " ")
                    localContext.editor.caretModel.moveToOffset(localContext.tailOffset + 1)
                }
            dictResult.addElement(PrioritizedLookupElement
                .withPriority(element, entry.count.toDouble()/total))
        }

    }
}

