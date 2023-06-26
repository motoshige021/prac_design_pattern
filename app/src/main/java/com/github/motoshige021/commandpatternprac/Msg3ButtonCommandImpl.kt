package com.github.motoshige021.commandpatternprac

import android.graphics.Color
import android.widget.EditText
import android.widget.TextView

class Msg3ButtonCommandImpl(in_textView: TextView) : SimpleButtonCommand{
    private lateinit var textView : TextView
    init {
        textView = in_textView
    }
    override fun execute() {
        textView.setTextColor(Color.BLACK)
        textView.setText("three Third No3")
    }
}