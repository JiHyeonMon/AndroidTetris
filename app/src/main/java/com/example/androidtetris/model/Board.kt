package com.example.androidtetris.model

import android.util.Log

class Board {

    private val width = GameConfig.TETRIS_WIDTH
    private val height = GameConfig.TERIS_HEIGHT
    val matrix = MutableList(GameConfig.TERIS_HEIGHT) { MutableList(GameConfig.TETRIS_WIDTH) { 0 } }

    init {
        for (i in 0 until height) {
            for (j in 0 until width) {
                matrix[i][j] = 0
            }
        }
    }

    fun arrange(block: Tetromino) {
        Log.e("arrange", "is arrange")

        for (y in block.shape.indices) {
            for (x in block.shape[y].indices) {

                if (block.y + y > GameConfig.TERIS_HEIGHT - 1 ||
                    block.x + x > GameConfig.TETRIS_WIDTH - 1 ||
                    block.x + x < 0
                ) {
                    continue
                }

                if (block.shape[y][x] > 0) {

                    matrix[block.y + y][block.x + x] = block.shape[y][x]
                }
            }
        }
    }

    fun isValid(block: Tetromino): Boolean {
        for (y in block.shape.indices) {
            for (x in block.shape[y].indices) {
                if (block.shape[y][x] != 0) {

                    if (block.y + y > GameConfig.TERIS_HEIGHT - 1 ||
                        block.x + x > GameConfig.TETRIS_WIDTH - 1 ||
                        block.x + x < 0
                    ) {
                        Log.e("isValid", "is not valid1")
                        return false
                    }

                    if (matrix[block.y + y][block.x + x] != 0) {
                        Log.e("isValid", "is not valid2")
                        return false
                    }

                }
            }
        }
        Log.e("isValid", "is valid")
        return true
    }

    fun checkClear(): Int? {
        for (y in 0 until height) {
            var isOccupied = true
            for (x in 0 until width) {
                if (matrix[y][x] == 0) {
                    isOccupied = false
                    break
                }
            }
            if (isOccupied) {
                clearLine(y)
            }
        }
        return null
    }


    fun clearLine(y: Int) {

        matrix.removeAt(y)

        matrix.add(0, MutableList(GameConfig.TETRIS_WIDTH) { 0 })

    }

    fun clearCurrentBlock(block: Tetromino) {

        for (y in block.shape.indices) {
            for (x in block.shape[y].indices) {

                if (block.shape[y][x] > 0) {
                    matrix[block.y + y][block.x + x] = 0
                }
            }
        }
    }
}