package com.example.mobileread

import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import kotlinx.android.synthetic.main.fragment_wode.*

class wodeFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wode_account=arguments?.getString("account")
        val dbHelper = MyDatabaseHelper(activity,"DataBase",2)
        val db = dbHelper.writableDatabase
        val cursor =db.rawQuery("select * from User Where account=?", arrayOf(wode_account))
        cursor.moveToFirst()
        val wode_name=cursor.getString(cursor.getColumnIndexOrThrow("name"))
        val wode_touxiang=cursor.getBlob(cursor.getColumnIndexOrThrow("image"))
        textView_account.text=wode_account
        textView_name.text=wode_name
        val opts=BitmapFactory.Options()
        imageView_touxiang2.setImageBitmap( BitmapFactory.decodeByteArray(wode_touxiang,0,wode_touxiang.count(),opts))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wode, container, false)
    }

}