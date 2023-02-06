package com.example.mobileread

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_shucheng.*
import kotlinx.android.synthetic.main.fragment_shujia.*

class shujiaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shujia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sxym()
    }

    override fun onResume() {
        super.onResume()
        sxym()
    }

    fun sxym(){
        val Books=ArrayList<Book>()
        val layoutManager = LinearLayoutManager(activity)
        shujia_recycleview.layoutManager=layoutManager
        val adapter=BookAdapter(Books,activity)
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
    }
}