package com.github.muldrik.toycodecompletion


import com.intellij.lang.ASTFactory;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition.SpaceRequirements
import com.intellij.psi.impl.source.PsiPlainTextFileImpl
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiUtilCore
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.tree.IFileElementType
import com.intellij.lang.PsiParser
import com.intellij.lexer.EmptyLexer
import com.intellij.psi.PlainTextTokenTypes
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.lexer.Lexer


class PlainTextParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer {
        return EmptyLexer()
    }

    override fun createParser(project: Project?): PsiParser {
        throw UnsupportedOperationException("Not supported")
    }

    override fun getFileNodeType(): IFileElementType {
        return IFileElementType(SimpleLanguage.INSTANCE)
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
        return PsiPlainTextFileImpl(viewProvider)
    }

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): SpaceRequirements {
        return SpaceRequirements.MAY
    }

    companion object {
        private val PLAIN_FILE_ELEMENT_TYPE: IFileElementType =
            object : IFileElementType(PlainTextFileType.INSTANCE.language) {
                override fun parseContents(chameleon: ASTNode): ASTNode {
                    val chars: CharSequence = chameleon.chars
                    return ASTFactory.leaf(PlainTextTokenTypes.PLAIN_TEXT, chars)
                }
            }
    }
}