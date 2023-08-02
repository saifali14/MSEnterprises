package com.msenterprisesdws.msenterprises

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Locale
import java.util.UUID

class ReportproblemActivity : AppCompatActivity() {


    private lateinit var name:String
    private lateinit var number:String
    private lateinit var location:String
    private lateinit var useruid:String
    private lateinit var attachfile:String
    private lateinit var message:String
    var long:Long?=null
    var lat:Long?=null


    private lateinit var imagepreview:ImageView
    private lateinit var submitbtn:Button
    private lateinit var messageedxt:EditText
    private lateinit var pickbtn:ImageView


//
////    private lateinit var lm : LocationManager
//    private lateinit var loc: Location

    private lateinit var database: FirebaseFirestore

    private lateinit var auth: FirebaseAuth


    val FINE_LOCATION_RO  =101
    val STORAGE_RO = 102
    val WSTORAGE_RO = 103

    val MEDIA_READ = 101



    val loading = RegisterationLoadingbar(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportproblem)

        init()

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        retreiveuser()



        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_MEDIA_IMAGES)



        permissionLauncherMultiple.launch(permissions)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
       val  loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)



        var ll = object:LocationListener{
            override fun onLocationChanged(location: Location) {

                reverseGeocode(location)
            }



        }



        submitbtn.setOnClickListener {


//                checkForPermission(android.Manifest.permission.READ_MEDIA_IMAGES,"read media",MEDIA_READ)

            loading.startloading()
            uploadimagetostorage()



        }

        pickbtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }





        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,100.2f,ll)




    }






    private fun init(){

        imagepreview = findViewById(R.id.report_imagepreview)
        submitbtn = findViewById(R.id.report_submitbtn)
        messageedxt = findViewById(R.id.report_edxt)
        pickbtn = findViewById(R.id.report_pickimaage)



    }



    private fun reverseGeocode(loc:Location?){


        lat = loc?.latitude?.toLong()
        long = loc?.longitude?.toLong()
        var gc = Geocoder(this, Locale.getDefault())
        var addresses = gc.getFromLocation(loc!!.latitude,loc.longitude,2)
        var address = addresses?.get(0)
        location = "${address!!.getAddressLine(0)} ${address.locality}"




    }




    // permission

    private val permissionLauncherMultiple = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ result ->

        var areAllGranted = true
        for (isGranted in result.values){
            Log.d("permission","permissionLauncherMultiple: $isGranted")
            areAllGranted = areAllGranted && isGranted
        }


        if (areAllGranted){

            multiplePermissionnsGranted()

        }else{

            Log.d("permission","permissionLauncherMultiple: All or some permission denied ...")
            Toast.makeText(this,"All or some permission denied ...",Toast.LENGTH_SHORT).show()

        }
    }

    private fun multiplePermissionnsGranted(){

        Toast.makeText(this,"All permission granted final",Toast.LENGTH_SHORT).show()
    }



    private fun checkForPermissions(permission:String, name:String, requestCode:Int){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            when{

                ContextCompat.checkSelfPermission(applicationContext,permission) == PackageManager.PERMISSION_GRANTED ->{

                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()

                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)

            }



        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String){

            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){

                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT).show()

            }else{

                Toast.makeText(applicationContext, "$name permission granted",Toast.LENGTH_SHORT).show()

            }

        }
        when(requestCode){

            FINE_LOCATION_RO -> innerCheck("location")
            STORAGE_RO -> innerCheck("read storage")
            WSTORAGE_RO -> innerCheck("write storage")
        }
    }


    private fun showDialog(permission: String, name: String, requestCode: Int){

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)

        builder.apply {


            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK"){dialog, which ->
                ActivityCompat.requestPermissions(this@ReportproblemActivity, arrayOf(permission),requestCode)
            }

        }

        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()

    }











    var imageuri: Uri = Uri.parse("android.resource://com.msenterprisesdws.msenterprises/drawable/caution")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode==0 && resultCode== Activity.RESULT_OK && data!=null){

            imageuri = data.data!!

            Glide.with(applicationContext).load(imageuri).into(imagepreview)
        }
    }





    private fun checkForPermission(permission:String , name:String , requestCode: Int){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            when{

                ContextCompat.checkSelfPermission(applicationContext,permission) == PackageManager.PERMISSION_GRANTED ->{

                    Toast.makeText(applicationContext,"$name permission granted", Toast.LENGTH_SHORT).show()

                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent,0)


                }

                shouldShowRequestPermissionRationale(permission) -> showDialog(permission,name,requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)

            }


        }

    }



//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        fun innerCheck(name: String){
//
//            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
//
//                Toast.makeText(applicationContext,"$name permission refused", Toast.LENGTH_SHORT).show()
//
//            }else{
//
//                Toast.makeText(applicationContext,"$name permission granted", Toast.LENGTH_SHORT).show()
//
//                val intent = Intent(Intent.ACTION_PICK)
//                intent.type = "image/*"
//                startActivityForResult(intent,0)
//            }
//
//
//        }
//
//
//        when(requestCode){
//
//            MEDIA_READ -> innerCheck("read media")
//
//
//        }
//
//
//    }
//
//
//
//
//    private fun showDialog(permission: String, name:String, requestCode: Int){
//
//
//
//        val builder = AlertDialog.Builder(this)
//
//        builder.apply {
//
//            setMessage("Permission to access your $name is required to use this app")
//            setTitle("Permission required")
//            setPositiveButton("Ok"){
//                    dialog , which ->
//
//
//                ActivityCompat.requestPermissions(this@ReportproblemActivity, arrayOf(permission),requestCode)
//            }
//
//        }
//
//        val dialog = builder.create()
//        dialog.show()
//
//
//    }



    private fun uploadimagetostorage(){


        val uid = UUID.randomUUID()


        val dbstorage = FirebaseStorage.getInstance().getReference("ReportImages").child(uid.toString())
        dbstorage.putFile(imageuri).addOnSuccessListener {

            Log.d("Report","report image uploaded")
            dbstorage.downloadUrl.addOnSuccessListener {

                Log.d("Report","report image download")
                reportdb(it.toString())
            }.addOnFailureListener{
                Log.d("Report","failed to upload image")
            }

        }

    }


    private fun retreiveuser(){

        val uid = auth.currentUser?.uid


        val db = database.collection("Customers").whereEqualTo("uid",uid)
        db.addSnapshotListener { value, error ->
            if (error != null) {

                Toast.makeText(
                   applicationContext,
                    "something went wrong",
                    Toast.LENGTH_SHORT
                ).show()

            }

            for (dc: DocumentChange in value?.documentChanges!!) {

                if (dc.type == DocumentChange.Type.ADDED) {


                    val customer = dc.document.toObject(Customer::class.java)

                    name = customer.name.toString()
                    number = customer.number.toString()


                }

            }
        }


    }



    private fun reportdb(attachedfile:String){


        val id = UUID.randomUUID().toString()
        useruid = auth.currentUser?.uid.toString()

        message = messageedxt.text.toString()
        val time = System.currentTimeMillis()




        val report = Report(id, useruid,number,message,name,location,time,lat,long,attachedfile)


        database.collection("Reports").document(id).set(report).addOnCompleteListener {



            if (!it.isSuccessful)return@addOnCompleteListener


            loading.isDismiss()


            val intent = Intent(this,HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()



        }.addOnFailureListener {

            Toast.makeText(this,"User Registered", Toast.LENGTH_SHORT).show()


        }


    }





}