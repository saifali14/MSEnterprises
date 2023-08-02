package com.msenterprisesdws.msenterprises

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NameInfoActivity : AppCompatActivity() {


    private lateinit var database: FirebaseFirestore

    private lateinit var auth: FirebaseAuth


    private lateinit var nameedxt:EditText
    private lateinit var submitbn:Button
    private lateinit var name:String
    private lateinit var pnumber:String
    private lateinit var location:String

    val FINE_LOCATION_RO  =101
    val STORAGE_RO = 102
    val WSTORAGE_RO = 103









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_info)


        init()






        pnumber = intent.getStringExtra(OTPActivity.PHONE_TAG)!!



//        checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location",FINE_LOCATION_RO)
//        checkForPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, "read storage",
//            STORAGE_RO)
//        checkForPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, "write storage",
//            WSTORAGE_RO)

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.READ_MEDIA_IMAGES)



        permissionLauncherMultiple.launch(permissions)


        submitbn.setOnClickListener {

            createdb()

        }






    }

    private fun createdb() {

        val id = auth.currentUser?.uid
        Log.d("TAG","user id is $id")
        location = "nani ka ghar"
        name = nameedxt.text.toString()
        val customer = Customer(id.toString(),pnumber,name,location)

        val db = database.collection("Customers").document(pnumber)
        db.set(customer).addOnCompleteListener {

            if (!it.isSuccessful)return@addOnCompleteListener

            Toast.makeText(this,"account created",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {


            Toast.makeText(this,"Failed to create account",Toast.LENGTH_SHORT).show()
            Log.d("TAG",it.message.toString())

        }

    }


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

        val builder = AlertDialog.Builder(this)

        builder.apply {


            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK"){dialog, which ->
                ActivityCompat.requestPermissions(this@NameInfoActivity, arrayOf(permission),requestCode)
            }

        }

        val dialog:AlertDialog = builder.create()
        dialog.show()

    }


    private fun init(){

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        nameedxt = findViewById(R.id.inof_nameedxt)
        submitbn = findViewById(R.id.info_submitbtn)



    }

}