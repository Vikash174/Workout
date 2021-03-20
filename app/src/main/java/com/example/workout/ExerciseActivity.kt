package com.example.workout

import Model.ExerciseModel
import adapter.ExerciseStatusAdapter
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.R
import constants.Constants
import kotlinx.android.synthetic.main.activity_exercise.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //Variable for timer which will be initialized later
    private var restTimer: CountDownTimer? = null

    //the duration of the timer is in milliseconds
    private var restProgress = 0


    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null // variable for TextToSpeech
    private var textToSpeech: String? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        llExerciseView.visibility = View.INVISIBLE

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)


        toolbar_exercise_activity.setNavigationOnClickListener {
            alertDialogFunction()

        }

        setupRestView()


        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this, this)

        setupExerciseStatusRecyclerView()

    }

    private fun setRestProgressbar() {
        currentExercisePosition++

        progressbar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                restProgress++

                progressbar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()

                if ((10 - restProgress) < 4) {
                    speakOut((10 - restProgress).toString())
                }

//
                textToSpeech = exerciseList!![currentExercisePosition].getName()
                upcoming_exercise.text = exerciseList!![currentExercisePosition].getName()


            }


            override fun onFinish() {

                llRestView.visibility = View.INVISIBLE
                llExerciseView.visibility = View.VISIBLE
                exerciseProgress = 0
                setUpExerciseView()
                speakOut(exerciseList!![currentExercisePosition].getName())
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

            }

        }.start()
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    private fun setExerciseProgressbar() {
        exerciseProgressbar.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                exerciseProgress++

                exerciseProgressbar.progress = 30 - exerciseProgress
                exerciseTimerTv.text = (30 - exerciseProgress).toString()
                if ((30 - exerciseProgress) < 4) {
                    speakOut((30 - exerciseProgress).toString())
                }


            }

            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! - 1) {

                    restProgress = 0
                    setupRestView()


                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                } else {

                    finish()
                    val intent = Intent(this@ExerciseActivity, exerciseEndActivity::class.java)
                    startActivity(intent)
                }


            }

        }.start()
    }

    private fun setUpExerciseView() {
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
        }
        setExerciseProgressbar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseView.text = exerciseList!![currentExercisePosition].getName()
    }


    private fun setupRestView() {

        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.INVISIBLE

        if (restTimer != null) {
            restTimer!!.cancel()

        }
        setRestProgressbar()


    }

    override fun onDestroy() {

        restTimer!!.cancel()
        restProgress = 0
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }

        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun setupExerciseStatusRecyclerView() {
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter

    }

    private fun alertDialogFunction() {


        val builder = AlertDialog.Builder(this)

        //set tittle for alert dialog
        builder.setTitle("Are you sure?")
        // set message for alert dialog
        builder.setMessage("This will stop your workout. You've that far, are you sure you want to quit.")
        //set Icon for dialog
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        // Performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            finish()
            dialogInterface.dismiss()
        }

        // performing negative  action
        builder.setNegativeButton("No ") { dialogInterface, which ->

            dialogInterface.dismiss()
        }


        // performing newtrol


        // create the alert dialog
        val alertDialog: AlertDialog = builder.create()
        // set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()


    }
}

