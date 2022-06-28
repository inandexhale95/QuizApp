package com.tutorials.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private var correctAnswers: String? = null
    private var totalQuestions: String? = null

    private var tvUserName: TextView? = null
    private var tvScore: TextView? = null
    private var btnFinish: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvUserName = findViewById(R.id.tv_userName)
        tvScore = findViewById(R.id.tv_score)
        btnFinish = findViewById(R.id.btn_finish)

        correctAnswers = intent.getStringExtra(Constants.CORRECT_ANSWERS)
        totalQuestions = intent.getStringExtra(Constants.TOTAL_QUESTIONS)

        tvUserName?.text = intent.getStringExtra(Constants.USER_NAME)
        tvScore?.text = "Your score is ${correctAnswers} of ${totalQuestions}"

        btnFinish?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}