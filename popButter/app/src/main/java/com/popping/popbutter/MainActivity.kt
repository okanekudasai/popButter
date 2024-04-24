package com.popping.popbutter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibratorManager
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.popping.popbutter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("ClickableViewAccessibility", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.testButton.setOnClickListener {
            Toast.makeText(this, "버튼 클릭됨", Toast.LENGTH_SHORT).show()
        }

        val countPlainText = binding.textView;
        val sharedPreferences: SharedPreferences = getSharedPreferences("counter", Context.MODE_PRIVATE)
        val count_memory = sharedPreferences.getInt("count", 0)
        countPlainText.text = count_memory.toString()

        val vibrator = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val vib = vibrator.defaultVibrator
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        binding.imageView.setOnTouchListener { view, motionEvent ->
            val iv: ImageView = view as ImageView
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    iv.setImageResource(R.drawable.opened_mouth)
                    var c = countPlainText.text.toString().toInt()
                    c += 1
                    editor.putInt("count", c)
                    editor.apply()
                    vib.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.EFFECT_TICK))
                    countPlainText.setText(c.toString())
                }
                MotionEvent.ACTION_UP -> {
                    iv.setImageResource(R.drawable.closed_mouth)
                }
            }
            true
        }
    }
}