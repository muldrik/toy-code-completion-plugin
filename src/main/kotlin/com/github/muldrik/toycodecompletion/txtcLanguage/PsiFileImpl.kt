package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.impl.source.PsiPlainTextFileImpl
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.impl.source.tree.PsiPlainTextImpl


open class PsiTxtcFileImpl(viewProvider: FileViewProvider) :
    PsiFileImpl(TxtcTokenTypes.TXTC_TEXT_FILE, TxtcTokenTypes.TXTC_TEXT_FILE, viewProvider), PsiFile {
    private val myFileType: FileType = if (viewProvider.baseLanguage !== TxtcLanguage) TxtcFileType else viewProvider.fileType
    override fun accept(visitor: PsiElementVisitor) {
        //println("B")
        visitor.visitElement(this)
    }

    override fun toString(): String {
        //println("BB")
        return "PsiFile(txtc text):$name"
    }

    override fun getFileType(): FileType {
        return myFileType
    }

    override fun getReferences(): Array<PsiReference> {
        //println("BBBB")
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }
}
