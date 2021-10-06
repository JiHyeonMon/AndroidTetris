package com.example.androidtetris.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidtetris.model.Game
import com.example.androidtetris.databinding.ActivityMainBinding
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import com.example.androidtetris.model.GameConfig


class MainActivity : AppCompatActivity() {

    /**
     * Data 선언
     */
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private lateinit var paint: Paint

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
        bitmap = Bitmap.createBitmap(GameConfig.CANVAS_WIDTH, GameConfig.CANVAS_HEIGHT, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        binding.imageview.setImageBitmap(bitmap)
        paint = Paint()

        game = Game()
        game.init()
        game.gameStart()
        updateUI()


        // Attach Click Listener
        binding.btnRotate.setOnClickListener {
            game.rotate()
            updateUI()
        }
        binding.btnDown.setOnClickListener {
            game.progress()
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

//        check()
    }

    /**
     * 게임 진행하는 함수
     * Model 값 확인 지속적으로 하며 화면 업데이트
     */
    fun check() {
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {

                // action
                game.progress()

                // update
                updateUI()

                //check game finish


                // 2초마다 움직임
                handler.postDelayed(this, 2000)
            }
        }

        handler.post(runnable)
    }

    /**
     * View에 테트리스 그리는 함수
     * Model에서 새로 반영된 값 가져와 화면에 그려줌
     */
    fun updateUI() {

        // 기존 화면 초기화
        bitmap.eraseColor(Color.WHITE)
        binding.imageview.invalidate()

        // Model에서 테트리스 게임 판 데이터 모두 가져옴
        val block = game.gamePanel

        // 색 정하기
        paint.color = Color.RED

        for (i in block.indices) {
            for (j in block[i].indices) {
                if (block[i][j] != 0) {
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
    }
}