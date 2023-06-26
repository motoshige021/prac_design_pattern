package com.github.motoshige021.commandpatternprac

import android.graphics.Color
import android.widget.EditText
import android.widget.TextView

class Mes1ButtonCommandImpl(in_textView: TextView) : SimpleButtonCommand{
    private lateinit var textView: TextView
    init {
        textView = in_textView
    }
    override fun execute() {
        textView.setText("one first No1 ")
        textView.setTextColor(Color.BLUE)
    }
}