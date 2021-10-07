package com.example.androidtetris.model.Tetromino

import android.graphics.Color

class JTetromino : Tetromino() {

    init {
        color = Color.BLUE
        shape =
            arrayOf(
                arrayOf(2, 0, 0),
                arrayOf(2, 2, 2),
                arrayOf(0, 0, 0)
            )
    }
}