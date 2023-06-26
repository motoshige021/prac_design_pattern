package com.github.motoshige021.commandpatternprac

import android.graphics.Color

abstract class ButtonCommand(in_appModel: AppViewModel, in_editor: Editor) {
    protected lateinit var appModel: AppViewModel
    protected lateinit var editor: Editor
    // コマンド履歴 （コマンド・オブジェクトのスタック）に、 その時点で
    // エディターの状態の バックアップ用コピーとともに置かれます
    var backupEdit: String = ""
    var backupColor : Color = Color.valueOf(Color.BLACK)

    init {
        appModel = in_appModel
        editor = in_editor
    }

    abstract fun execute() : Boolean

    abstract fun copy() : ButtonCommand

    protected fun backup() {
        backupEdit = editor.getBackup()
        backupColor = editor.textColor
    }
}