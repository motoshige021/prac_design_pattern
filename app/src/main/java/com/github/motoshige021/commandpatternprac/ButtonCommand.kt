package com.github.motoshige021.commandpatternprac

import android.graphics.Color

abstract class ButtonCommand(in_appModel: AppViewModel, in_editor: Editor)
    : ProtoTypeButtonCommand{
    protected lateinit var appModel: AppViewModel
    protected lateinit var editor: Editor
    // コマンド履歴 （コマンド・オブジェクトのスタック）に、 その時点で
    // エディターの状態の バックアップ用コピーとともに置かれます
    // Memento パターンで、状態のスナップショットの作成、保存する
    // EditorMemetoに移動
    //var backupEdit: String = ""
    //var backupColor : Color = Color.valueOf(Color.BLACK)
    protected var backupMemeto:EditorMemeto ? = null

    init {
        appModel = in_appModel
        editor = in_editor
    }

    abstract fun execute() : Boolean
    override abstract  fun clone(): ButtonCommand

    protected fun backup() {
        backupMemeto = editor.getBackupMemeto()
    }

    fun restoreEditor() {
        this.backupMemeto?.let {
            it.restore()
        }
    }
}