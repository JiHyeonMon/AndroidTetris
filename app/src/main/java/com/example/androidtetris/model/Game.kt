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

        // 0 이 아닌 실제 블럭들만 그린다!!!!

        /**
         * 값이 있는 애들만 넣어야 하는데
         *
         * 010
         * 110
         * 100
         *
         * 0 도 같이 씌워져서 기존의 블럭들 0이 덮어서 지워짐,,
         */
        for (y in currentBlock.shape.indices) {
            for (x in currentBlock.shape[y].indices) {
                if (currentBlock.shape[y][x] > 0) {

                    gamePanel[currentBlock.y + y][currentBlock.x + x] = currentBlock.shape[y][x]
                }
            }
        }

        gamePanel.forEach {
            Log.e("tetris", it.contentToString())
        }

        Log.d("tetris", " ------------------------------------------ ")
    }

    fun progress() {

        // 아래 체크 먼저 - stop 조건
        // 블럭의 아래가 바닥에 닿이거나

        // 블록에 닿이거나(그러나 블럭 제일 아래만 보면 안되는게 아래만 뚫려있을 수도 있음)
//        if (gamePanel[currentBlock.y + currentBlock.shape.size + 1][])
        if (isInvalidateDown()) {

            // 아래로 한 칸 이동
            moveDown()
            drawBlock()
            return
        }


        // stop 되면 한번 모든 바닥 스캔 - 없어질 layer 있는지 확인
        clear()

        // stop 되면 nextBlock이 현재 블럭, 새로운 nextBlock
        // 새로운 블럭 생성
        currentBlock = nextBlock
        nextBlock = Tetromino()
        drawBlock()
    }

    private fun isInvalidateDown(): Boolean {
        // 내릴 수 있어야 내린다.

        // 아래 다른 블럭이 있는지 체크
        for (y in currentBlock.shape.indices) {
            for (x in currentBlock.shape[y].indices) {
                if (currentBlock.shape[y][x] != 0) {


                    if (currentBlock.y + y + 1 == GameConfig.TERIS_HEIGHT) {
                        for (y in currentBlock.shape.indices) {
                            for (x in currentBlock.shape[y].indices) {
                                if (gamePanel[currentBlock.y + y][currentBlock.x + x] != 0) {

                                    gamePanel[currentBlock.y + y][currentBlock.x + x] = -1
                                }
                            }
                        }
                        return false
                    }

                    // 해당 블럭 네모 밑에 0이 아닌 게 있으면 내려갈 수 없다. // 근데 자기 자신은 괜찮음
                    if (gamePanel[currentBlock.y + y + 1][currentBlock.x + x] != currentBlock.shape[y][x] && gamePanel[currentBlock.y + y + 1][currentBlock.x + x] != 0) {
                        Log.e(
                            "meet other",
                            gamePanel[currentBlock.y + y][currentBlock.x + x].toString()
                        )

                        // 내려갈 수 없음
                        for (y in currentBlock.shape.indices) {
                            for (x in currentBlock.shape[y].indices) {
                                if (gamePanel[currentBlock.y + y][currentBlock.x + x] != 0) {

                                    gamePanel[currentBlock.y + y][currentBlock.x + x] = -1
                                }
                            }
                        }

                        return false
                    }
                }
            }
        }




        return true
    }

    private fun moveDown() {

        clearCurrentBlock()

        // 아래로 내린다.
        currentBlock.y += 1

        // 새로 그린다.
        drawBlock()

    }

    // TODO  // 돌렸는데 벽을 넘어선다? 안됨
    fun rotate() {

        clearCurrentBlock()


        // 이건 Controller에서 버튼 클릭
        // 현재 테트로미노 rotate

        // 블럭은 돌아감
        currentBlock.rotate()

        // 게임판의 블럭도 돌려야함.
        // currentBlock 의 현재 위치 기준으로
        drawBlock()
    }

    private fun clearCurrentBlock() {
        // 이전의 나 지워,,,
        for (i in currentBlock.shape.indices) {
            for (j in currentBlock.shape[i].indices) {
                // 이거다
                    // 내가 move 하면 움직이고 이전의 나는 지우는데 0으로 초기화 필요, 기존의 나 지우고 다시 그릴거기 때문
                        // 근데 여기서 내가 아닌 다른 쌓인 블럭이 있을 수 있다. 그 친구들은 안건들기 위해
                            // 기존의 나 였던!! 애만 지운다.
                if (gamePanel[currentBlock.y + i][currentBlock.x + j] == currentBlock.shape[i][j]) {
                    gamePanel[currentBlock.y + i][currentBlock.x + j] = 0
                }
            }
        }
    }


    // TODO Move Right, Left 내 블럭 사이즈로 판별하는데 0으로 채워진 경우 움직일 수 있어야 함.
    fun moveLeft() {

        if (currentBlock.x > 0) {
            clearCurrentBlock()
            currentBlock.x -= 1
            drawBlock()
        }
    }

    fun moveRight() {
        if (currentBlock.x + currentBlock.shape.size < GameConfig.TETRIS_WIDTH) {
            clearCurrentBlock()
            currentBlock.x += 1
            drawBlock()
        }

    }

    fun clear() {

        for (y in gamePanel.indices) {
            for(x in gamePanel[y].indices) {
                if (gamePanel[y][x]!= -1) {
                    return
                }
            }
            // 한 줄 다 찬 -1 !!! 지운다.
            gamePanel[y] = gamePanel[y-1]
            gamePanel[0] = IntArray(GameConfig.TETRIS_WIDTH) { 0 }
        }
    }

    fun gameOver() {
        // 현재 블럭이 움직일 수 없을 때 == 바닥에 닿이면 종료

    }
}