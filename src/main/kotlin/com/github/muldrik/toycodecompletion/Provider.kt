package com.github.muldrik.toycodecompletion

import com.github.muldrik.toycodecompletion.MyDictionary.filterByPrefix
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.util.ProcessingContext
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.lookup.LookupElementWeigher

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

        // return early when there's not prefix
        var prefix = result.prefixMatcher.prefix
        if (prefix.isEmpty()) {
            return
        }

        // make sure that our prefix is the last word
        // (In plain text files, the prefix initially contains the whole
        // file up to the cursor. We don't want that, as we're only
        // completing a single word.)
        val dictResult: CompletionResultSet
        val lastSpace = prefix.lastIndexOf(' ')
        if (lastSpace >= 0 && lastSpace < prefix.length - 1) {
            prefix = prefix.substring(lastSpace + 1)
            dictResult = result.withPrefixMatcher(prefix)
        } else {
            dictResult = result
        }

        val words = filterByPrefix(prefix.toLowerCase(), 30)
        dictResult.restartCompletionOnAnyPrefixChange()
        val total = words.sumByDouble { it.count.toDouble() }
        for ((i, entry) in words.withIndex()) {
            ProgressManager.checkCanceled()
            val word = if (prefix.first().isUpperCase()) entry.word.capitalize() else entry.word
            println(prefix.first().isUpperCase())
            val element = LookupElementBuilder
                .create(word)
                .withTypeText(entry.count.toString())
            dictResult.addElement(PrioritizedLookupElement
                .withPriority(element, entry.count.toDouble()/total))
            /*dictResult.addElement(PrioritizedLookupElement
                .withExplicitProximity(element, i+1))*/
        }

        /*for (dict in get()) {
            // limit completions to 20 additional characters max
            dict.getWords(Character.toLowerCase(firstChar), length, length + 20) { word ->
                // return early when the user modified the data of our editor
                ProgressManager.checkCanceled()
                val element: LookupElementBuilder
                if (isUppercase) {
                    element = LookupElementBuilder.create(
                        word.substring(0, 1).toUpperCase() + word.substring(1)
                    )
                } else {
                    element = LookupElementBuilder.create(word)
                }

                // finally, add it to the completions
                dictResult.addElement(element)
            }
        }*/
    }
}

