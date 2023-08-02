package com.msenterprisesdws.msenterprises

data class Report(val id:String?="",
                  val ueruid:String?="",
                  val number:String?="",
                  val message:String?="",
                  val name:String?="",
                  val location:String?="",
                  val time:Long?=null,
                  val lat:Long?=null,
                  val lon:Long?=null,
                  val attachfile:String?="")
