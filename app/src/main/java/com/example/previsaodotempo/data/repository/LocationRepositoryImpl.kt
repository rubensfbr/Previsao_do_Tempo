package com.example.previsaodotempo.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.previsaodotempo.domain.repository.LocationRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val context: Context) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Task<Location> {
        return LocationServices.getFusedLocationProviderClient(context).lastLocation

    }


}