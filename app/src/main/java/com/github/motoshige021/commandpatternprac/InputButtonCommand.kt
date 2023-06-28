package com.github.motoshige021.commandpatternprac

import android.graphics.Color

class InputButtonCommand(in_appModel: AppViewModel, in_editor: Editor)
        : ButtonCommand(in_appModel, in_editor) {
    private var count = 0
    private val msgArray = arrayOf("it is one of the larger Alpine Lakes.",
        "the Blüemlisalp in the Bernese Alps", "A gondola ride from Kandersteg",
        "lake warms to 20 degrees Celsius", "Activities enjoyed here include rowing")
    private val colorArray = arrayOf(Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW)

    override fun execute(): Boolean {
        this.backup()
        var msgIdx = count % msgArray.size
        var line = count % Editor.MAX_LINE
        var colorIdx = count % colorArray.size
        var textColor = Color.valueOf(colorArray[colorIdx])
        ++count
        editor.setText(msgArray[msgIdx], line, textColor)
        return true
    }

    override fun copy(): ButtonCommand {
        var inputCommand = InputButtonCommand(this.appModel, this.editor)
        inputCommand.backupEdit = this.backupEdit
        inputCommand.backupColor = this.backupColor
        return inputCommand
    }
}