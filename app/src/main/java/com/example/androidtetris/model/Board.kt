package com.example.androidtetris.model

import android.util.Log
import com.example.androidtetris.model.Tetromino.Tetromino

class Board {

    // 테트리스 게임판의 가로 세로 칸 지정
    private val width = GameConfig.TETRIS_WIDTH
    private val height = GameConfig.TERIS_HEIGHT

    // 보드판 - 2차원 배열의 가로 세로 길이 맞춰 0으로 세팅
    // 초기 보드판엔 아무 블럭도 들어오지 않은 모두 0으로 차있다.
    val board = MutableList(height) { MutableList(width) { 0 } }
    lateinit var block: Tetromino

    fun addBlock(b: Tetromino) {
        block = b
    }

    // 보드판에 현재 블럭 그린다.
    fun arrange() {
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

                    board[block.y + y][block.x + x] = block.shape[y][x]
                }
            }
        }
    }

    // 현재 블럭이 움직일 때, 보드판 상에서 유효한지 검사
    // 일단 움직인 값 제공!

    // 1. 현재 블럭이 오른쪽/왼쪽 벽을 넘었는지
    // 2. 현재 블럭이 바닥에 닿였는지
    // 3. 현재 블럭이 다른 블럭과 겹치는지
    fun isValid(): Boolean {
        for (y in block.shape.indices) {
            for (x in block.shape[y].indices) {
                if (block.shape[y][x] != 0) {

                    if (block.y + y > GameConfig.TERIS_HEIGHT - 1 || // 2번 검사
                        block.x + x > GameConfig.TETRIS_WIDTH - 1 || // 1번 검사 - 오른쪽 벽
                        block.x + x < 0 // 1번 검사 - 왼쪽 벽
                    ) {
                        return false
                    }

                    // 3번 검사 - 다른 블럭과 겹치는지
                    if (board[block.y + y][block.x + x] != 0) {
                        return false
                    }
                }
            }
        }

        // 유효하다.
        // 해당 블럭 움직일 수 있다.
        return true
    }

    // 보드판에서 수평선 지울 수 있는지 확인 및 지우기
    // 블럭이 더 이상 움직일 수 없을 때, 실행된다.
    fun checkClear(): Int {
        // 지운 선만큼 점수에 반영되기에, 몇 줄을 지웠는지 판단 필요하다. clearLine이라는 정수 변수를 생성
        var clearLine = 0

        // 모든 보드판 칸을 검사한다.
        for (y in 0 until height) {
            // 한 줄마다 isOccupied 라는 boolean 변수를 true로 할당
            // 한 줄에 0 이 있다면 지울 수 없다. - fasle로 반복문 나옴. - 더 확인할 필요 없다.
            // 한 줄에 0이 없다면 다 찼다 - 지울 수 있다. - true
            var isOccupied = true
            for (x in 0 until width) {
                // 해당 수평선의 칸 검사하며 0이 있다면 한 줄 다 안찼기에 false로 해당 수평선 검사 끝
                if (board[y][x] == 0) {
                    isOccupied = false
                    break
                }
            }
            // 한 줄의 수평선 검사 마쳤는데 isOccupied 값 그대로 true면 다 블럭으로 차 있다는 말이므로 해당 수평선 지운다.
            if (isOccupied) {
                // 지운 선 수 반영
                clearLine += 1
                // 해당 수평선 삭제
                board.removeAt(y)
                // 새로 맨 위에 0으로 찬 수평선 추가
                board.add(0, MutableList(GameConfig.TETRIS_WIDTH) { 0 })
            }
        }
        // 총 몇 줄 지워졌는지 반환
        return clearLine
    }


    // 블럭이 움직일 때마다 기존의 블럭 지우고 다시 그린다.
    // 움직이기 전에 해당 함수 호출해 해당 블럭의 움직이기 이전의 값 지운다.
    fun remove() {

        // 4*4, 3*3, 2*2 블럭 칸 만큼 돌면서 값이 있는 곳 확인
        for (y in block.shape.indices) {
            for (x in block.shape[y].indices) {

                if (block.shape[y][x] > 0) {
                    // 숫자 있는 칸
                    // 기존의 블럭 있던 칸 0으로 해서 블럭 지운다
                    board[block.y + y][block.x + x] = 0
                }
            }
        }
    }
}