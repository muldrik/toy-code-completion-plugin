package com.github.muldrik.toycodecompletion.completion

import com.github.muldrik.toycodecompletion.completion.MyDictionary.filterByPrefix
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.LookupElementBuilder

import com.intellij.openapi.progress.ProgressManager


/**
 * Completion Provider for Txtc language
 * Mostly sorts words by frequencies, displays them as percentages in completion options
 */
internal class Provider :
    CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {

        //prefix is set to the current word
        val prefix = result.prefixMatcher.prefix

        //Add nothing for an empty prefix
        if (prefix.isEmpty() && parameters.isAutoPopup) {
            return
        }

        //Get all word entries with the current prefix, sorted by descending frequency
        val words = filterByPrefix(prefix.toLowerCase())

        //Compute total word frequencies to later display relevance
        val total = words.sumByDouble { it.count.toDouble() }

        //Update on every prefix change to update relevance
        result.restartCompletionOnAnyPrefixChange()


        for (entry in words) {
            ProgressManager.checkCanceled()

            //If the prefix is capitalized, options also must be
            val word = if (prefix.isNotEmpty() && prefix.first().isUpperCase()) entry.word.capitalize() else entry.word

            //Calculate relevance as the percentage of all words with the same prefix
            val relevance = (entry.count.toDouble() / total) * 100
            val element = LookupElementBuilder
                .create(word)
                .withTypeText(String.format("%.2f", relevance)) //Display relevance with 2 decimal digits accuracy
                .withInsertHandler { localContext, _ ->
                    localContext.document.insertString(localContext.tailOffset, " ") //Paste a space on completion
                    localContext.editor.caretModel.moveToOffset(localContext.tailOffset + 1) //Move to the space
                }
            result.addElement(PrioritizedLookupElement //Add an element with a priority based on calculated relevance
                .withPriority(element, entry.count.toDouble()/total))
        }

    }
}

