package com.mohamedhashim.turboapp

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by Mohamed Hashim on 12/15/2018.
 */

@IgnoreExtraProperties
data class Bin(
    var id: String? = "",
    var name: String? = "",
    var lat: Double? = 0.0,
    var lng: Double? = 0.0,
    var status: Float? = 0.0f
)