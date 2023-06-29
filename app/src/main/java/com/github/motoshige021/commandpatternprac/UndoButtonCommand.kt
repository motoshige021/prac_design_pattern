package com.github.motoshige021.commandpatternprac

import android.graphics.Color

class UndoButtonCommand (in_appModel: AppViewModel, in_editor: Editor)
    : ButtonCommand(in_appModel, in_editor) {
    override fun execute(): Boolean {
        return false
    }

    override fun clone(): ButtonCommand {
        var buttonCommand = UndoButtonCommand(appModel, editor)
        buttonCommand.backupEdit= this.backupEdit
        buttonCommand.backupColor = this.backupColor
        return buttonCommand
    }
}