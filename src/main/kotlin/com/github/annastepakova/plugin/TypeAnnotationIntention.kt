package com.github.annastepakova.plugin

import com.intellij.AbstractBundle
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.contextOfType
import com.jetbrains.python.psi.*
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey

class TypeAnnotationIntention: PsiElementBaseIntentionAction(), IntentionAction {
    override fun getFamilyName(): String {
        return "Python Type Annotation"
    }

    override fun startInWriteAction(): Boolean {
        return true
    }

    override fun getText(): String {
        return "Add Any annotation"
    }

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        return LanguageLevel.forElement(element) >= LanguageLevel.PYTHON36 && (element.context is PyTargetExpression && element.parent.parent !is PyWithItem &&
                element.parent.parent !is PyForPart && element.parent.nextSibling !is PyAnnotation &&
                element.parent.children.isEmpty() ||
                element.context is PyNamedParameter && element.nextSibling !is PyAnnotation)
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        PyUtil.updateDocumentUnblockedAndCommitted(element){
            it: Document -> it.insertString(element.textRange.endOffset, ": Any")
        }
    }

}


@NonNls
private const val BUNDLE = "messages.MyBundle"

object MyBundle : AbstractBundle(BUNDLE) {

    @Suppress("SpreadOperator")
    @JvmStatic
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) = getMessage(key, *params)

    @Suppress("SpreadOperator")
    @JvmStatic
    fun messagePointer(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) = run {
        message(key, *params)
    }
}
