package com.github.muldrik.toycodecompletion.txtcLanguage


import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.ParserDefinition.SpaceRequirements
import com.intellij.lang.PsiParser
import com.intellij.lexer.EmptyLexer
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiUtilCore


class TxtcParserDefinition : ParserDefinition {

    override fun createLexer(project: Project?): Lexer {
        return EmptyLexer()
    }

    override fun createParser(project: Project?): PsiParser {
        throw UnsupportedOperationException("Not supported")
    }

    override fun getFileNodeType(): IFileElementType {
        return TXTC_FILE_ELEMENT_TYPE
    }

    override fun getWhitespaceTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }


    override fun createElement(node: ASTNode?): PsiElement {
        return PsiUtilCore.NULL_PSI_ELEMENT
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return PsiTxtcFileImpl(viewProvider)
    }

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): SpaceRequirements {
        return SpaceRequirements.MAY
    }

    companion object {
        private val TXTC_FILE_ELEMENT_TYPE: IFileElementType =
            object : IFileElementType(TxtcFileType.INSTANCE.language) {
                override
                fun parseContents(chameleon: ASTNode): ASTNode {
                    val chars: CharSequence = chameleon.chars
                    return ASTFactory.leaf(TxtcTokenTypes.TXTC_TEXT, chars)
                }
            }
    }
}