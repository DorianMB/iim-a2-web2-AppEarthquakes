package com.example.dorian.earthquake

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.dorian.earthquake.api.EarthquakeService
import com.example.dorian.earthquake.api.model.EarthquakeData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mService: EarthquakeService
    private var mMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mMessage = intent.getStringExtra(MainActivity.EXTRA_CHOICE)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Fetch Api
        val retrofit = Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        mService = retrofit.create<EarthquakeService>(EarthquakeService::class.java)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera


        //Fetch Api
        val request = when (mMessage) {
            "All" -> mService.listAll()
            "M1.0+" -> mService.listMag10()
            "M2.5+" -> mService.listMag25()
            "M4.5+" -> mService.listMag45()
            else -> mService.listSignificantEarthquakes()
        }

        request.enqueue(object : Callback<EarthquakeData>{
            override fun onFailure(call: Call<EarthquakeData>?, t: Throwable?) {
                Log.e(TAG, "Error with listSignificantEarthquakes, error : $t")
                longToast("Oups, y a eu une erreur man !")
            }

            override fun onResponse(call: Call<EarthquakeData>?, response: Response<EarthquakeData>?) {
                Log.d(TAG, "Success, ${response?.body()}")
                longToast("Ouais man, ça marche !")

                response?.body()?.let {
                    it.features?.forEach {feature ->
                        feature.geometry?.coordinates?.let {
                            mMap.addMarker(MarkerOptions().position(LatLng(it[1], it[0])).title(feature.properties?.place).snippet(feature.properties?.mag.toString()))
                        }
                    }
                }
            }

        })
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}
