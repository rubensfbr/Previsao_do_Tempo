package com.example.previsaodotempo.domain.repository

import android.location.Location
import com.google.android.gms.tasks.Task

interface LocationRepository {

    suspend fun getLocation(): Task<Location>
}