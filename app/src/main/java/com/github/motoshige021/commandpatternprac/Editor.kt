package com.github.motoshige021.commandpatternprac

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Editor() {
    companion object {
        val MAX_LINE = 3
    }

    private var texts = arrayOfNulls<String>(MAX_LINE)
    var textColor: Color = Color.valueOf(Color.BLACK)
    private val _updated = MutableLiveData<Int>(0)
    val updated : LiveData<Int> = _updated
    private val _cutText = MutableLiveData<String>("")
    var cutText : LiveData<String> = _cutText
    private val _pasted = MutableLiveData<Int>(0)
    val pasted : LiveData<Int> = _pasted

    fun getText(line: Int): String ? {
        if (line >= MAX_LINE) { return null }
        return texts[line]
    }

    fun setText(in_text: String, line: Int, in_textColor: Color,
                isPaste : Boolean = false) {
        if (line >= MAX_LINE) { return }
        textColor = Color.valueOf(in_textColor.toArgb())
        texts[line] = in_text
        val value = _updated.value!!
        _updated.value = value + 1
        if (isPaste) {
            val value = _pasted.value!!
            _pasted.value = value + 1
        }
    }

    fun cutSelection(line: Int): String ?{
        if (line >= MAX_LINE) { return null }
        var text = texts[line]
        texts[line] = ""
        text?.apply {
            _cutText.value = text
        }
        if (text == null) { _cutText.value = "" }

        val value = _updated.value!!
        _updated.value = value + 1
        return text
    }

    fun getBackupText(): String {
        var text = ""
        texts.forEach { str->
            str?.let {
                text += it
            }
            text += "\n"
        }
        text = text.dropLast(1)
        return text
    }

    fun getBackupMemeto() : EditorMemeto {
        return EditorMemeto(this, this.getBackupText(), textColor)
    }

    fun setBackupData(backupText: String, backupColor: Color) {
        this.textColor = backupColor
        val textArray = backupText.split('\n')
        for (i in 0 until textArray.size) {
            var text = textArray[i]
            this.setText(text, i, backupColor)
        }
        for (i in textArray.size until Editor.MAX_LINE) {
            this.setText("", i, backupColor)
        }
    }
}