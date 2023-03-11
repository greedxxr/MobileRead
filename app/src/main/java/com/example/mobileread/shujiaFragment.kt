package com.example.mobileread

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.book_item.*
import kotlinx.android.synthetic.main.book_item.view.*
import kotlinx.android.synthetic.main.fragment_shucheng.*
import kotlinx.android.synthetic.main.fragment_shujia.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import kotlin.concurrent.thread

class shujiaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shujia, container, false)
    }


    override fun onResume() {
        super.onResume()

        sxym()
    }

    fun sxym(){
        val Books=ArrayList<Book>()
        var layoutManager = LinearLayoutManager(activity)
        shujia_recycleview.layoutManager=layoutManager
        var adapter=BookAdapter(Books,activity,1)
        shujia_recycleview.adapter=adapter
        //加载数据库
        val dbHelper = MyDatabaseHelper(activity,"DataBase",2)
        val db = dbHelper.writableDatabase
        val user=activity?.application as User
        val cursor = db.rawQuery("select * from UwithB Where account=?", arrayOf(user.account))
        if (cursor.moveToFirst()){
            do {
                var bookname=cursor.getString(cursor.getColumnIndexOrThrow("book_name"))
                var cursor2=db.rawQuery("select * from Book Where book_name=?", arrayOf(bookname))
                cursor2.moveToFirst()
                val nBook=Book()
                nBook.setBook(
                    cursor2.getString(cursor2.getColumnIndexOrThrow("book_name")),
                    cursor2.getString(cursor2.getColumnIndexOrThrow("img_src")),
                    cursor2.getString(cursor2.getColumnIndexOrThrow("introduction")),
                    cursor2.getString(cursor2.getColumnIndexOrThrow("writer")),
                    cursor2.getString(cursor2.getColumnIndexOrThrow("book_src")),
                )
                cursor2.close()
                Books.add(nBook)
            }while (cursor.moveToNext())
            cursor.close()
        }

        adapter.setItemClick(object :BookAdapter.itemClick{
            override fun onItemClick(view: View, position: Int) {
                val intent= Intent(activity,BookActivity::class.java)
                intent.putExtra("book",Books[position])
                startActivity(intent)
            }
        })

        adapter.setshanchuClick(object :BookAdapter.shanchuClick{
            override fun onshanchuClick(view: View, int: Int) {
                    val dbHelper = MyDatabaseHelper(activity,"DataBase",2)
                    val db = dbHelper.writableDatabase
                    val user=activity?.application as User
                    db.execSQL("delete from UwithB where book_name = ? and account= ?", arrayOf(view.book_name.text,user.account))
                    onResume()
            }
        })
    }
}