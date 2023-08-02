package com.msenterprisesdws.msenterprises

import android.app.Activity
import android.app.AlertDialog

class RegisterationLoadingbar(val mActivity: Activity) {


    private lateinit var isdialog: AlertDialog


    fun startloading(){


        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_report,null)

        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)

        isdialog = builder.create()
        isdialog.show()


    }


    fun isDismiss(){

        isdialog.cancel()


    }


}
