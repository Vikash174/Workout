package com.example.workout

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import constants.DatabaseHandler
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_exercise_end.*
import java.text.SimpleDateFormat
import java.util.*

class exerciseEndActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_end)



        finishButton.setOnClickListener {
            finish()
        }


        addDateToDatabase()



    }


   private fun addDateToDatabase(){
       val calendar = Calendar.getInstance()
       val dateTime = calendar.time

       Log.d("DATE: ",""+dateTime)

       val sdf = SimpleDateFormat("dd MM HH:mm:ss",Locale.getDefault())
       val date = sdf.format(dateTime)

       val dbHandler = DatabaseHandler(this,null)
       dbHandler.addDate(date)

   }

}