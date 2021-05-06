import com.github.muldrik.toycodecompletion.completion.MyDictionary
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import org.junit.Test

class DictionaryTest : LightPlatformCodeInsightFixture4TestCase() {
    @Test
    fun `Check dictionary is not empty`() {
        val contents = MyDictionary.dumbFilterByPrefix("")
        assertTrue(contents.isNotEmpty())
    }

    @Test
    fun `Check binary search correctness`() {
        var binSearchResults = MyDictionary.filterByPrefix("")
        var brutforceResults = MyDictionary.getAllWords()
        assertEquals(binSearchResults, brutforceResults)
        for (c in 'a'..'z') {
            if (c == 'x') continue //No words in the dictionary start with x
            binSearchResults = MyDictionary.filterByPrefix(c.toString())
            brutforceResults = MyDictionary.dumbFilterByPrefix(c.toString())
            assertEquals(binSearchResults, brutforceResults)
        }
    }
}