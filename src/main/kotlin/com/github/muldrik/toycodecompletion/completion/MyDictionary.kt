package com.github.muldrik.toycodecompletion.completion

import java.io.InputStream



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

    private fun smallerNotPrefix(word: String, prefix: String): Boolean {
        return (word < prefix && !word.startsWith(prefix))
    }

    private fun leftBound(prefix: String): Int {
        var l = 0
        var r = words.size
        while (r - l > 1) {
            val m = (l + r) / 2
            if (smallerNotPrefix(words[m].word, prefix)) {
                l = m
            }
            else r = m
        }
        return if (l == 0) l else l + 1
    }

    private fun rightBound(leftBound: Int, prefix: String): Int {
        var l = leftBound
        var r = words.size
        while (r - l > 1) {
            val m = (l + r) / 2
            if (words[m].word.startsWith(prefix)) {
                l = m
            }
            else r = m
        }
        return l
    }

    fun filterByPrefix(prefix: String): List<WordEntry> {

        val leftBound = leftBound(prefix)
        val rightBound = rightBound(leftBound, prefix)

        val res = mutableListOf<WordEntry>()

        for (i in leftBound..rightBound) {
            res.add(words[i])
        }
        res.sortByDescending { it.count }
        return res
    }

    fun dumbFilterByPrefix(prefix: String): List<WordEntry> {
        val res = mutableListOf<WordEntry>()

        for (pair in words) {
            if (pair.word.startsWith(prefix)) {
                res.add(pair)
            }
        }
        res.sortByDescending { it.count }
        return res
    }

    fun getAllWords(): List<WordEntry> {
        return words.sortedByDescending { it.count }
    }
}