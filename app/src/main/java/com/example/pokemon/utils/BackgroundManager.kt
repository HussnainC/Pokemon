package com.example.pokemon.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

class BackgroundManager @Inject constructor(val ctx: Context) {

    fun isServiceRunning(serviceClass: Class<*>): Boolean {
        checkContext()
        val manager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun startService(serviceClass: Class<*>, intent: (Intent) -> Unit = {}) {
        checkContext()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundServices(serviceClass, intent)
        } else ctx.startService(Intent(ctx, serviceClass))
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startForegroundServices(serviceClass: Class<*>, intent: (Intent) -> Unit) {
        ctx.startForegroundService(Intent(ctx, serviceClass).also {
            intent(it)
        })
    }


    private fun checkContext() {
        if (ctx == null) throw RuntimeException("Context can not be null: Initialize context first")
    }

    fun stopService(serviceClass: Class<*>?) {
        checkContext()
        if (isServiceRunning(ctx::class.java)) ctx.stopService(
            Intent(
                ctx,
                serviceClass
            )
        )
    }

    companion object {
        private const val period = 15 * 1000
    }
}