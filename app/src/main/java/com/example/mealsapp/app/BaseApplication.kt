package com.example.mealsapp.app

import android.app.Application
import android.content.Context

class BaseApplication : Application (){

    override fun onCreate() {
        super.onCreate()
        context=this
    }

    companion object{
        lateinit var  context:Context
    }

}