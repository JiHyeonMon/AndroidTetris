package com.example.androidtetris.model.Tetromino

import android.graphics.Color

class STetromino : Tetromino() {

    init {
        color = Color.GREEN
        shape = arrayOf(
            arrayOf(0, 5, 5),
            arrayOf(5, 5, 0),
            arrayOf(0, 0, 0)
        )
    }
}