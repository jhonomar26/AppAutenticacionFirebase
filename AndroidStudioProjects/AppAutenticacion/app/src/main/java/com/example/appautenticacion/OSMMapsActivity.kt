package com.example.appautenticacion

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider

//Hereda de AppCompatActivity e implementa MapListener
class OSMMapsActivity : AppCompatActivity(), MapListener {

    private lateinit var mapView: MapView
    private lateinit var myLocationOverlay: MyLocationNewOverlay

    //Manejar el zoom y el centro del mapa
    private lateinit var mapController: IMapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración inicial de osmdroid
        Configuration.getInstance().load(
            applicationContext,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
        Configuration.getInstance().userAgentValue = packageName

        setContentView(R.layout.activity_osm_maps)

        // Inicializar y configurar el mapView
        //Lienzo del mapa
        mapView = findViewById(R.id.osmMap)
        //(Como se vera el mapa)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        //(Hacer zoom al mapa)
        mapView.setMultiTouchControls(true)
        mapView.isTilesScaledToDpi = true

        // Inicializar el controlador del mapa
        mapController = mapView.controller
        mapController.setZoom(16.0) // Aumentamos el zoom inicial

        //Ubicación en el mapa, mas un punto azul que nos sigue
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        myLocationOverlay.isDrawAccuracyEnabled = true

        // Definir los puntos que forman el polígono
        val areaPermitida = arrayListOf(
            GeoPoint(1.219395, -77.278107),
            GeoPoint(1.218681, -77.277443),
            GeoPoint(1.213349, -77.275381),
            GeoPoint(1.212433, -77.275587),
            GeoPoint(1.210223, -77.273572),
            GeoPoint(1.208348, -77.276356),
            GeoPoint(1.206582, -77.281174),
            GeoPoint(1.206600, -77.281224),
            GeoPoint(1.210916, -77.283201),
            GeoPoint(1.211664, -77.281549),
            GeoPoint(1.217479, -77.284044),
            GeoPoint(1.217654, -77.283881),
            GeoPoint(1.216724, -77.283046),
            GeoPoint(1.217326, -77.282343)
        )

        // Crear y configurar el polígono
        val polygon = Polygon().apply {
            points = areaPermitida
            strokeColor = android.graphics.Color.BLUE
            fillColor = android.graphics.Color.argb(75, 0, 255, 0)
            strokeWidth = 3f
        }

        // Agregar overlays al mapa
        mapView.overlays.add(myLocationOverlay)
        mapView.overlays.add(polygon)

        
        // Centrar el mapa en el polígono
        //Crea una caja rectangular que va contener todos los puntos
        val boundingBox = BoundingBox.fromGeoPoints(areaPermitida)
        mapView.post {
            //Ajusta el zoom de la camara automaticamente
            mapView.zoomToBoundingBox(boundingBox, true, 100)
            // Centrar en un punto específico del polígono: Centra el mapa en un punto especifico
            mapController.setCenter(GeoPoint(1.213349, -77.275381))
        }

        // Agregar listener para el mapa
        mapView.addMapListener(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        Log.d(
            "Map Scroll",
            "Lat: ${event?.source?.mapCenter?.latitude}, Lon: ${event?.source?.mapCenter?.longitude}"
        )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        Log.d("Map Zoom", "Zoom Level: ${event?.zoomLevel}")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDetach()
    }
}