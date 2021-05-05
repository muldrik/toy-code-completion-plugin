package com.github.muldrik.toycodecompletion

import com.intellij.spellchecker.BundledDictionaryProvider
import com.intellij.spellchecker.SpellCheckerManager
import com.intellij.spellchecker.StreamLoader
import com.intellij.spellchecker.dictionary.Dictionary
import com.intellij.spellchecker.engine.Transformation
import com.intellij.util.containers.ContainerUtil
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.net.URI
import java.net.URL


/**
 * @author jansorg
 */

data class WordEntry(val word: String, val count: Long)


internal object MyDictionary {

    private lateinit var words: MutableList<WordEntry>

    fun loadWords(filename: InputStream) {
        val result = mutableListOf<WordEntry>()
        //val file = File(filename.toURI())
        val file = filename.bufferedReader()
        val writer = FileWriter("kek.txt")
        writer.write(123)
        writer.close()
        /*if(!file.exists())
            throw IllegalArgumentException("Cannot read dictionary file")*/
        file.forEachLine {
            val values = it.split(' ')
            result.add(WordEntry(values.first(), values.last().toLong()))
        }
        result.sortBy { it.word }
        words = result
    }

    init {
        //loadWords("/dictionary.txt")
    }


    fun filterByPrefix(prefix: String, amount: Int): List<WordEntry> {
        val res = mutableListOf<WordEntry>()
        for (pair in words) {
            if (pair.word.startsWith(prefix)) {
                res.add(pair)
            }
        }
        res.sortByDescending { it.count }
        for (el in res) {
            print("$el ")
            println()
        }
        return res
    }
}