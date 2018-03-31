package com.example.dorian.earthquake.api

import com.example.dorian.earthquake.api.model.EarthquakeData
import retrofit2.Call
import retrofit2.http.GET


interface EarthquakeService {
    @GET("earthquakes/feed/v1.0/summary/significant_month.geojson")
    fun listSignificantEarthquakes(): Call<EarthquakeData>

    @GET("earthquakes/feed/v1.0/summary/all_month.geojson")
    fun listAll(): Call<EarthquakeData>

    @GET("earthquakes/feed/v1.0/summary/1.0_month.geojson")
    fun listMag10(): Call<EarthquakeData>

    @GET("earthquakes/feed/v1.0/summary/2.5_month.geojson")
    fun listMag25(): Call<EarthquakeData>

    @GET("earthquakes/feed/v1.0/summary/4.5_month.geojson")
    fun listMag45(): Call<EarthquakeData>
}