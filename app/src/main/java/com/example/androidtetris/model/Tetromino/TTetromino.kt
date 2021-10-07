package com.example.androidtetris.model.Tetromino

import android.graphics.Color

class TTetromino : Tetromino() {

    init {
        color = Color.MAGENTA
        shape = arrayOf(
            arrayOf(0, 6, 0),
            arrayOf(6, 6, 6),
            arrayOf(0, 0, 0)
        )
    }
}