package com.example.androidtetris.model.Tetromino

import android.graphics.Color
import com.example.androidtetris.model.GameConfig

open class Tetromino {

    // 현재 위치를 나타내는 x, y
    // 기본 위치 : x 값 - 테트리스 판의 가운데 위치, y 값 - 테트리스 판의 제일 위
    var x: Int = GameConfig.TETRIS_WIDTH / 2 - 1
    var y: Int = 0

    // 각 테트로미노 별로 색상을 가진다.
    var color: Int = 0

    // 몇 by 몇 사이즈의 모양인지 나타낼
    var shape: Array<Array<Int>> = arrayOf()


    // 시계방향 회전 (Default)
    // 각 테트로미노는 회전할 수 있다.
    fun rotate() {
        // 회전한 테트로미노를 넣을 배열 새로 만든다.
        val rotateTetromino = Array(shape.size) { Array(shape.size) { 0 } }

        // 시계방향으로 새로운 배열에 기존 값 넣는다.
        // 012     630
        // 345  -> 741
        // 678     852
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                rotateTetromino[i][j] = shape[shape.size - 1 - j][i]
            }
        }

        // 회전한 테트로미노를 기존 모양에 넣어준다.
        shape = rotateTetromino
    }

    // 반시계 방향 회전
    // 해당 반시계 회전은 시계 회전을 기본으로 회전할 때, 회전할 수 없는 상황이라면 반시계로 돌려 원래 상태로 만든다.
    fun reverseRotate() {
        val rotateTetromino = Array(shape.size) { Array(shape.size) { 0 } }

        // 반시계방향으로 새로운 배열에 기존 값 넣는다.
        // 012     258
        // 345  -> 147
        // 678     036
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                rotateTetromino[shape.size-1-j][i] = shape[i][j]
            }
        }

        // 회전한 테트로미노를 기존 모양에 넣어준다.
        shape = rotateTetromino
    }
}
