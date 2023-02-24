package com.bignerdranch.android.geoquizrenkoski

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bignerdranch.android.geoquizrenkoski.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var numCorrect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
           checkAnswer(true)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            if (currentIndex % questionBank.size == 0) {
                computeScore()
            }
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            numCorrect++
            R.string.correct_toast
        }
        else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
     }

    private fun computeScore() {
        val score = (numCorrect.toDouble() / questionBank.size.toDouble()) * 100
        val formattedScore = "%.1f".format(score) + "%"
        Toast.makeText(this, "Your score is $formattedScore", Toast.LENGTH_LONG)
            .show()
        numCorrect = 0
    }
}