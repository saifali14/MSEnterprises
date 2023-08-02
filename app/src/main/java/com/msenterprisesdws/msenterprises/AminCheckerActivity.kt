package com.msenterprisesdws.msenterprises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AminCheckerActivity : AppCompatActivity() {



    private lateinit var passedxt:EditText
    private lateinit var adminsubmitbtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amin_checker)



        init()

        adminsubmitbtn.setOnClickListener {

            passchecker()
        }

    }

    private fun passchecker() {

        val pass = "98260"
        val epass = passedxt.text.toString()

        if (epass == pass){

            passedxt.text.clear()

            val intent = Intent(this,AdminActivity::class.java)
            startActivity(intent)
            finish()
        }else{

            Toast.makeText(this,"entered the wrong pass",Toast.LENGTH_SHORT).show()
            passedxt.text.clear()
        }


    }

    private fun init(){

        passedxt = findViewById(R.id.adminc_passcedxt)
        adminsubmitbtn = findViewById(R.id.adminc_btn)


    }
}