package com.example.androidtetris.model

import android.util.Log

class Game {

    /***********************************************************************************************
     * Data 선언
     */
    enum class GAMESTATE { IN_PROGRESS, FINISHED }


    lateinit var state: GAMESTATE
    var score: Int = 0

    val gamePanel =
        MutableList(GameConfig.TERIS_HEIGHT) { MutableList<Int>(GameConfig.TETRIS_WIDTH) { 0 } }

    lateinit var currentBlock: Tetromino
    lateinit var nextBlock: Tetromino

    /***********************************************************************************************
     * Public Methods
     */

    // 초기화
    // 게임 객체 생성 후 변수들 초기화 작업
    fun init() {

        for (i in gamePanel.indices) {
            for (j in gamePanel[i].indices) {
                gamePanel[i][j] = 0
            }
        }

        state = GAMESTATE.FINISHED
        currentBlock = Tetromino()
        nextBlock = Tetromino()

        score = 0
    }

    fun gameStart() {
        init()
        state = GAMESTATE.IN_PROGRESS

        // 블럭 넣음
        arrange()
        nextBlock = Tetromino()

    }

    fun blockRotate() {
        // 이건 Controller에서 버튼 클릭
        // 현재 테트로미노 rotate
        clearCurrentBlock()


        // TODO  // 돌렸는데 벽을 넘어선다? 안됨
        currentBlock.rotate()

        if (isValid()) {
            Log.e("click rotate", "can rotate")
            // 게임판의 블럭도 돌려야함.
            // currentBlock 의 현재 위치 기준으로
            arrange()

        } else {
            Log.e("click rotate", "can't rotate")

            // valid 하지 않을 경우 이전 상태로 돌림
            currentBlock.reverseRotate()
            arrange()

        }
    }

    private fun newBlock() {
        currentBlock = nextBlock
        nextBlock = Tetromino()

        if (isValid()) {
            arrange()
        } else {
            gameOver()
        }

    }

    fun moveDown() {

        clearCurrentBlock()

        // 아래로 내린다.
        currentBlock.y += 1
        if (!isValid()) {
            currentBlock.y -= 1
            arrange()

            score += 10
            checkClearLine()
            newBlock()
        }
        arrange()

    }

    fun moveLeft() {

        clearCurrentBlock()

        currentBlock.x -= 1
        if (!isValid()) {
            currentBlock.x += 1
        }
        arrange()

    }

    fun moveRight() {

        clearCurrentBlock()
        currentBlock.x += 1
        if (!isValid()) {
            currentBlock.x -= 1
        }
        arrange()
    }

    /***********************************************************************************************
     * Private Methods
     */

    private fun arrange() {
        // 첫번째 블럭을 테트리스 판에 넣음
        // 맨 위 && 가운데

        // 0 이 아닌 실제 블럭들만 그린다!!!!
        for (y in currentBlock.shape.indices) {
            for (x in currentBlock.shape[y].indices) {

                if (currentBlock.y + y > GameConfig.TERIS_HEIGHT - 1 ||
                    currentBlock.x + x > GameConfig.TETRIS_WIDTH - 1 ||
                    currentBlock.x + x < 0
                ) {
                    continue
                }

                if (currentBlock.shape[y][x] > 0) {

                    gamePanel[currentBlock.y + y][currentBlock.x + x] = currentBlock.shape[y][x]
                }
            }
        }
    }

    private fun isValid(): Boolean {
        for (y in currentBlock.shape.indices) {
            for (x in currentBlock.shape[y].indices) {
                if (currentBlock.shape[y][x] != 0) {

                    if (currentBlock.y + y > GameConfig.TERIS_HEIGHT - 1 ||
                        currentBlock.x + x > GameConfig.TETRIS_WIDTH - 1 ||
                        currentBlock.x + x < 0
                    ) {

                        Log.e("is not valid1", "can't move")
                        return false
                    }

                    if (gamePanel[currentBlock.y + y][currentBlock.x + x] != 0) {
                        Log.e("is not valid2", "can't move")
                        return false
                    }

                }
            }
        }
        Log.e("is valid", "can move")

        return true
    }

    private fun clearCurrentBlock() {

        for (y in currentBlock.shape.indices) {
            for (x in currentBlock.shape[y].indices) {

                if (currentBlock.shape[y][x] > 0) {
                    gamePanel[currentBlock.y + y][currentBlock.x + x] = 0
                }
            }
        }
    }

    private fun checkClearLine() {
        for (y in gamePanel.indices) {
            var isOccupied = true
            for (x in gamePanel[y].indices) {
                if (gamePanel[y][x] == 0) {
                    isOccupied = false
                    break
                }
            }
            if (isOccupied) {
                clearLine(y)
            }
        }
    }

    private fun clearLine(y: Int) {
        gamePanel.forEach {
            Log.e("tetris", it.joinToString(" "))
        }
        gamePanel.removeAt(y)

        gamePanel.add(0, MutableList(GameConfig.TETRIS_WIDTH) { 0 })
        checkClearLine()
        gamePanel.forEach {
            Log.e("tetris", it.joinToString(" "))
        }

        score += 100

    }

    private fun gameOver() {
        // 현재 블럭이 움직일 수 없을 때 == 바닥에 닿이면 종료
        state = GAMESTATE.FINISHED
        Log.e("gameOver", "over")
    }
}