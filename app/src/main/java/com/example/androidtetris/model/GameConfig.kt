package com.example.androidtetris.model

object GameConfig {

    // 테트리스 판 너비와 높이 설정
    const val TETRIS_WIDTH = 10
    const val TERIS_HEIGHT = 20

    val tetromino1 = arrayOf(
        arrayOf(1, 0, 0, 0),
        arrayOf(1, 0, 0, 0),
        arrayOf(1, 0, 0, 0),
        arrayOf(1, 0, 0, 0)
    ) // 1*4 가로로 긴 애

    val tetromino2 = arrayOf(
        arrayOf(2, 2),
        arrayOf(2, 2)
    ) // 2*2 정사각형

    val tetromino3 = arrayOf(
        arrayOf(3, 0, 0),
        arrayOf(3, 3, 0),
        arrayOf(0, 3, 0)
    )

    val tetromino4 = arrayOf(
        arrayOf(0, 4, 0),
        arrayOf(4, 4, 0),
        arrayOf(4, 0, 0)
    )

    val tetromino5 = arrayOf(
        arrayOf(5, 0, 0),
        arrayOf(5, 0, 0),
        arrayOf(5, 5, 0)
    )

    val tetromino6 = arrayOf(
        arrayOf(0, 6, 0),
        arrayOf(0, 6, 0),
        arrayOf(6, 6, 0)
    )

    val tetromino7 = arrayOf(
        arrayOf(7, 0, 0),
        arrayOf(7, 7, 0),
        arrayOf(7, 0, 0)
    )

    val block = arrayOf(tetromino1, tetromino2, tetromino3, tetromino4, tetromino5, tetromino6, tetromino7)
}