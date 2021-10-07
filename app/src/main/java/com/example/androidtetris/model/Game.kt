package com.example.androidtetris.model

import android.util.Log

class Game {

    /***********************************************************************************************
     * Data 선언
     */
    enum class GAMESTATE { IN_PROGRESS, FINISHED }


    lateinit var state: GAMESTATE
    var score: Int = 0

    lateinit var board : Board
    lateinit var currentBlock: Tetromino
    lateinit var nextBlock: Tetromino

    /***********************************************************************************************
     * Public Methods
     */

    // 초기화
    // 게임 객체 생성 후 변수들 초기화 작업
    fun init() {

        state = GAMESTATE.FINISHED
        board = Board()
        currentBlock = Tetromino()
        nextBlock = Tetromino()

        score = 0
    }

    fun gameStart() {
        init()
        state = GAMESTATE.IN_PROGRESS

        // 블럭 넣음
        board.arrange(currentBlock)

    }

    fun moveDown() {

        board.clearCurrentBlock(currentBlock)

        // 아래로 내린다.
        currentBlock.y += 1
        if (!board.isValid(currentBlock)) {
            currentBlock.y -= 1
            board.arrange(currentBlock)

            score += 10

            board.checkClear()

            newBlock()
        }
        board.arrange(currentBlock)

    }

    fun moveLeft() {

        board.clearCurrentBlock(currentBlock)

        currentBlock.x -= 1
        if (!board.isValid(currentBlock)) {
            currentBlock.x += 1
        }

        board.arrange(currentBlock)

    }

    fun moveRight() {

        board.clearCurrentBlock(currentBlock)
        currentBlock.x += 1
        if (!board.isValid(currentBlock)) {
            currentBlock.x -= 1
        }
        board.arrange(currentBlock)
    }

    fun blockRotate() {
        // 이건 Controller에서 버튼 클릭
        // 현재 테트로미노 rotate
        board.clearCurrentBlock(currentBlock)

        currentBlock.rotate()
        if (!board.isValid(currentBlock)) {// valid 하지 않을 경우 이전 상태로 돌림
            currentBlock.reverseRotate()
        }

        board.arrange(currentBlock)

    }

    private fun newBlock() {
        currentBlock = nextBlock
        nextBlock = Tetromino()

        if (!board.isValid(currentBlock)) {
            gameOver()
        }

        board.arrange(currentBlock)

    }

    private fun gameOver() {
        // 현재 블럭이 움직일 수 없을 때 == 바닥에 닿이면 종료
        state = GAMESTATE.FINISHED
        Log.e("gameOver", "over")
    }
}