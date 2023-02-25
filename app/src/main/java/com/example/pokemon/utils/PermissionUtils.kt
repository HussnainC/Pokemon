package com.example.pokemon.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import javax.inject.Inject

class PermissionUtils @Inject constructor(val context: Context) {

    val cameraPermissions =
        arrayOf(Manifest.permission.CAMERA)
    val CAMERA_REQUEST_CODE = 2222

    fun checkCameraPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            cameraPermissions[0]
        ) == PackageManager.PERMISSION_GRANTED
    }
}