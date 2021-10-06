package com.example.androidtetris.model

open class Tetromino {

    // 현재 위치를 나타내는 x, y
    // x 값 - 테트리스 판의 가운데 위치
    var x: Int = GameConfig.TETRIS_WIDTH / 2 - 1
    var y: Int = 0

    // 몇 by 몇 사이즈의 모양인지 나타낼
    var shape: Array<Array<Int>> = GameConfig.block[(0..6).random()] as Array<Array<Int>>

    fun rotate() {
        val rotateTetromino = Array(shape.size) { Array(shape.size) { 0 } }

        for (i in shape.indices) {
            for (j in shape[i].indices) {
                rotateTetromino[i][j] = shape[shape.size - 1 - j][i]
            }
        }

        shape = rotateTetromino
    }
}
