package com.halation.tv

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.BLACK)
        }

        val title = TextView(this).apply {
            text = "HALATION"
            textSize = 32f
            setTextColor(Color.rgb(255, 150, 50)) // Orange
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 40)
            text = "Checking permissions..."
        }

        layout.addView(title)
        layout.addView(status)
        setContentView(layout)
        layout.tag = status
    }

    override fun onResume() {
        super.onResume()
        // This updates the text when you come back to the app from Settings
        val statusText = findViewById<LinearLayout>(android.R.id.content).getChildAt(1) as TextView
        
        val overlayStatus = if (Settings.canDrawOverlays(this)) "✅ Overlay: ON" else "❌ Overlay: OFF"
        
        statusText.text = "$overlayStatus\n\n👉 Go to TV Settings > Device Preferences > Accessibility.\n👉 Turn ON Halation."
    }
}
