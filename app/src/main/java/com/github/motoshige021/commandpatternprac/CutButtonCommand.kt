package com.github.motoshige021.commandpatternprac

class CutButtonCOmmand (in_appModel: AppViewModel, in_editor: Editor)
    : ButtonCommand(in_appModel, in_editor) {
    private var count = 1

    override fun execute(): Boolean {
        this.backup()
        var line = count % Editor.MAX_LINE
        ++count
        editor.cutSelection(line)
        return true
    }

    override fun clone(): ButtonCommand {
        var buttonCommand = CutButtonCOmmand(appModel, editor)
        // EditorMemeto
        //buttonCommand.backupEdit = this.backupEdit
        //buttonCommand.backupColor = this.backupColor
        this.backupMemeto?.let {
            buttonCommand.backupMemeto =  it.clone()
        }
        return buttonCommand
    }
}