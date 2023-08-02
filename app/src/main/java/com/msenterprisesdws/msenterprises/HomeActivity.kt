package com.msenterprisesdws.msenterprises

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Math.abs
import java.util.Calendar

class HomeActivity : AppCompatActivity() {



    private lateinit var  viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter
    private lateinit var dateview:TextView
    private lateinit var settingbtn:ImageView
    private lateinit var previouscard:CardView
    private lateinit var reportcard:CardView
    private lateinit var facilitycard:CardView



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        dateview = findViewById(R.id.home_datetext)


        init()
        setUpTransformer()
        daysetter()





        settingbtn.setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }

        previouscard.setOnClickListener{

            val intent = Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }

        reportcard.setOnClickListener {
            val intent = Intent(this,ReportproblemActivity::class.java)
            startActivity(intent)

        }
        facilitycard.setOnClickListener {
            val intent = Intent(this,FacilityprovideActivity::class.java)
            startActivity(intent)
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 3000)
            }
        })
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 3000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init(){
        viewPager2 = findViewById(R.id.home_imageslider)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        settingbtn = findViewById(R.id.home_settingbtn)
        previouscard = findViewById(R.id.home_preprojectcard)
        reportcard = findViewById(R.id.home_reportcard)
        facilitycard = findViewById(R.id.home_facilitycard)

        imageList.add(R.drawable.img1)
        imageList.add(R.drawable.img2)
        imageList.add(R.drawable.img3)
        imageList.add(R.drawable.img4)




        adapter = ImageAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }


    private fun daysetter(){

        val cal = Calendar.getInstance()

        val daycode = cal.get(Calendar.DAY_OF_WEEK)
        val date = cal.get(Calendar.DATE)
        val monthcode = cal.get(Calendar.MONTH)

        val day = DAYSelector(daycode)
        val month = MONThSelector(monthcode)

        Log.d("DAY", (day))

        Log.d("DAY", (date.toString()))
        Log.d("DAY",month)


        dateview.text = "$day, $date $month"





    }


    private fun DAYSelector(code:Int): String {

        var lday = "Mon"

        when(code) {

            1  -> { lday =  "Sun"}

            2 -> {lday =  "Mon"}

            3 -> { lday = "Tues"}

            4 -> { lday =  "Wed"}

            5 -> { lday =  "Thu"}

            6 -> { lday =  "Fri"}

            7 -> { lday =  "Sat"}

        }


        return lday

    }


    private fun MONThSelector(codem:Int):String{

        var lmon = "January"

        when(codem){

            0 ->{ lmon = "January" }

            1 ->{ lmon = "February" }

            2 ->{ lmon = "March" }

            3 ->{ lmon = "April" }

            4 ->{ lmon = "May" }

            5 ->{ lmon = "June" }

            6 ->{ lmon = "July" }

            7 ->{ lmon = "August" }

            8 ->{ lmon = "September" }

            9 ->{ lmon = "October" }

            10 ->{ lmon = "November" }

            11 ->{ lmon = "December" }


        }


        return lmon


    }










}