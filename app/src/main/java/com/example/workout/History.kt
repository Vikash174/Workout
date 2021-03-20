package com.example.workout

import adapter.HistoryAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import constants.DatabaseHandler
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        getAllCompletedDates()
        title = "History"
        supportActionBar?.hide()
    }

    private fun getAllCompletedDates(){
        val dbHandler = DatabaseHandler(this,null)
        val allCompletedDatesList = dbHandler.getAllCompleteDatesList()
        Log.d("_______","+++"+dbHandler.getAllCompleteDatesList())

        for (i in allCompletedDatesList){

            if (allCompletedDatesList.size>0){
                tvHistory.visibility = View.VISIBLE
                rvHistory.visibility = View.VISIBLE
                tvNoDataAvailable.visibility = View.GONE

                rvHistory.layoutManager = LinearLayoutManager(this)
                val historyAdapter = HistoryAdapter(this,allCompletedDatesList)
                rvHistory.adapter= historyAdapter
            }else{
                tvHistory.visibility =View.GONE
                rvHistory.visibility = View.GONE
                tvNoDataAvailable.visibility = View.VISIBLE
            }
        }
    }
}