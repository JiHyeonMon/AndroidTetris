package com.example.androidtetris.model.Tetromino

import android.graphics.Color

class ZTetromino : Tetromino() {

    init {
        color = Color.RED
        shape = arrayOf(
            arrayOf(7, 7, 0),
            arrayOf(0, 7, 7),
            arrayOf(0, 0, 0)
        )
    }
}