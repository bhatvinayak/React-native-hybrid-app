package com.hybridapp

import android.content.Intent
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class NativeScreenModule(private val reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String = "NativeScreen"

    @ReactMethod
    fun openNativeScreen(name: String, age: Int) {
        val intent = Intent(reactContext, NativeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        // pass data to Activity
        intent.putExtra("name", name)
        intent.putExtra("age", age)

        reactContext.startActivity(intent)
    }
}
