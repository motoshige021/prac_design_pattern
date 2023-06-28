package com.github.motoshige021.commandpatternprac

import android.widget.Button
import androidx.fragment.app.Fragment

class AppMediator(in_appViewMode: AppViewModel, in_owner: Fragment) {
    private lateinit var appViewMode: AppViewModel
    private var cutButtonProc: (()->Unit)? = null
    private var undoButtonProc: ((historySize: Int)->Unit)? = null
    private var pasteButonProc: (()->Unit)? = null
    private var updateProc: ((appViewModel: AppViewModel)->Unit) ? = null

    init {
        appViewMode = in_appViewMode

        appViewMode.editor.updated.observe(in_owner) {
            var _updateProc = updateProc?: return@observe
            _updateProc(appViewMode)
        }
        appViewMode.editor.cutText.observe(in_owner) {
            var _cutButtonProc = cutButtonProc?: return@observe
            _cutButtonProc()
        }
        appViewMode.historyChanged.observe(in_owner) {
            var historySize = it ?: return@observe
            var _undoButtonProc = undoButtonProc ?: return@observe
            _undoButtonProc(historySize)
        }
        appViewMode.editor.pasted.observe(in_owner) {
            var _pasteButtonProc = pasteButonProc?: return@observe
            _pasteButtonProc()
        }
    }

    fun setCutButtonProc(func:()->Unit) {
        cutButtonProc = func
    }

    fun setUndoButtonProc(func: (historySize: Int)->Unit) {
        undoButtonProc = func
    }

    fun setPasteButtonProc(func: ()->Unit) {
        pasteButonProc = func
    }

    fun setUpdateProc(func: (appView: AppViewModel)->Unit) {
        updateProc = func
    }
}
