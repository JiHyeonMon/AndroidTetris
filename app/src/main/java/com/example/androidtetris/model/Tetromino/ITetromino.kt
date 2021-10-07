package com.example.androidtetris.model.Tetromino

import android.graphics.Color

class ITetromino : Tetromino() {

    init {
        color = Color.rgb(47.055F, 55.289F, 86.785F)
        shape = arrayOf(
            arrayOf(1, 1, 1, 1),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        ) // 1*4 가로로 긴 애
    }
}