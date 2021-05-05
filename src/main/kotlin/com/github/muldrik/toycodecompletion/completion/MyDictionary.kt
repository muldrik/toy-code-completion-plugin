package com.github.muldrik.toycodecompletion.completion

import java.io.InputStream


/**
 * @author jansorg
 */

data class WordEntry(val word: String, val count: Long)


internal object MyDictionary {


    private lateinit var words: MutableList<WordEntry>

    fun loadWords(filename: InputStream) {
        val result = mutableListOf<WordEntry>()
        val file = filename.bufferedReader()
        file.forEachLine {
            val values = it.split(' ')
            result.add(WordEntry(values.first(), values.last().toLong()))
        }
        result.sortBy { it.word }
        words = result
    }

    fun filterByPrefix(prefix: String, amount: Int): List<WordEntry> {
        val res = mutableListOf<WordEntry>()
        for (pair in words) {
            if (pair.word.startsWith(prefix)) {
                res.add(pair)
            }
        }
        res.sortByDescending { it.count }
        return res
    }
}