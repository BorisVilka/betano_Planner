package com.betano.planner

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.betano.planner.databinding.ActivityTimeBinding
import java.text.SimpleDateFormat
import java.util.*

class TimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeBinding
    private var dateAndTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView15.text = Html.fromHtml("<font color=#000000>Want to change task list ? </font> <font color=#AD491E>Go to previous screen</font>")
        binding.textView15.setOnClickListener {
            finish()
        }
        binding.button4.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putBoolean("first",false).apply()
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
            finish()
        }
        binding.button3.setOnClickListener {
            TimePickerDialog(
                this@TimeActivity, {p,h,m ->
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);
                    binding.button3.text = "Once a day: ${SimpleDateFormat("hh:mm aa").format(dateAndTime.time)}"
                },
                dateAndTime[Calendar.HOUR_OF_DAY],
                dateAndTime[Calendar.MINUTE], false,

                ).show()
        }
    }
}