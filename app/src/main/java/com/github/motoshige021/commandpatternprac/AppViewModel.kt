package com.github.motoshige021.commandpatternprac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AppViewModel : ViewModel() {
    var editor: Editor = Editor()

    private val _historyChanged = MutableLiveData<Int>(0)
    val historyChanged: LiveData<Int> = _historyChanged

    var historyButtonCommand = arrayListOf<ButtonCommand>()

    fun executeCommand(buttonCommand: ButtonCommand) {
        // 履歴に積むButtonCommandと履歴に積まないのがあり、execute()の戻り値で判断
        if (buttonCommand.execute()) {
            val copyCommand = buttonCommand.clone()
            historyButtonCommand.add(copyCommand)
            notifyHIstoryChange(historyButtonCommand.size)
        }
    }

    fun undo(buttonCommand: ButtonCommand) : Boolean {
        if (historyButtonCommand.isEmpty()) { return false }
        var historySize = historyButtonCommand.size
        var buttonCommand = historyButtonCommand.removeAt(historySize - 1)
        val textArray = buttonCommand.backupEdit.split('\n')
        for (i in 0 until textArray.size) {
            var text = textArray[i]
            editor.setText(text, i, buttonCommand.backupColor)
        }
        for (i in textArray.size until Editor.MAX_LINE) {
            editor.setText("", i, buttonCommand.backupColor)
        }
        notifyHIstoryChange(historyButtonCommand.size)
        return true
    }
    fun notifyHIstoryChange(historySize: Int) {
        _historyChanged.value = historySize
    }
}