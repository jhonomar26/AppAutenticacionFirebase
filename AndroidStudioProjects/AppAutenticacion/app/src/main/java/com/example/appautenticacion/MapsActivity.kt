package com.example.appautenticacion

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var areaPermitidaPolygon: Polygon
    private var userMarker: Marker? = null // Marcador para la ubicación del usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Inicializar FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtener el SupportMapFragment y ser notificado cuando el mapa esté listo para usarse
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Definir los puntos que forman el polígono
        val areaPermitida = listOf(
            LatLng(1.219395, -77.278107),
            LatLng(1.218681, -77.277443),
            LatLng(1.213349, -77.275381),
            LatLng(1.212433, -77.275587),
            LatLng(1.210223, -77.273572),
            LatLng(1.208348, -77.276356),
            LatLng(1.206582, -77.281174),
            LatLng(1.206600, -77.281224),
            LatLng(1.210916, -77.283201),
            LatLng(1.211664, -77.281549),
            LatLng(1.217479, -77.284044),
            LatLng(1.217654, -77.283881),
            LatLng(1.216724, -77.283046),
            LatLng(1.217326, -77.282343)
        )

        // Dibujar el polígono en el mapa
        val polygonOptions = PolygonOptions()
            .addAll(areaPermitida)
            .strokeColor(0xFF0000FF.toInt()) // Color del borde
            .fillColor(0x7F00FF00.toInt())   // Color de relleno con transparencia
            .strokeWidth(5f)

        areaPermitidaPolygon = mMap.addPolygon(polygonOptions)

        // Centrar el mapa en el área del polígono
        val boundsBuilder = LatLngBounds.Builder()
        for (latLng in areaPermitida) {
            boundsBuilder.include(latLng)
        }
        val bounds = boundsBuilder.build()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100)
        mMap.moveCamera(cameraUpdate)

        // Verificar la ubicación del usuario
        checkLocationPermissionAndTrack()
    }

    private fun checkLocationPermissionAndTrack() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Si el permiso está otorgado, rastrear ubicación
            trackUserLocation()
        } else {
            // Si el permiso no está otorgado, solicitarlo
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }

    private fun trackUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val userLatLng = LatLng(location.latitude, location.longitude)

                // Mostrar la ubicación actual del usuario en el mapa
                if (userMarker == null) {
                    userMarker =
                        mMap.addMarker(MarkerOptions().position(userLatLng).title("Tu ubicación"))
                } else {
                    userMarker?.position = userLatLng // Actualizar la posición del marcador
                }

                // Verificar si el usuario está dentro del área permitida
                if (isPointInPolygon(userLatLng, areaPermitidaPolygon.points)) {
                    // Cambiar el color del marcador si está dentro
                    userMarker?.setIcon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_RED
                        )
                    )
                    Toast.makeText(this, "¡No puedes estar en esta área!", Toast.LENGTH_LONG).show()
                } else {
                    // Color normal si está fuera
                    userMarker?.setIcon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_GREEN
                        )
                    )
                    Toast.makeText(this, "Estás fuera del área permitida.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                // Si no hay ubicación, simular una ubicación dentro del área permitida
                val simulatedUserLocation =
                    LatLng(1.215000, -77.276000) // Coordenadas dentro del polígono
                if (userMarker == null) {
                    userMarker = mMap.addMarker(
                        MarkerOptions().position(simulatedUserLocation).title("Ubicación simulada")
                    )
                } else {
                    userMarker?.position =
                        simulatedUserLocation // Actualizar la posición del marcador
                }

                // Verificar si la ubicación simulada está dentro del área permitida
                if (isPointInPolygon(simulatedUserLocation, areaPermitidaPolygon.points)) {
                    // Cambiar el color del marcador si está dentro
                    userMarker?.setIcon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_RED
                        )
                    )
                    Toast.makeText(this, "¡No puedes estar en esta área!", Toast.LENGTH_LONG).show()
                } else {
                    // Color normal si está fuera
                    userMarker?.setIcon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_GREEN
                        )
                    )
                    Toast.makeText(this, "Estás fuera del área permitida.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    // Función para verificar si un punto está dentro de un polígono
    private fun isPointInPolygon(point: LatLng, polygonPoints: List<LatLng>): Boolean {
        var intersects = false
        for (i in polygonPoints.indices) {
            val j = (i + 1) % polygonPoints.size
            val vertex1 = polygonPoints[i]
            val vertex2 = polygonPoints[j]

            if (((vertex1.longitude > point.longitude) != (vertex2.longitude > point.longitude)) &&
                (point.latitude < (vertex2.latitude - vertex1.latitude) * (point.longitude - vertex1.longitude) / (vertex2.longitude - vertex1.longitude) + vertex1.latitude)
            ) {
                intersects = !intersects
            }
        }
        return intersects
    }
}
