package com.github.muldrik.toycodecompletion.completion

import java.io.InputStream


/**
 * Represents a word entry in a frequency dictionary
 * @param word - the word itself
 * @param count - frequency relative to other words (e.g. how many times a word is encountered in all datasets
 */
data class WordEntry(val word: String, val count: Long)


/**
 * Stores word entries and provides filtered access to them
 */
internal object MyDictionary {

    private fun loadDictionary(): List<WordEntry> {
        val current = Thread.currentThread().contextClassLoader //Load dictionary file
        var result = listOf<WordEntry>()
        try {
            Thread.currentThread().contextClassLoader = this.javaClass.classLoader
            val path = this.javaClass.getResourceAsStream("/dictionary.txt")
            result = loadWords(path)
        } finally {
            Thread.currentThread().contextClassLoader = current
        }
        return result
    }

    /**
     * Contains all word entries. Must be alphabetically sorted on initialization
     */
    private val words: List<WordEntry> by lazy {
        loadDictionary()
    }

    /**
     * Load word entries from a dictionary
     * @param filename - opened InputStream to read text from
     * Text file is expected to contain one word, a space and one integer on every line in that order,
     * initial sorting is not required
     */
    fun loadWords(filename: InputStream): List<WordEntry> {
        val result = mutableListOf<WordEntry>()
        val file = filename.bufferedReader()
        file.forEachLine {
            val values = it.split(' ')
            result.add(WordEntry(values.first(), values.last().toLong()))
        }
        result.sortBy { it.word }
        return result
    }

    private fun smallerNotPrefix(word: String, prefix: String): Boolean {
        return (word < prefix && !word.startsWith(prefix))
    }


    /**
     * Find the leftmost index for a WordEntry that begins with the provided prefix
     */
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

    /**
     * Find the rightmost index for a WordEntry that begins with the provided prefix
     */
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

    /**
     * Uses binary search to find the subarray containing all words staring with the provided prefix
     * @return List of all words that begin with the provided prefix. Sorted by descending frequency
     */
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

    /**
     * Iterates over all words to find the subarray containing all words staring with the provided prefix
     * @return List of all words that begin with the provided prefix. Sorted by descending frequency
     */
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

    /**
     * @return All loaded word entries. Equivalent to filterByPrefix("")
     */
    fun getAllWords(): List<WordEntry> {
        return words.sortedByDescending { it.count }
    }
}