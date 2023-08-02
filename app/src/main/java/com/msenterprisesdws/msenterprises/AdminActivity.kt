package com.msenterprisesdws.msenterprises

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminActivity : AppCompatActivity() {


    private lateinit var database : FirebaseFirestore
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<Report>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        database = FirebaseFirestore.getInstance()


        userRecyclerview = findViewById(R.id.admin_recyclerview)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()
    }

    private fun getUserData() {



        val db = database.collection("Reports")
            .orderBy("time", Query.Direction.DESCENDING)

        db.addSnapshotListener { value, error ->
            if (error != null) {

                Toast.makeText(applicationContext,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()

            }


            for (dc: DocumentChange in value?.documentChanges!!) {

                if (dc.type == DocumentChange.Type.ADDED) {


                    val report = dc.document.toObject(Report::class.java)
                    userArrayList.add(report)



                }
                userRecyclerview.adapter = MyAdapter(userArrayList)


            }

        }


    }
}