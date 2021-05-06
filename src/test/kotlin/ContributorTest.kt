
import com.github.muldrik.toycodecompletion.completion.MyDictionary
import com.intellij.codeInsight.editorActions.CompletionAutoPopupHandler
import com.intellij.testFramework.TestModeFlags
import com.intellij.testFramework.fixtures.CompletionAutoPopupTester
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import junit.framework.TestCase
import org.junit.Test
import kotlin.test.assertNotEquals


class ContributorTest : LightPlatformCodeInsightFixture4TestCase() {

    override fun runInDispatchThread(): Boolean {
        return false
    }

    init {
        TestModeFlags.set(CompletionAutoPopupHandler.ourTestingAutopopup, true)
    }


    @Test
    fun `Check loaded dictionary`() {

        assertTrue(MyDictionary.filterByPrefix("", 30).isNotEmpty())
        for (c in 'a'..'z') {
            if (c == 'x') continue //No words in the dictionary start with x
            assertTrue(MyDictionary.filterByPrefix(c.toString(), 30).isNotEmpty())
        }
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
    fun `Capitalized words complete the same as lowercase`() {
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
        val tester = CompletionAutoPopupTester(myFixture)
        myFixture.configureByText("test.txtc", "")
        val entries = MyDictionary.filterByPrefix("", 30)
        for (entry in entries) {
            tester.typeWithPauses(entry.word)
            val completionResults = myFixture.lookupElementStrings
            println("${entry.word} $completionResults")
            if (completionResults != null) {
                assertEquals(entry.word, completionResults.first())
            }
            else {
                assertEquals(myFixture.completeBasic(), null)
            }
            tester.typeWithPauses(" ")
        }
    }
}