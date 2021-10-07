package com.example.androidtetris.controller

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.androidtetris.databinding.ActivityMainBinding
import com.example.androidtetris.model.Game
import com.example.androidtetris.model.GameConfig


class MainActivity : AppCompatActivity() {

    /**
     * Data 선언
     */
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private lateinit var paint: Paint

    private lateinit var nextBlockBitmap: Bitmap
    private lateinit var nextCanvas: Canvas
    private lateinit var nextPaint: Paint

    private lateinit var binding: ActivityMainBinding

    private lateinit var game: Game

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    /**
     * Activity LifeCycle Start
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 테트리스 그릴 그림판 초기화
        bitmap = Bitmap.createBitmap(
            GameConfig.CANVAS_WIDTH,
            GameConfig.CANVAS_HEIGHT,
            Bitmap.Config.ARGB_8888
        )
        canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        binding.imageview.setImageBitmap(bitmap)
        paint = Paint()


        nextBlockBitmap = Bitmap.createBitmap(
            GameConfig.NEXT_BLOCK_WIDTH,
            GameConfig.NEXT_BLOCK_HEIGHT,
            Bitmap.Config.ARGB_8888
        )
        nextCanvas = Canvas(nextBlockBitmap)
        nextCanvas.drawColor(Color.WHITE)

        binding.nextBlock.setImageBitmap(nextBlockBitmap)
        nextPaint = Paint()



        game = Game()
        game.init()
        game.gameStart()
        updateUI()


        // Attach Click Listener
        binding.btnRotate.setOnClickListener {
            game.blockRotate()
            updateUI()
        }
        binding.btnDown.setOnClickListener {
            game.moveDown()
            updateUI()
        }
        binding.btnLeft.setOnClickListener {
            game.moveLeft()
            updateUI()
        }
        binding.btnRight.setOnClickListener {
            game.moveRight()
            updateUI()
        }

        check()
    }

    /**
     * 게임 진행하는 함수
     * Model 값 확인 지속적으로 하며 화면 업데이트
     */
    fun check() {
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {


                //check game finish
                if (game.state == Game.GAMESTATE.FINISHED) {
                    gameOver()
                    return
                }

                // action
                game.moveDown()

                // update
                updateUI()

                // 1초마다 움직임
                handler.postDelayed(this, 1000)
            }
        }

        handler.post(runnable)
    }

    /**
     * View에 테트리스 그리는 함수
     * Model에서 새로 반영된 값 가져와 화면에 그려줌
     */
    fun updateUI() {

        binding.textScore.text = game.score.toString()
        // 기존 화면 초기화
        bitmap.eraseColor(Color.WHITE)
        binding.imageview.invalidate()

        // Model에서 테트리스 게임 판 데이터 모두 가져옴
        val block = game.board.matrix

        // 색 정하기

        for (i in block.indices) {
            for (j in block[i].indices) {
                if (block[i][j] > 0) {

                    paint.color = GameConfig.color[block[i][j]]

                    val left = GameConfig.BLOCK_SIZE * j.toFloat()
                    val top = GameConfig.BLOCK_SIZE * i.toFloat()

                    canvas.drawRect(
                        left,
                        top,
                        left + GameConfig.BLOCK_SIZE,
                        top + GameConfig.BLOCK_SIZE,
                        paint
                    )
                }
            }
        }

        nextBlockBitmap.eraseColor(Color.WHITE)
        binding.nextBlock.invalidate()

        // Model에서 테트리스 게임 판 데이터 모두 가져옴
        val nextBlock = game.nextBlock


        for (i in nextBlock.shape.indices) {
            for (j in nextBlock.shape[i].indices) {
                if (nextBlock.shape[i][j] != 0) {
                    val left = GameConfig.NEXT_BLOCK_SIZE * j.toFloat()
                    val top = GameConfig.NEXT_BLOCK_SIZE * i.toFloat()

                    // 색 정하기
                    nextPaint.color = GameConfig.color[nextBlock.shape[i][j]]

                    nextCanvas.drawRect(
                        left,
                        top,
                        left + GameConfig.NEXT_BLOCK_SIZE,
                        top + GameConfig.NEXT_BLOCK_SIZE,
                        nextPaint
                    )
                }
            }
        }
    }

    private fun gameOver() {
        binding.textGameover.isVisible = true
        handler.removeCallbacks(runnable)
        Toast.makeText(this, "게임 종료", Toast.LENGTH_SHORT).show()
    }

}