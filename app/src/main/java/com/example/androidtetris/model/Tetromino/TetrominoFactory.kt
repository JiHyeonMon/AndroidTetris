package com.example.androidtetris.model.Tetromino

class TetrominoFactory {

    fun create(): Tetromino {
        return when ((1..7).random()) {
            1 -> ITetromino()
            2 -> JTetromino()
            3 -> LTetromino()
            4 -> OTetromino()
            5 -> STetromino()
            6 -> TTetromino()
            7 -> ZTetromino()
            else -> OTetromino()
        }
    }
}