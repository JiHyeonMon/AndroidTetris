package com.example.androidtetris.model.Tetromino

import android.graphics.Color

class LTetromino : Tetromino() {

    init {
        color = Color.rgb(255,69,0)
        shape = arrayOf(
            arrayOf(0, 0, 3),
            arrayOf(3, 3, 3),
            arrayOf(0, 0, 0)
        )
    }

}