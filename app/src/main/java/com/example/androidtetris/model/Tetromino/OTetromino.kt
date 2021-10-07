package com.example.androidtetris.model.Tetromino

import android.graphics.Color

class OTetromino : Tetromino() {

    init {
        color = Color.YELLOW
        shape = arrayOf(
            arrayOf(4, 4),
            arrayOf(4, 4)
        )
    }
}