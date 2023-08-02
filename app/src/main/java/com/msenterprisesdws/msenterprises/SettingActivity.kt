package com.msenterprisesdws.msenterprises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.cardview.widget.CardView

class SettingActivity : AppCompatActivity() {


    private lateinit var backnbtn:ImageView
    private lateinit var stadmincard:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        init()


        backnbtn.setOnClickListener{
            finish()
        }

        stadmincard.setOnClickListener {

            val intent = Intent(this,AminCheckerActivity::class.java)
            startActivity(intent)

        }




    }


    private fun init(){

        backnbtn = findViewById(R.id.setting_backbtn)
        stadmincard = findViewById(R.id.setting_admincard)


    }
}