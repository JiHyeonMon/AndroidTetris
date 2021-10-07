package com.example.androidtetris.model

import android.graphics.Color

object GameConfig {

    // 테트리스 판 너비와 높이 설정
    const val TETRIS_WIDTH = 8
    const val TERIS_HEIGHT = 10

    const val CANVAS_WIDTH = 800
    const val CANVAS_HEIGHT = 1000

    const val NEXT_BLOCK_WIDTH = 50
    const val NEXT_BLOCK_HEIGHT = 50

    const val BLOCK_SIZE = CANVAS_HEIGHT/TERIS_HEIGHT
    const val NEXT_BLOCK_SIZE = BLOCK_SIZE/8

    val ITETROMINO = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(1, 1, 1, 1),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    ) // 1*4 가로로 긴 애

    val OTETROMINO = arrayOf(
        arrayOf(2, 2),
        arrayOf(2, 2)
    ) // 2*2 정사각형

    val STETROMINO = arrayOf(
        arrayOf(0, 3, 3),
        arrayOf(3, 3, 0),
        arrayOf(0, 0, 0)
    )

    val ZTETROMINO = arrayOf(
        arrayOf(4, 4, 0),
        arrayOf(0, 4, 4),
        arrayOf(0, 0, 0)
    )

    val JTETROMINO = arrayOf(
        arrayOf(5, 0, 0),
        arrayOf(5, 5, 5),
        arrayOf(0, 0, 0)
    )

    val LTETROMINO = arrayOf(
        arrayOf(0, 0, 6),
        arrayOf(6, 6, 6),
        arrayOf(0, 0, 0)
    )

    val TTETROMINO = arrayOf(
        arrayOf(0, 7, 0),
        arrayOf(7, 7, 7),
        arrayOf(0, 0, 0)
    )

    val block = arrayOf(ITETROMINO, OTETROMINO, STETROMINO, ZTETROMINO, JTETROMINO, LTETROMINO, TTETROMINO)
    val color = arrayOf(Color.BLACK, Color.rgb(47.055F, 55.289F, 86.785F), Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE, Color.rgb(255,69,0), Color.MAGENTA)
}