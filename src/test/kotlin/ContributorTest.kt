
import com.github.muldrik.toycodecompletion.completion.MyDictionary
import com.intellij.codeInsight.editorActions.CompletionAutoPopupHandler
import com.intellij.testFramework.TestModeFlags
import com.intellij.testFramework.fixtures.CompletionAutoPopupTester
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
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
            if (c == 'x') continue //No words in the used dictionary start with x
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


}

/*
class StringLiteralDictionaryAutoPopupContributorTest : BasePlatformTestCase() {
    override fun runInDispatchThread(): Boolean {
        return false
    }

    @Test
    fun testAutoPopupCompletions() {

        val tester = CompletionAutoPopupTester(myFixture)
        tester.runWithAutoPopupEnabled {
            myFixture.configureByText("test.txtc", "")
            */
/*tester.typeWithPauses("ob")
            println(myFixture.lookupElementStrings)*//*

            for (c in 'a'..'z') {
                if (c == 'x') continue //No words in the used dictionary start with x
                tester.typeWithPauses(c.toString())
                println(myFixture.lookupElementStrings)
                assertFalse(myFixture.lookupElementStrings.isNullOrEmpty())
                tester.typeWithPauses(" ")
            }
        }

    }
}
*/
