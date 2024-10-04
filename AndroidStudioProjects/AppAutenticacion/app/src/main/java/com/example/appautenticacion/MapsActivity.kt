package com.example.appautenticacion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtener el SupportMapFragment y ser notificado cuando el mapa esté listo para usarse
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Definir los puntos que forman el polígono (por ejemplo, una zona en una ciudad)
        val areaPermitida = listOf(
            //1.219395, -77.278107

            //1.218681, -77.277443

            //1.213349, -77.275381
            //1.212433, -77.275587
            //1.210391, -77.273932

            //1.208348, -77.276356
            //1.206582, -77.281174

            //1.210916, -77.283201
            //1.211664, -77.281549
            //1.217479, -77.284044
            //1.218173, -77.283382
            //1.217330, -77.282362
            LatLng(1.219395, -77.278107),  // Carrera 29 calle 21, punto 1

            LatLng(1.218681, -77.277443),  // Carrera 29 calle 21, punto 1

            LatLng(1.213349, -77.275381),  // Carrera 29 calle 21, punto 1
            LatLng(1.212433, -77.275587),  // Carrera 29 calle 21, punto 1
            LatLng(1.210391, -77.273932),  // Carrera 29 calle 21, punto 1

            LatLng(1.208348, -77.276356),  // Carrera 29 calle 21, punto 1
            LatLng(1.206582, -77.281174),  // Carrera 19, calle 21


            LatLng(1.206600, -77.281224),  // Carrera 19, calle 12
            LatLng(1.210916, -77.283201),   // Carrera 23, calle 12
            LatLng(1.211664, -77.281549),   // Carrera 23, calle 14
            LatLng(1.217479, -77.284044), //Carrera 30, calle 14
            LatLng(1.218173, -77.283382), //Carrera 30a, calle 16
            LatLng(1.217330, -77.282362), //Carrera 29, calle 16

        )

        // Dibujar el polígono en el mapa
        val polygonOptions = PolygonOptions()
            .addAll(areaPermitida)
            .strokeColor(0xFF0000FF.toInt()) // Color del borde
            .fillColor(0x7F00FF00.toInt())   // Color de relleno con transparencia
            .strokeWidth(5f)

        mMap.addPolygon(polygonOptions)

        // Centrar el mapa en el área del polígono
        val boundsBuilder = LatLngBounds.Builder()
        for (latLng in areaPermitida) {
            boundsBuilder.include(latLng)
        }
        val bounds = boundsBuilder.build()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100)
        mMap.moveCamera(cameraUpdate)
    }
}
