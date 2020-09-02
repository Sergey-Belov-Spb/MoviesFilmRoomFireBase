package com.example.moviesfilmroomfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val idMoveNewFromFireBase = getIntent().getStringExtra("idMovie")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener{
            throw RuntimeException("Test Crash")
        }

        if (idMoveNewFromFireBase!= null)
        {

            textView.text=idMoveNewFromFireBase
        }


        button2.setOnClickListener{
            val firstValue = 1
            val secondValue = 0
            FirebaseCrashlytics.getInstance().apply {
                log("Button 2 click")
                try {
                    firstValue / secondValue
                } catch (e: Exception) {
                    e.printStackTrace()
                    setCustomKey("first_value", firstValue)
                    setCustomKey("second_value", secondValue)
                    recordException(e)
                }
            }

        }

    }
}
