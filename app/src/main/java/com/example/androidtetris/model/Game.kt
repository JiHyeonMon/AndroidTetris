package com.example.androidtetris.model

import android.util.Log

class Game {

    /***********************************************************************************************
     * Data 선언
     */
    enum class GAMESTATE { IN_PROGRESS, FINISHED }

    val gamePanel = Array(GameConfig.TERIS_HEIGHT) { Array<Int>(GameConfig.TETRIS_WIDTH) { 0 } }

    private lateinit var state: GAMESTATE

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
    }

    fun gameStart() {
        init()
        state = GAMESTATE.IN_PROGRESS

        // 블럭 넣음
        drawBlock()
        nextBlock = Tetromino()

    }

    fun progress() {
        gamePanel.forEach {
            Log.e("tetris", it.contentToString())
        }

        Log.d("tetris", " ------------------------------------------ ")

        moveDown()

//        if (isValid()) {
//            drawBlock()
//
//        } else {
//
//        }
//        if (isValid("down")) {
//
//            // 아래로 한 칸 이동
//            moveDown()
//            return
//        }

        // 이제 못내림
//        stopBlocked()

        // stop 되면 한번 모든 바닥 스캔 - 없어질 layer 있는지 확인

//        checkClearLine()

        // stop 되면 nextBlock이 현재 블럭, 새로운 nextBlock
        // 새로운 블럭 생성
//        currentBlock = nextBlock
//        nextBlock = Tetromino()
//        drawBlock()
    }

    fun blockRotate() {
        // 이건 Controller에서 버튼 클릭
        // 현재 테트로미노 rotate
        clearCurrentBlock()


        // TODO  // 돌렸는데 벽을 넘어선다? 안됨
        currentBlock.rotate()

//        if (isValid("right") && isValid("left") && isValid("down")) {

        if (isValid()) {
            Log.e("click rotate", "can rotate")
            // 게임판의 블럭도 돌려야함.
            // currentBlock 의 현재 위치 기준으로
            drawBlock()

        } else {
            Log.e("click rotate", "can't rotate")

            // valid 하지 않을 경우 이전 상태로 돌림
            currentBlock.reverseRotate()
            drawBlock()

        }


    }

    fun newBlock() {
        currentBlock = nextBlock
        nextBlock = Tetromino()
        drawBlock()
    }

    fun moveLeft() {

//        if (isValid("left")) {
//            clearCurrentBlock()
//            currentBlock.x -= 1
//            drawBlock()
//        }
        clearCurrentBlock()

        currentBlock.x -= 1
        if (isValid()) {
            drawBlock()
        } else {
            currentBlock.x += 1
            drawBlock()
        }
    }

    fun moveRight() {

//        if (isValid("right")) {
//            clearCurrentBlock()
//            currentBlock.x += 1
//            drawBlock()
//        }
        clearCurrentBlock()

        currentBlock.x += 1
        if (isValid()) {
            drawBlock()
        } else {
            currentBlock.x -= 1
            drawBlock()
        }

    }

    /***********************************************************************************************
     * Private Methods
     */

    private fun drawBlock() {
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

    private fun isValid(direction: String): Boolean {

        for (y in currentBlock.shape.indices) {
            for (x in currentBlock.shape[y].indices) {
                if (currentBlock.shape[y][x] != 0) {

                    Log.e("where am i", "${currentBlock.y + y} ${currentBlock.x + x}")
                    when (direction) {
                        "down" -> {
                            if (currentBlock.y + y + 1 > GameConfig.TERIS_HEIGHT - 1) {
                                Log.e("can't move down", "meet bottom")
                                return false
                            }

                            // 해당 블럭 네모 밑에 0이 아닌 게 있으면 내려갈 수 없다. // 근데 자기 자신은 괜찮음
                            if (gamePanel[currentBlock.y + y + 1][currentBlock.x + x] != currentBlock.shape[y][x] &&
                                gamePanel[currentBlock.y + y + 1][currentBlock.x + x] != 0
                            ) {
                                Log.e("can't move down", "meet other")
                                return false
                            }
                        }
                        "right" -> {
                            if (currentBlock.x + x + 1 > GameConfig.TETRIS_WIDTH - 1) {
                                Log.e("can't move right", "meet wall")
                                return false
                            }

                            // 해당 블럭 네모 밑에 0이 아닌 게 있으면 내려갈 수 없다. // 근데 자기 자신은 괜찮음
                            if (gamePanel[currentBlock.y + y][currentBlock.x + x + 1] != currentBlock.shape[y][x] &&
                                gamePanel[currentBlock.y + y][currentBlock.x + x + 1] != 0
                            ) {
                                Log.e("can't move right", "meet other")
                                return false
                            }
                        }
                        "left" -> {

                            if (currentBlock.x + x - 1 < 0) {
                                Log.e("can't move left", "meet wall")
                                return false
                            }
                            // 해당 블럭 네모 밑에 0이 아닌 게 있으면 내려갈 수 없다. // 근데 자기 자신은 괜찮음
                            if (gamePanel[currentBlock.y + y][currentBlock.x + x - 1] != currentBlock.shape[y][x] &&
                                gamePanel[currentBlock.y + y][currentBlock.x + x - 1] != 0
                            ) {
                                Log.e("can't move right", "meet other")
                                return false
                            }
                        }
                    }

                }
            }
        }
        Log.e("can move", "is valid")
        return true
    }

    private fun moveDown() {

        clearCurrentBlock()

        // 아래로 내린다.
        currentBlock.y += 1
        if (isValid()) {
            drawBlock()
        } else {
            currentBlock.y -= 1
            drawBlock()
            newBlock()
        }

        // 새로 그린다.

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
                if (gamePanel[y][x] != -1) {
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
        // -1로 다 채워진 줄
        Log.e("clear line", "clear!!!!!!!!!!!!!! $y")
        for (i in y - 1..0) {
            gamePanel[i + 1] = gamePanel[i]
        }

        gamePanel[0] = Array(GameConfig.TETRIS_WIDTH) { 0 }
        drawBlock()
//        checkClearLine()

    }

//    private fun stopBlocked() {
//        Log.e("i stop", "set -1 ")
//
//        for (y in gamePanel.indices) {
//            for (x in gamePanel[y].indices) {
//                if (gamePanel[y][x] > 0) {
//                    gamePanel[y][x] = -1
//                }
//            }
//        }
//    }

    fun gameOver() {
        // 현재 블럭이 움직일 수 없을 때 == 바닥에 닿이면 종료

    }
}