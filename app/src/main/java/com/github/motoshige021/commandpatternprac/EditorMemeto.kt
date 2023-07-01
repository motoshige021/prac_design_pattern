package com.github.motoshige021.commandpatternprac

import android.graphics.Color

class EditorMemeto(in_editor: Editor, in_backupText: String, in_backupColor: Color) {
    private lateinit var backupText: String
    private lateinit var backupColor: Color
    private lateinit var editor: Editor

    init {
        editor = in_editor
        backupText = in_backupText
        backupColor = in_backupColor
    }

    fun restore() {
        editor.setBackupData(backupText, backupColor)
    }

    fun clone() : EditorMemeto {
        return EditorMemeto(this.editor, this.backupText, this.backupColor)
    }
}