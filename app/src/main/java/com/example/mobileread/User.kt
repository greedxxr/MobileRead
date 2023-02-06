package com.example.mobileread

import android.app.Application

class User:Application() {
    lateinit var account:String


    override fun onCreate() {
        super.onCreate()
        account =""
    }
}