package com.github.motoshige021.commandpatternprac

interface ProtoTypeButtonCommand {
    fun clone() : ButtonCommand
}