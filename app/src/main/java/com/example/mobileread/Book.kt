package com.example.mobileread


import android.media.Image
import java.io.*

class Book : Serializable {
    lateinit var name:String
    lateinit var image:String
    lateinit var introduction:String
    lateinit var writer:String
    lateinit var src:String
    lateinit var CharpterList:List<Charpter>

    fun setBook(name:String,image: String,introduction:String,
    writer:String,src:String){
        this.name=name
        this.image=image
        this.src=src
        this.writer=writer
        this.introduction=introduction
    }
}