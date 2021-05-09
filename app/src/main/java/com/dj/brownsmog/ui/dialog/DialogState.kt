package com.dj.brownsmog.ui.dialog

enum class DialogType {
    SIMPLE, TITLE, VERTICALBUTTON, IMAGE, LONGDIALOG, ROUNDED
}

data class DialogState(var showDialog: Boolean, var dialogType: DialogType)