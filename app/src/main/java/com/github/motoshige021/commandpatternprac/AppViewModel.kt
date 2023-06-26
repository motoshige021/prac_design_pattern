package com.github.motoshige021.commandpatternprac

import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    var editor: Editor = Editor()

    var historyButtonCommand = arrayListOf<ButtonCommand>()

    fun executeCommand(buttonCommand: ButtonCommand) {
        // 履歴に積むButtonCommandと履歴に積まないのがあり、execute()の戻り値で判断
        if (buttonCommand.execute()) {
            val copyCommand = buttonCommand.copy()
            historyButtonCommand.add(copyCommand)
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
        return true
    }
}