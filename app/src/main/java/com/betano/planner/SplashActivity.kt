package com.betano.planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.betano.planner.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            var first = getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("first",true)
            if(first) {
                startActivity(Intent(applicationContext,ChooseActivity::class.java))
            } else {
                startActivity(Intent(applicationContext,MainActivity::class.java))
                finish()
            }
        }
    }
}