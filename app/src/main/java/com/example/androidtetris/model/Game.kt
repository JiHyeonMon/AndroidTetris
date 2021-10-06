package com.example.androidtetris.model

import android.util.Log
import java.util.*

class Game {

    /**
     * Data 선언
     */
    enum class GAMESTATE { IN_PROGRESS, FINISHED }

    val gamePanel = Array(GameConfig.TERIS_HEIGHT) { IntArray(GameConfig.TETRIS_WIDTH) }

    private lateinit var state: GAMESTATE

    lateinit var currentBlock: Tetromino
    private lateinit var nextBlock: Tetromino

    /**
     * 초기화
     * 게임 객체 생성 후 변수들 초기화 작업
     */
    fun init() {

        for (i in gamePanel.indices) {
            for (j in gamePanel[i].indices) {
                gamePanel[i][j] = 0
            }
        }

        state = GAMESTATE.FINISHED
        currentBlock = Tetromino()
        nextBlock = Tetromino()
    }

    fun gameStart() {
        init()
        state = GAMESTATE.IN_PROGRESS

        // 블럭 넣음
        drawBlock()
        nextBlock = Tetromino()

    }

    private fun drawBlock() {
        // 첫번째 블럭을 테트리스 판에 넣음
        // 맨 위 && 가운데
        Log.e("tetris's x, y", "${currentBlock.x} ${currentBlock.y}")
        for (i in currentBlock.shape.indices) {
            for (j in currentBlock.shape[i].indices) {
                gamePanel[currentBlock.y+i][currentBlock.x+j] = currentBlock.shape[i][j]
            }
        }

        Log.e("tetris", gamePanel.contentDeepToString())
    }

    fun progress() {

        // 아래 체크 먼저 - stop 조건
        // 블럭의 아래가 바닥에 닿이거나 블록에 닿이거나(그러나 블럭 제일 아래만 보면 안되는게 아래만 뚫려있을 수도 있음)



        // 아래로 한 칸 이동
        moveDown()


        // stop 되면 한번 모든 바닥 스캔 - 없어질 layer 있는지 확인
        // stop 되면 nextBlock이 현재 블럭, 새로운 nextBlock

        // 새로운 블럭 생성
        currentBlock = nextBlock
        nextBlock = Tetromino()
    }

    fun moveDown() {

        clearCurrentBlock()

        // 아래로 내린다.
        currentBlock.y += 1

        // 새로 그린다.
        drawBlock()
    }

    fun rotate() {

        // 돌렸는데 벽을 넘어선다? 안됨


        // 이건 Controller에서 버튼 클릭
        // 현재 테트로미노 rotate

        // 블럭은 돌아감
        currentBlock.rotate()

        // 게임판의 블럭도 돌려야함.
        // currentBlock 의 현재 위치 기준으로
        drawBlock()
    }

    fun clearCurrentBlock(){
        // 이전의 나 지워,,,
        for (i in currentBlock.shape.indices) {
            for (j in currentBlock.shape[i].indices) {
                gamePanel[currentBlock.y+i][currentBlock.x+j] = 0
            }
        }
    }

    fun moveLeft() {
        clearCurrentBlock()
        currentBlock.x -= 1
        drawBlock()
    }

    fun moveRight() {
        clearCurrentBlock()
        currentBlock.x += 1
        drawBlock()

    }

    fun nextTetromino() {

    }

    fun gameOver() {
        // 현재 블럭이 움직일 수 없을 때 == 바닥에 닿이면 종료

    }
}