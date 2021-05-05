package com.github.muldrik.toycodecompletion.txtcLanguage

import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry

open class PsiTxtcFileImpl(viewProvider: FileViewProvider) :
    PsiFileImpl(TxtcTokenTypes.TXTC_TEXT_FILE, TxtcTokenTypes.TXTC_TEXT_FILE, viewProvider),
    PsiFile {
    private val myFileType: FileType = if (viewProvider.baseLanguage !== TxtcLanguage) TxtcFileType else viewProvider.fileType
    override fun accept(visitor: PsiElementVisitor) {
        visitor.visitElement(this)
    }

    override fun toString(): String {
        return "PsiFile(txtc text):$name"
    }

    override fun getFileType(): FileType {
        return myFileType
    }

    override fun getReferences(): Array<PsiReference> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }
}
