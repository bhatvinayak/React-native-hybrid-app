package com.hybridapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.LinearLayout

class NativeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Receive data from Intent
        val name = intent.getStringExtra("name") ?: "Guest"
        val age = intent.getIntExtra("age", 0)

        // UI setup
        val textView = TextView(this)
        textView.text = "Hello $name, age $age — from Native Android!"
        textView.textSize = 20f
        textView.setPadding(100, 200, 100, 100)

        val button = Button(this)
        button.text = "Back to React Native"
        button.setOnClickListener {
            finish()
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(textView)
        layout.addView(button)

        setContentView(layout)
    }
}
