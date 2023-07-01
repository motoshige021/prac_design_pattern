package com.github.motoshige021.commandpatternprac

import android.widget.Button

class PastoButtonCommand (in_appModel: AppViewModel, in_editor: Editor)
    : ButtonCommand(in_appModel, in_editor) {
    private var count = 2

    override fun execute(): Boolean {
        this.backup()
        var line = count % Editor.MAX_LINE
        ++count
        editor.cutText.value ?.let {
            editor.setText(it, line, editor.textColor, true)
        }
        return true
    }

    override fun clone(): ButtonCommand {
        var buttonCommand = PastoButtonCommand(appModel, editor)
        // EditorMemetoに移動
        //buttonCommand.backupEdit = this.backupEdit
        //buttonCommand.backupColor = this.backupColor
        this.backupMemeto?.let {
            buttonCommand.backupMemeto = it.clone()
        }
        return buttonCommand
    }
}