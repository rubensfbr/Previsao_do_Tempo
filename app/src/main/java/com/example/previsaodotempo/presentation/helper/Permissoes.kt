package com.example.previsaodotempo.presentation.helper

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.previsaodotempo.presentation.activities.MainActivity

class Permissoes {

    fun verificarPermissoes(activity: MainActivity) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissoes = mutableListOf<String>()
            permissoes.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissoes.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(activity, permissoes.toTypedArray(), 0)
        }
    }
}