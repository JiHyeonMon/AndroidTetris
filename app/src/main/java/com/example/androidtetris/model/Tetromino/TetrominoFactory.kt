package com.example.androidtetris.model.Tetromino

class TetrominoFactory {

    // 테트로미노를 만들어서 반환한다.
    // 테트로미노 모양은 랜덤으로 나온다.
    // 1~7에 랜덤으로 숫자를 뽑아 랜덤으로 테트로미노를 뽑는다.
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