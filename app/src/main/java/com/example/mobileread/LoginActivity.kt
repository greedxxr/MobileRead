package com.example.mobileread

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val dbHelper = MyDatabaseHelper(this,"DataBase",2)
        button_register.setOnClickListener {
            val intent1 = Intent(this,RegisterActivity::class.java)
            startActivity(intent1)
        }
        button_login.setOnClickListener {
            val db = dbHelper.writableDatabase
            val uaccount=account.text.toString()
            val upassword=password.text.toString()
            val cursor = db.rawQuery("select * from User Where account=?", arrayOf(uaccount))
            if(cursor.count!=0 && uaccount.isNotEmpty()){
                cursor.moveToFirst()
            if (cursor.getString(1)==upassword){
                cursor.close()
                val user=application as User
                user.account=uaccount
                db.close()
                val intent2 = Intent(this,MainActivity::class.java)
                intent2.putExtra("account",uaccount)
                startActivity(intent2)
                finish()
            }
            else{
                cursor.close()
                Toast.makeText(this,"密码或错误",Toast.LENGTH_LONG).show()
                account.text.clear()
                password.text.clear() }
            }
            else{
                cursor.close()
                account.text.clear()
                password.text.clear()
                Toast.makeText(this,"账号错误",Toast.LENGTH_LONG).show()
            }
        }
    }
}