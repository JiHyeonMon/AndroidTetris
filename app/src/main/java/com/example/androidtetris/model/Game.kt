package com.example.androidtetris.model

import android.util.Log
import com.example.androidtetris.model.Tetromino.Tetromino
import com.example.androidtetris.model.Tetromino.TetrominoFactory

class Game {

    /***********************************************************************************************
     * Data 선언
     */

    // 게임상태를 나타내는 상수 값 클래스
    enum class GAMESTATE { IN_PROGRESS, FINISHED }

    // 게임 상태를 나타낼 변수 state
    lateinit var state: GAMESTATE

    // 게임 점수 score 변수
    var score: Int = 0

    // 게임 레벨 level 변수
    var level: Int = 1

    // 보드 게임 판 객체 board 선언
    lateinit var board: Board

    // 테트로미노 생성할 tetrominoFactory 선언 - 해당 클래스로 테트로미노 랜덤으로 생성할 것
    lateinit var tetrominoFactory: TetrominoFactory

    // 현재 움직일 테트로미노 currentBlock 선언
    lateinit var currentBlock: Tetromino

    // 다음 테트로미노 nextBlock 선언
    lateinit var nextBlock: Tetromino

    /***********************************************************************************************
     * Public Methods
     */

    // lateinit 선언해둔 변수들 초기화 작업
    fun init() {

        // 게임 상태 시작 전 - 종료 상태로 맞춤.
        state = GAMESTATE.FINISHED

        // tetrominoFactory 객체 생성
        tetrominoFactory = TetrominoFactory()
        // board 객체 생성
        board = Board()

        // 현재 블럭과 다음 블럭 tetrominoFactory를 통해 Tetromino 생성한다.
        currentBlock = tetrominoFactory.create()
        nextBlock = tetrominoFactory.create()


        // 초기 점수, 레벨 설정
        score = 0
        level = 1
    }

    fun gameStart() {
        // 실제 게임 시작
        // 게임 상태 IN_PROGRESS
        state = GAMESTATE.IN_PROGRESS

        // 현재 블럭 보드판에 넣고 그린다.
        board.addBlock( currentBlock )
        board.arrange()
    }


    fun moveDown() {
        // 현재 블럭 아래로 한칸 움직이기

        // 블럭이 움직이면 기존의 위치 값 지우고 새 위치로 이동한다.
        // 아래 함수 호출하여 현재 블럭 보드판에서 지운다.
        board.remove()

        // 블럭 위치 아래로 한 칸 움직인다.
        board.block.y += 1

        // 움직인 현재 블럭 값 유효한지 isValid 함수로 검사
        // 유효하지 않으면 기존의 위치로 돌린다.
        if (!board.isValid()) {
            board.block.y -= 1
            board.arrange()

            // 아래로 움직였는데 유효하지 않다는 것은, 이제 해당 블럭 아래로 다 움직였다.
            // 점수 계산
            checkScore()
            // 새 블럭 생성
            newBlock()
        }

        // 움직인 위치 유효하다.
        // 움직인 위치로 그린다.
        board.arrange()

    }

    fun moveLeft() {
        // 현재 블럭 왼쪽으로 한칸 움직이기

        // 블럭이 움직이면 기존의 위치 값 지우고 새 위치로 이동한다.
        board.remove()

        // 현재 블럭 왼쪽으로 한칸 이동
        board.block.x -= 1

        // 왼쪽으로 움직였을 때 유효성 판단.
        if (!board.isValid()) {
            //유효하지 않을 경우 다시 기존 위치로 이동
            board.block.x += 1
        }

        // 현재 블럭 그린다.
        board.arrange()

    }

    fun moveRight() {
        // 현재 블럭 오른쪽으로 한칸 움직이기

        // 블럭이 움직이면 기존의 위치 값 지우고 새 위치로 이동한다.
        board.remove()

        // 현재 블럭 오른쪽으로 한칸 이동
        board.block.x += 1
        // 움직였을 때 유효성 판단.
        if (!board.isValid()) {
            //유효하지 않을 경우 다시 기존 위치로 이동
            board.block.x -= 1
        }

        // 현재 블럭 그린다.
        board.arrange()
    }

    fun blockRotate() {
        // 현재 블럭 시계방향으로 회전

        // 블럭이 움직이면 기존의 위치 값 지우고 새 위치로 이동한다.
        board.remove()

        // 현재 블록 시계방향 회전
        board.block.rotate()
        // 회전 시켰을 때 유효성 판단
        if (!board.isValid()) {
            // 유효하지 않을 경우, 반시계로 돌려 기존 상태로 돌림
            board.block.reverseRotate()
        }

        // 현재 블럭 그린다.
        board.arrange()

    }

    /***********************************************************************************************
     * Private Methods
     */

    // 현재 블럭이 다 내려왔을 때, 점수 계산
    private fun checkScore() {

        // 한 블럭당 GameConfig.BLOCK_SCORE(10점)씩 제공
        // 난이도가 높아질 수록, 난이도에 곱한 값 점수로 부여
        score += GameConfig.BLOCK_SCORE * level

        // 지울 수 있는 수평선 있는지 검사
        board.checkClear().apply {
            // 수평선 한 줄 당 GameConfig.LINE_SCORE(100점) 에 레벨 곱한 값 점수로 제공
            score += this * GameConfig.LINE_SCORE * level
        }

        // 점수에 따라 레벨 상승
        // 레벨 상승할 수록 점수 쉽게 모임 - 블럭 내려오는 속도 빨라짐
        level = when (score) {
            in 0..100 -> 1
            in 101..300 -> 2
            in 301..700 -> 3
            in 701..1000 -> 4
            in 1001..1500 -> 5
            in 1501..2500 -> 6
            else -> 7
        }
    }

    // 현재 블럭 다 내려왔다면 새로운 블럭 보드판에 등장
    private fun newBlock() {
        // 기존의 다음 블럭을 현재 블럭으로 변경
        currentBlock = nextBlock
        board.addBlock( currentBlock )
        // 새로운 블럭 tetrominoFactory통해 생성
        nextBlock = tetrominoFactory.create()

        // 현재 블럭 유효성 검사
        if (!board.isValid()) {
            // 유효하지 않다는 건 움직일 수 없다.
            // 기존 보드판 블록들 다 차서 움직일 수 없다.
            // 게임 오버
            gameOver()
        }

        // 유효하다 - 보드판에 새 블록으로 그린다.
        board.arrange()

    }

    private fun gameOver() {
        // 블럭들로 가득 차서 현재 블럭이 움직일 수 없을 때 게임 종료
        state = GAMESTATE.FINISHED
        Log.e("gameOver", "over")
    }
}