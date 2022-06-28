package com.tutorials.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var userName: String? = null
    private var correctAnswerCount: Int = 0

    //

    private var currentPosition: Int = 1
    private var questionList: ArrayList<Question>? = null
    private var selectedPosition: Int = 0
    private var answerCheck: Boolean = false

    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null

    private var btnSubmit: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question_activty)

        userName = intent.getStringExtra(Constants.USER_NAME)

        questionList = Constants.getQuestions()

        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)

        tvOptionOne = findViewById(R.id.tv_optionOne)
        tvOptionTwo = findViewById(R.id.tv_optionTwo)
        tvOptionThree = findViewById(R.id.tv_optionThree)
        tvOptionFour = findViewById(R.id.tv_optionFour)

        btnSubmit = findViewById(R.id.btn_submit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)

        btnSubmit?.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion() {
        val q = questionList!![currentPosition - 1]

        tvQuestion?.text = q.question
        ivImage?.setImageResource(q.image)
        progressBar?.progress = currentPosition
        tvProgress?.text = "${currentPosition}/${questionList?.size}"

        tvOptionOne?.text = q.optionOne
        tvOptionTwo?.text = q.optionTwo
        tvOptionThree?.text = q.optionThree
        tvOptionFour?.text = q.optionFour

        btnSubmit?.text = "SUBMIT"

        defaultOptionView()
    }

    override fun onClick(v: View?) {
        btnSubmit?.text = "SUBMIT"
        when (v?.id) {
            R.id.tv_optionOne -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_optionTwo -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_optionThree -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_optionFour -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_submit -> {
                if (selectedPosition == 0) {
                    Toast.makeText(this, "정답을 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (answerCheck) {
                    // 정답을 확인 했으면 현재 상태를 +1 시킨다.
                    answerCheck = false
                    selectedPosition = 0
                    currentPosition++

                    if (currentPosition <= questionList!!.size) {
                        setQuestion()
                    } else {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, userName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswerCount.toString())
                        intent.putExtra(Constants.TOTAL_QUESTIONS, questionList?.size.toString())
                        startActivity(intent)
                        finish()
                    }

                } else {
                    // 1~4 선택 후 SUBMIT 버튼 클릭시 정답이 확인된다.
                    answerCheck = true
                    val question = questionList?.get(currentPosition - 1)

                    if (question?.correctAnswer != selectedPosition) {
                        answerView(selectedPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        correctAnswerCount++
                    }
                    answerView(question!!.correctAnswer, R.drawable.correct_option_border_bg)

                    if (currentPosition == questionList?.size) {
                        btnSubmit?.text = "FINISH"
                    } else {
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivity,
                    drawableView
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivity,
                    drawableView
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivity,
                    drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        answerCheck = false

        selectedPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()

        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(0, it)
        }
        tvOptionThree?.let {
            options.add(0, it)
        }
        tvOptionFour?.let {
            options.add(0, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }
}