
import com.github.muldrik.toycodecompletion.completion.MyDictionary
import com.intellij.codeInsight.editorActions.CompletionAutoPopupHandler
import com.intellij.testFramework.TestModeFlags
import com.intellij.testFramework.fixtures.CompletionAutoPopupTester
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import junit.framework.TestCase
import org.junit.Test
import kotlin.math.sqrt
import kotlin.test.assertNotEquals


class ContributorTest : LightPlatformCodeInsightFixture4TestCase() {

    override fun runInDispatchThread(): Boolean { //Required for autocompletion tests
        return false
    }

    init {
        TestModeFlags.set(CompletionAutoPopupHandler.ourTestingAutopopup, true) //Required for autocompletion tests
    }


    @Test
    fun `Autocompletion is disabled for empty strings`() {
        myFixture.configureByText("test.txtc", "")
        assertTrue(
            "expected no autocompletion for an empty file",
            myFixture.lookupElementStrings.isNullOrEmpty()
        )
        myFixture.configureByText("test.txtc", "foo")
        myFixture.type(" ")
        assertTrue(
            "expected no autocompletion after whitespace",
            myFixture.lookupElementStrings.isNullOrEmpty()
        )
    }

    @Test
    fun `Manual invocation on empty string returns the entire dictionary`() {
        myFixture.configureByText("test.txtc", "")
        assertTrue(myFixture.completeBasic().isNotEmpty())
        myFixture.type(" ")
        assertTrue(myFixture.completeBasic().isNotEmpty())
    }

    @Test
    fun `Autocompletion for single letters expected non-empty`() {
        val tester = CompletionAutoPopupTester(myFixture)
        myFixture.configureByText("test.txtc", "")
        for (c in 'a'..'z') {
            if (c == 'x') continue //No words in the used dictionary begin with x
            tester.typeWithPauses(c.toString())
            assertFalse(myFixture.lookupElementStrings.isNullOrEmpty())
            tester.typeWithPauses(" ")
        }
    }

    @Test
    fun `Capitalized word suggestions are the same as non-capitalized`() {
        val tester = CompletionAutoPopupTester(myFixture)
        myFixture.configureByText("test.txtc", "")
        for (c in 'a'..'z') {
            tester.typeWithPauses("$c")
            val lowercaseResults = myFixture.lookupElementStrings
            tester.typeWithPauses(" ")
            tester.typeWithPauses("${c.toUpperCase()}")
            val capitalizedResults = myFixture.lookupElementStrings
            tester.typeWithPauses(" ")
            if (lowercaseResults == null || capitalizedResults == null) {
                assert(lowercaseResults == null && capitalizedResults == null)
                continue
            }
            assertEquals(lowercaseResults.size, capitalizedResults.size)

            for ((i, element) in capitalizedResults.withIndex()) {
                val lowercaseWord = lowercaseResults[i]
                assertNotEquals(element, lowercaseWord)
                assertEquals(element, lowercaseWord.capitalize())
            }
        }
    }

    @Test
    fun `Fully typed word is the first in suggestion list`() {
        myFixture.configureByText("test.txtc", "")
        val entries = MyDictionary.getAllWords()
        val randomPoolSize = minOf(300, entries.size)
        val randomPool = entries.shuffled().subList(0, randomPoolSize - 1)
        for (entry in randomPool) {
            myFixture.type(entry.word)
            val completionResults = myFixture.completeBasic()
            if (completionResults != null) { //If multiple options are availible
                assertEquals(entry.word, completionResults.first().lookupString)
            }
            else {
                assertEquals(completionResults, null) //null <=> the word was successfully completed
            }
            myFixture.type(" ")
        }
    }
}