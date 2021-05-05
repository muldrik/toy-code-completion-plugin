
import com.github.muldrik.toycodecompletion.MyDictionary
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import org.junit.Assert
import org.junit.Test
import com.intellij.testFramework.fixtures.CompletionAutoPopupTester

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.jupiter.api.BeforeAll


class ContributorTest : LightPlatformCodeInsightFixture4TestCase() {

    companion object {
        @BeforeAll
        @JvmStatic
        fun initDictionary() {
            println("test kek1")
            val current = Thread.currentThread().contextClassLoader
            try {
                Thread.currentThread().contextClassLoader = this.javaClass.classLoader
                println(current.definedPackages)
                val path = this.javaClass.getResourceAsStream("/dictionary.txt")
                MyDictionary.loadWords(path)
                println("not dead")
            } finally {
                Thread.currentThread().contextClassLoader = current
            }
            println("test kek2")
        }
    }

    @Test
    fun noEmptyPrefix() {
        // empty file
        myFixture.configureByText("test.txt", "")
        Assert.assertEquals(
            "expected no completions for an empty file",
            0, myFixture.completeBasic().size
        )

        // whitespace suffix
        myFixture.configureByText("test.txt", "foo")
        myFixture.type(" ")
        Assert.assertEquals(
            "expected no completions after whitespace",
            0, myFixture.completeBasic().size
        )
    }

    @Test
    fun completions() {
        myFixture.configureByText("test.txt", "")
        myFixture.type("ob")
        Assert.assertTrue(myFixture.completeBasic().isNotEmpty())
    }
}

/*
class StringLiteralDictionaryAutoPopupContributorTest : BasePlatformTestCase() {
    override fun runInDispatchThread(): Boolean {
        return false
    }

    fun testAutoPopupCompletions() {
        val tester = CompletionAutoPopupTester(myFixture)
        tester.runWithAutoPopupEnabled {
            myFixture.configureByText("test.txt", "")
            tester.typeWithPauses("ob")
            tester.joinCompletion()
        }
    }
}*/
