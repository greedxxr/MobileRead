package com.example.mobileread

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_book.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.io.Serializable

class BookActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        val book=intent.getSerializableExtra("book")as Book
        var ChapterList= ArrayList<Charpter>()
        textView_bookname.text=book.name
        textView_bookwriter.text=book.writer
        textView_bookjianjie.text=book.introduction
        Glide.with(this).load(book.image).into(imageView_bookimg)
        OkHttpUtils.sendRequestWithOkHttp(book.src,object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                ChapterList=OkHttpUtils.getBookInfo(responseData!!,book)!!
            }
        })
        button_yuedu.setOnClickListener {
            val intent =Intent(this,ReadActivity::class.java)
            if(ChapterList.isNotEmpty())
            {
                Sent_CharpterList.CharpterList=ChapterList
              //  intent.putExtra("charpters", ChapterList as Serializable)
                startActivity(intent)}
            else
                Toast.makeText(this,"加载中,请稍等",Toast.LENGTH_SHORT).show()
        }

        button_jiaru.setOnClickListener {
            val dbHelper = MyDatabaseHelper(this, "DataBase", 2)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("book_name", book.name)
                put("writer", book.writer)
                put("introduction", book.introduction)
                put("img_src", book.image)
                put("book_src", book.src)
            }
            db.insert("Book", null, values)
            val user = application as User
            val values2 = ContentValues().apply {
                put("account", user.account)
                put("book_name", book.name)
            }
            db.insert("UwithB", null, values2)
            db.close()
            Toast.makeText(this, "加入书架成功", Toast.LENGTH_SHORT).show()
        }
    }
}