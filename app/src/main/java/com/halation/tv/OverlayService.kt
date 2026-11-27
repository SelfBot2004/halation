package com.halation.tv
import android.accessibilityservice.AccessibilityService
import android.graphics.Color
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebView

class OverlayService : AccessibilityService() {
    private var webView: WebView? = null
    private var isVisible = false
    private lateinit var params: WindowManager.LayoutParams

    override fun onServiceConnected() {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
        }

        webView = WebView(this).apply {
            setBackgroundColor(Color.TRANSPARENT)
            settings.javaScriptEnabled = true
            loadUrl("file:///android_asset/index.html") 
            visibility = View.VISIBLE
        }
        wm.addView(webView, params)
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == 271 && event.action == KeyEvent.ACTION_DOWN) {
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            isVisible = !isVisible
            if (isVisible) {
                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                webView?.evaluateJavascript("toggleMenu()", null)
                webView?.requestFocus()
            } else {
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                webView?.evaluateJavascript("toggleMenu()", null)
            }
            wm.updateViewLayout(webView, params)
            return true
        }
        return super.onKeyEvent(event)
    }
    override fun onAccessibilityEvent(event: android.view.accessibility.AccessibilityEvent?) {}
    override fun onInterrupt() {}
}
