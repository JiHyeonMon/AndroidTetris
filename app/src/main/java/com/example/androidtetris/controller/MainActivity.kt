package com.example.androidtetris.controller

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.androidtetris.databinding.ActivityMainBinding
import com.example.androidtetris.model.Game
import com.example.androidtetris.model.GameConfig
import com.example.androidtetris.model.Tetromino.*


class MainActivity : AppCompatActivity() {

    /**
     * Data 선언
     */
    // view에 Canvas 만들어서 게임판 그려주기 위해 bitmap, canvas, paint 사용할 것
    private lateinit var gameBoardBitmap: Bitmap
    private lateinit var gameBoardCanvas: Canvas
    private lateinit var gameBoardPaint: Paint

    // view에 Canvas 만들어서 다음 블럭 그려주기 위해 bitmap, canvas, paint 사용할 것
    private lateinit var nextBlockBitmap: Bitmap
    private lateinit var nextBlockCanvas: Canvas
    private lateinit var nextBlockPaint: Paint

    // 실제 게임과 관련된 데이터를 가지고 있는 모델 선언
    private lateinit var game: Game

    // View 객체를 참조하기 위해 ViewBinding 사용
    // binding 객체 선언
    private lateinit var binding: ActivityMainBinding

    // Model을 지속적으로 관찰하고 View를 업데이트 시키기 위한 runnable객체와 Handler
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    /**
     * Activity LifeCycle Start
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Reference View to use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 앞서 lateinit으로 선언해둔 Canvas 관련 변수들, 그림그리기 위한 초기화 작업
        initial()

        // 게임 생성
        game = Game()
        // 게임에 필요한 데이터 초기화 작업
        game.init()

        // Attach Click Listener
        binding.btnRotate.setOnClickListener {
            // 블럭 회전하기
            game.blockRotate()
            // 회전 시킨 모양 View에 업데이트
            updateUI()
        }
        binding.btnDown.setOnClickListener {
            // 블럭 내리기
            game.moveDown()
            // 화면 업데이트
            updateUI()
        }
        binding.btnLeft.setOnClickListener {
            // 블럭 왼쪽 이동
            game.moveLeft()
            updateUI()
        }
        binding.btnRight.setOnClickListener {
            // 블럭 오른쪽 이동
            game.moveRight()
            updateUI()
        }


        // 게임 시작
        game.gameStart()

        // 생성된 블럭 화면에 그리기
        updateUI()

        // 게임 진행
        progress()
    }

    // View 에 나타낼 그림판 초기화
    private fun initial() {
        // 테트리스 그릴 그림판 초기화 -----------------------------
        // 크기 지정
        gameBoardBitmap = Bitmap.createBitmap(
            GameConfig.CANVAS_WIDTH,
            GameConfig.CANVAS_HEIGHT,
            Bitmap.Config.ARGB_8888
        )
        // 캔버스 생성 및 색상 지정
        gameBoardCanvas = Canvas(gameBoardBitmap)
        gameBoardCanvas.drawColor(Color.WHITE)
        gameBoardPaint = Paint()
        // View에 비트맵으로 지정하기
        binding.gameBoard.setImageBitmap(gameBoardBitmap)

        // 다음 블럭 그릴 그림판 초기화 --------------------------
        // 크기 지정
        nextBlockBitmap = Bitmap.createBitmap(
            GameConfig.NEXT_BLOCK_WIDTH,
            GameConfig.NEXT_BLOCK_HEIGHT,
            Bitmap.Config.ARGB_8888
        )
        // 캔버스 생성 및 색상 지정
        nextBlockCanvas = Canvas(nextBlockBitmap)
        nextBlockCanvas.drawColor(Color.WHITE)
        nextBlockPaint = Paint()
        // View에 비트맵으로 지정하기
        binding.nextBlock.setImageBitmap(nextBlockBitmap)

    }

    /**
     * 게임 진행하는 함수
     * Model 값 확인 지속적으로 하며 화면 업데이트
     */
    private fun progress() {
        // 게임 진행
        // 정해둔 타이머 시간 간격으로 한칸 씩 블럭이 내려온다.

        handler = Handler()
        runnable = object : Runnable {
            override fun run() {

                // Check Game State
                // 게임 종료인지 확인한다.
                if (game.state == Game.GAMESTATE.FINISHED) {
                    gameOver()
                    return
                }

                // action
                // 블럭이 한 칸 내려온다.
                game.moveDown()

                // update
                // 블럭 내려온 거 화면 업데이트 한다.
                updateUI()

                // 정해둔 타이머 시간 간격으로 반복 - 레벨이 올라갈 수록 시간이 짧아짐.
                handler.postDelayed(this, GameConfig.TIMER[game.level])
            }
        }

        handler.post(runnable)
    }

