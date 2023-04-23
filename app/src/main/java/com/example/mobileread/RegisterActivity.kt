package com.example.mobileread

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream
import java.net.URI
import java.net.URL

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val dbHelper = MyDatabaseHelper(this,"DataBase",2)
        val db = dbHelper.writableDatabase
        imageView_touxiang.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            intent.putExtra("return-data",true)
            startActivityForResult(intent,1)
        }
        button_send.setOnClickListener {
            val account=new_account.text.toString()
            val password =new_password.text.toString()
            val password_repeat=new_password_repeat.text.toString()
            val name=new_name.text.toString()
            val bmp=imageView_touxiang.drawable.toBitmap()
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG,100,stream)
            if(account.isNotEmpty() and password.isNotEmpty() and name.isNotEmpty() and password_repeat.isNotEmpty()){
            if (password.equals(password_repeat)) {
                val values = ContentValues().apply {
                    put("account",account)
                    put("password",password)
                    put("name",name)
                    put("image",stream.toByteArray())
                }
                db.insert("User",null,values)
                db.close()
                Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show()
            }
            else {
                new_password.text.clear()
                new_password_repeat.text.clear()
                Toast.makeText(this, "密码和确认密码不一致，注册失败", Toast.LENGTH_SHORT).show()
                }
            finish()

            }
            else{
                Toast.makeText(this,"不能为空",Toast.LENGTH_SHORT).show()
            }
        }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if(requestCode==1){
                val uri=data?.data
                imageView_touxiang.setImageURI(uri)
            }
        }
    }
}