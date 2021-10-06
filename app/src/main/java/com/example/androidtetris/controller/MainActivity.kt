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
        bitmap = Bitmap.createBitmap(800, 1200, Bitmap.Config.ARGB_8888)
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

//        check()
    }

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
     */
    fun updateUI() {

        canvas.drawColor(Color.WHITE)
        binding.imageview.setImageBitmap(bitmap)

        val block = game.gamePanel

        // 색 정하기
        paint.color = Color.RED

        for (i in block.indices) {
            for (j in block[i].indices) {
                if (block[i][j] != 0) {
                    val left = 100 * j.toFloat()
                    val top = 100 * i.toFloat()

                    canvas.drawRect(
                        left,
                        top,
                        left + 100,
                        top + 100,
                        paint
                    )
                }
            }
        }

//        canvas.drawRect(block.x.toFloat(), y, x + 100, y + 100, paint)
//        canvas.drawRect(x, y + 100, x + 100, y + 200, paint)
//        canvas.drawRect(x - 100, y + 100, x, y + 200, paint)
//        canvas.drawRect(x - 100, y + 200, x, y + 300, paint)

    }
}