    /**
     * View에 테트리스 그리는 함수
     * Model에서 새로 반영된 값 가져와 화면에 그려줌
     */
    fun updateUI() {

        // Model에서 점수와 레벨 값 가져와 View에 업데이트
        binding.textScore.text = game.score.toString()
        binding.textLevel.text = game.level.toString()

        // View 의 테트리스 보드판 업데이트
        updateGameBoard()

        // View의 다음 블럭 보이는 화면 업데이트
        updateNextBlock()
    }

    // 테트리스 보드판 화면 업데이트
    private fun updateGameBoard() {
        // 기존 화면 초기화 - (이전의 블럭에서 움직임 -> 이전 블럭 지우고 그릴 필요 있다.)
        gameBoardBitmap.eraseColor(Color.WHITE)
        binding.gameBoard.invalidate()

        // Model에서 테트리스 게임 판 배열 데이터 모두 가져옴
        val block = game.board.board

        // 배열 전부 돌면서 숫자 있는 칸 확인
        for (y in block.indices) {
            for (x in block[y].indices) {
                if (block[y][x] > 0) {
                    // 숫자 있는 칸 - 블럭이 있는 부분

                    // 색 정하기
                    // 블럭마다 색상이 다르다.
                    // 해당 칸의 숫자를 보고 어떤 블럭인지 확인 후 해당 블럭의 맞는 색상으로 맞춘다.
                    gameBoardPaint.color = when (block[y][x]) {
                        1 -> ITetromino().color
                        2 -> JTetromino().color
                        3 -> LTetromino().color
                        4 -> OTetromino().color
                        5 -> STetromino().color
                        6 -> TTetromino().color
                        7 -> ZTetromino().color
                        else -> OTetromino().color
                    }

                    // 해당 블럭 한 칸! 위치 가져온다.
                    // 가져온 x, y 좌표는 좌표. 실제 게임 보드는 사각형으로 너비를 가진다.
                    // 그리기 시작할 위치 left, top 위치는 가져온 좌표에 블럭 사이즈 곱한 값.
                    val left = GameConfig.BLOCK_SIZE * x.toFloat()
                    val top = GameConfig.BLOCK_SIZE * y.toFloat()

                    // 사각형으로 그린다.
                    // 가져온 위치 좌표 기준으로 블럭 사이즈 만큼 크기로 가로 세로 사각형을 그린다.
                    gameBoardCanvas.drawRect(
                        left,
                        top,
                        left + GameConfig.BLOCK_SIZE,
                        top + GameConfig.BLOCK_SIZE,
                        gameBoardPaint
                    )
                }
            }
        }
    }

    // 다음 테트리스 블록 화면 업데이트
    private fun updateNextBlock() {
        // 기존 화면 초기화
        nextBlockBitmap.eraseColor(Color.WHITE)
        binding.nextBlock.invalidate()

        // Model에서 다음 테트로미노 배열 데이터 가져옴
        val nextBlock = game.nextBlock

        // 테트로미노 블럭 4*4, 3*3, 2*2 형태
        // 배열 돌면서 값이 있는 부분을 그리면 된다.
        for (i in nextBlock.shape.indices) {
            for (j in nextBlock.shape[i].indices) {
                if (nextBlock.shape[i][j] != 0) {
                    // 값이 있는 부분 좌표를 가지고 와서 그린다.
                    val left = GameConfig.NEXT_BLOCK_SIZE * j.toFloat()
                    val top = GameConfig.NEXT_BLOCK_SIZE * i.toFloat()

                    // 해당 다음 테트로미노에 해당하는 색으로 그린다.
                    nextBlockPaint.color = nextBlock.color

                    // 사각형으로 그린다.
                    // 가져온 위치 좌표 기준으로 블럭 사이즈 만큼 크기로 가로 세로 사각형을 그린다.
                    nextBlockCanvas.drawRect(
                        left,
                        top,
                        left + GameConfig.NEXT_BLOCK_SIZE,
                        top + GameConfig.NEXT_BLOCK_SIZE,
                        nextBlockPaint
                    )
                }
            }
        }
    }

    private fun gameOver() {
        // 게임이 종료되면
        // 게임 종료 메시지 visible로 바꾸기
        binding.textGameover.isVisible = true
        // 움직이던 핸들러 runnable 제거
        handler.removeCallbacks(runnable)
    }

}