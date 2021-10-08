package com.example.androidtetris.model


object GameConfig {

    // View에서 그리는 테트리스 게임판 CANVAS 크기
    const val CANVAS_WIDTH = 1000
    const val CANVAS_HEIGHT = 1500

    // 테트리스 판 너비와 높이 설정
    // 테트리스 몇 칸 움직일 수 있는지
    const val TETRIS_WIDTH = 10
    const val TERIS_HEIGHT = 15

    // View에서 그리는 테트리스 게임판 옆에 작게 다음 블록 보여줄 CANVAS 크기 지정
    const val NEXT_BLOCK_WIDTH = 200
    const val NEXT_BLOCK_HEIGHT = 200

    // 보드판에 그릴 테트리스 블록 사이즈 설정
    const val BLOCK_SIZE = CANVAS_HEIGHT/TERIS_HEIGHT

    // 다음 블럭으로 보일 블록 사이즈 설정
    const val NEXT_BLOCK_SIZE = BLOCK_SIZE/2

    // 테트리스 점수 지정
    // 블럭을 하나 쌓을 때마다 10점, 수평선 하나 없앨 때마다 100점
    const val BLOCK_SCORE = 10
    const val LINE_SCORE = 100

    // 블럭 떨어지는 속도를 레벨 상수 값으로 맞추기 위해 타이머로 설정할 초를 배열로 미리 생성
    val TIMER = arrayListOf<Long>(0, 1000, 800, 650, 500, 400, 350, 300, 200)
}