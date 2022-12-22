package com.betano.planner

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.betano.planner.databinding.ActivityChooseBinding
import java.text.SimpleDateFormat
import java.util.*


class ChooseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseBinding
    private var dateAndTime = Calendar.getInstance()
    private var arr = arrayOf<Date?>(
       null,null,null,null,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView19.text = Html.fromHtml("<font color=#000000>Do you want to skip planning now ?</font> <font color=#AD491E>Skip</font>")
        binding.textView19.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putBoolean("first",false)
                .apply()
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }
        binding.button2.setOnClickListener {
            var m = mutableListOf<String>()
            if(arr[0]!=null) m.add(convert(binding.textView6.text.toString(),binding.textView5.text.toString(),false,0))
            if(arr[1]!=null) m.add(convert(binding.textView8.text.toString(),binding.textView7.text.toString(),false,1))
            if(arr[2]!=null) m.add(convert(binding.textView12.text.toString(),binding.textView11.text.toString(),false,2))
            if(arr[3]!=null) m.add(convert(binding.textView10.text.toString(),binding.textView9.text.toString(),false,3))
            Log.d("TAG",m.toString())
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putStringSet("set",m.toSet()).apply()
            startActivity(Intent(applicationContext,TimeActivity::class.java))
        }
        binding.textView6.setOnClickListener {
            TimePickerDialog(
                this@ChooseActivity, {p,h,m ->
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);
                    binding.textView6.text = SimpleDateFormat("hh:mm aa").format(dateAndTime.time)
                    arr[0] = dateAndTime.time
                },
                dateAndTime[Calendar.HOUR_OF_DAY],
                dateAndTime[Calendar.MINUTE], false,

            ).show()
        }
        binding.textView8.setOnClickListener {
            TimePickerDialog(
                this@ChooseActivity, {p,h,m ->
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);
                    binding.textView8.text = SimpleDateFormat("hh:mm aa").format(dateAndTime.time)
                    arr[1] = dateAndTime.time
                },
                dateAndTime[Calendar.HOUR_OF_DAY],
                dateAndTime[Calendar.MINUTE], false,

                ).show()
        }
        binding.textView12.setOnClickListener {
            TimePickerDialog(
                this@ChooseActivity, {p,h,m ->
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);
                    binding.textView12.text = SimpleDateFormat("hh:mm aa").format(dateAndTime.time)
                    arr[2] = dateAndTime.time
                },
                dateAndTime[Calendar.HOUR_OF_DAY],
                dateAndTime[Calendar.MINUTE], false,

                ).show()
        }
        binding.textView10.setOnClickListener {
            TimePickerDialog(
                this@ChooseActivity, {p,h,m ->
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);
                    binding.textView10.text = SimpleDateFormat("hh:mm aa").format(dateAndTime.time)
                    arr[3] = dateAndTime.time
                },
                dateAndTime[Calendar.HOUR_OF_DAY],
                dateAndTime[Calendar.MINUTE], false,

                ).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convert(date: String, name: String, ch: Boolean, ind: Int): String {
        return "$ind^$name^${date}^$ch"
    }
}