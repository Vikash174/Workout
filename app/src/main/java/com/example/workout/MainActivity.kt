package com.example.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        llStart.setOnClickListener {

            startActivity(Intent(this, ExerciseActivity::class.java))
        }

        llBMI.setOnClickListener {
            startActivity(Intent(this, BMI::class.java))
        }
        llHistory.setOnClickListener {
            startActivity(Intent(this, History::class.java))
        }
    }


}