package com.betano.planner

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import com.betano.planner.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mode = 0
    private var dateAndTime = Calendar.getInstance()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var adapter = MyAdapter(this,0)
        binding.list.adapter = adapter
        binding.remove.setOnClickListener {
            binding.layout.visibility = View.INVISIBLE
            binding.list.visibility = View.VISIBLE
            binding.textView20.text = "Daily Tasks"
            if(mode!=1) {
                binding.list.adapter = MyAdapter(this,1)
                mode = 1
            } else {
                var ch = (binding.list.adapter as MyAdapter).checks
                var data = (binding.list.adapter as MyAdapter).data
                for(i in ch.indices) {
                    while(i<ch.size && ch[i]) {
                        data.removeAt(i)
                        ch.removeAt(i)
                    }
                }
                for(i in data.indices) {
                    var s = data[i].split("^")
                    data[i] = "$i^${s[1]}^${s[2]}^${s[3]}"
                    if(s[3].toBoolean()) {
                        cancel(s[2],s[0].toInt())
                        addTimer(s[2],s[1],i)
                    }
                }
                getSharedPreferences("prefs", MODE_PRIVATE).edit().putStringSet("set",data.toSet()).apply()
                binding.list.adapter = MyAdapter(this,0)
                mode = 0
            }
        }
        binding.add.setOnClickListener {
            if(mode==2) {
                binding.layout.visibility = View.INVISIBLE
                binding.list.visibility = View.VISIBLE
                binding.textView20.text = "Daily Tasks"
                mode = 0
                var n  = convert(dateAndTime.time,binding.textView21.text.toString(),binding.check.isChecked,adapter.data.size)
                var set = getSharedPreferences("prefs", MODE_PRIVATE).getStringSet("set",HashSet<String>())
                var set1 = HashSet<String>()
                set1.addAll(set!!.toList())
                set1.add(n)
                getSharedPreferences("prefs", MODE_PRIVATE).edit().putStringSet("set",set1).apply()
                binding.list.adapter = MyAdapter(this,0)
                if(binding.check.isChecked) addTimer(binding.time.text.toString(),binding.textView21.text.toString(),(binding.list.adapter as MyAdapter).itemCount-1)
            } else {
                binding.list.visibility = View.INVISIBLE
                binding.layout.visibility = View.VISIBLE
                binding.textView20.text = "New task"
                mode = 2
                binding.check.isChecked = false
                binding.time.text = "TIME"
                binding.textView21.setText("Please type a new task there and press “Plus” button to save it.")
            }
        }
        binding.time.setOnClickListener {
            TimePickerDialog(
                this@MainActivity, {p,h,m ->
                    dateAndTime.set(Calendar.HOUR_OF_DAY, h);
                    dateAndTime.set(Calendar.MINUTE, m);
                    binding.time.text = SimpleDateFormat("hh:mm aa").format(dateAndTime.time)
                },
                dateAndTime[Calendar.HOUR_OF_DAY],
                dateAndTime[Calendar.MINUTE], false,
                ).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convert(date: Date, name: String, ch: Boolean, ind: Int): String {
        return "$ind^$name^${SimpleDateFormat("hh:mm aa").format(date)}^$ch"
    }

    fun addTimer(time: String, name: String,ind: Int) {
        val alarm = getSystemService(ALARM_SERVICE) as AlarmManager
        var c1 = LocalDateTime.now()
        var c2 = LocalDateTime.from(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm a").parse(LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy"))+" "+time))
        var delta = ((c2.hour-c1.hour)*3600+(c2.minute-c1.minute)*60)*1000L
        if(delta<0) delta += 24*60*60*1000L
        Log.d("TAG","$delta ${c1.hour} ${c1.minute} ${c2.hour} ${c2.minute}")
        var intent = Intent(this,MyReceiver::class.java).apply {
            putExtra("text",name)
        }
        var p = PendingIntent.getBroadcast(this,ind,intent,PendingIntent.FLAG_MUTABLE)
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+delta,AlarmManager.INTERVAL_DAY,p)
    }
    fun cancel(name: String,ind: Int) {
        val alarm = getSystemService(ALARM_SERVICE) as AlarmManager
        var intent = Intent(this,MyReceiver::class.java).apply {
            putExtra("text",name)
        }
        var p = PendingIntent.getBroadcast(this,ind,intent,PendingIntent.FLAG_MUTABLE)
        alarm.cancel(p)
    }
}