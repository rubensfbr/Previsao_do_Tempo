package com.example.previsaodotempo.domain.usecase

import android.content.Context
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.previsaodotempo.domain.repository.LocationRepository
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class LocationUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val context: Context
) {

    suspend fun getLocation(): Task<Location> {
        val locationList = mutableListOf<Double>()
        return repository.getLocation()
//            .addOnCompleteListener {
//                locationList.add(it.result.latitude)
//                locationList.add(it.result.longitude)
//            }

//            .addOnSuccessListener {
//                locationList.add(it.latitude)
//                locationList.add(it.longitude)
//
//            }
            .addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }



    }


}