package com.msenterprisesdws.msenterprises

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.msenterprisesdws.msenterprises.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
    @SuppressLint("MissingInflatedId")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val mshome = LatLng(22.97615731694454, 76.0555631177904)
        val work1 = LatLng(23.163137606081385, 75.70519794160096)
        val work2 = LatLng(23.267700666867682, 76.16312199566312)
        val work3 = LatLng(22.730154264684945, 76.14574726681063)
        val work4 = LatLng(22.7448597300734, 75.91036009595376)

//        mMap.addMarker(MarkerOptions().position(mshome).title("MS Enterprises"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mshome,10f))






        // view intializing office
        val markderView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.mapmarker, null)
        val mapcard = markderView.findViewById<CardView>(R.id.mapmarkercard)
        val maptext = markderView.findViewById<TextView>(R.id.mapmarkertext)
        val mapimage = markderView.findViewById<ImageView>(R.id.mapmarkerimage)



        // view intializing work
        val mapworkView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.workmapcard, null)
        val mapwcard = mapworkView.findViewById<CardView>(R.id.mapworkcard)
        val mapwtext = mapworkView.findViewById<TextView>(R.id.mapworktext)
        val mapwimage = mapworkView.findViewById<ImageView>(R.id.mapworkimage)






        // work 1 marker intialializing
        mapwtext.text = "Pipe Line Construction"
        val bitmapW1 = Bitmap.createScaledBitmap(viewToBitmap(mapwcard)!!,mapwcard.width,mapwcard.height,false)
        val smallMarkerIconW1 = BitmapDescriptorFactory.fromBitmap(bitmapW1)
        mMap.addMarker(MarkerOptions().position(work1).icon(smallMarkerIconW1))



        // work 2 marker intialializing
        mapwtext.text = "Boundry wall construction"
        val bitmapW2 = Bitmap.createScaledBitmap(viewToBitmap(mapwcard)!!,mapwcard.width,mapwcard.height,false)
        val smallMarkerIconW2 = BitmapDescriptorFactory.fromBitmap(bitmapW2)
        mMap.addMarker(MarkerOptions().position(work2).icon(smallMarkerIconW2))




        // work 3 marker intialializing
        mapwtext.text = "Water Tank Construction"
        val bitmapW3 = Bitmap.createScaledBitmap(viewToBitmap(mapwcard)!!,mapwcard.width,mapwcard.height,false)
        val smallMarkerIconW3 = BitmapDescriptorFactory.fromBitmap(bitmapW3)
        mMap.addMarker(MarkerOptions().position(work3).icon(smallMarkerIconW3))



        // work 4 marker intialializing
        mapwtext.text = "Pipe Line Construction"
        val bitmapW4 = Bitmap.createScaledBitmap(viewToBitmap(mapwcard)!!,mapwcard.width,mapwcard.height,false)
        val smallMarkerIconW4 = BitmapDescriptorFactory.fromBitmap(bitmapW4)
        mMap.addMarker(MarkerOptions().position(work4).icon(smallMarkerIconW4))





        //marker intilializing office
        maptext.text = "MS Enterprises"
        val bitmapH = Bitmap.createScaledBitmap(viewToBitmap(mapcard)!!,mapcard.width,mapcard.height,false)
        val smallMarkerIconH= BitmapDescriptorFactory.fromBitmap(bitmapH)
        mMap.addMarker(MarkerOptions().position(mshome).icon(smallMarkerIconH))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mshome,9f))









    }

     private fun viewToBitmap(view:View):Bitmap?{


         view.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED )
         val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
         val canvas = Canvas(bitmap)
         view.layout(0,0,view.measuredWidth, view.measuredHeight)
         view.draw(canvas)
         return bitmap
     }




}