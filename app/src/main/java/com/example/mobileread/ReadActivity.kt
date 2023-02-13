package com.example.mobileread


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_read.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import java.io.Serializable
import java.text.FieldPosition


class ReadActivity : BaseActivity() {
    var CharpterList=ArrayList<Charpter>()
    lateinit var pagerAdapter:ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        CharpterList=intent.getSerializableExtra("charpters")as ArrayList<Charpter>
        pagerAdapter=ViewPagerAdapter(CharpterList)
        view_page2.adapter=pagerAdapter
        //加载第0章
        OkHttpUtils.sendRequestWithOkHttp(
            CharpterList[0].Charpter_uri,
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    CharpterList[0] = OkHttpUtils.getCharpterInfo(responseData!!, CharpterList[0])
                    showResponse(CharpterList,0,pagerAdapter)
                    Log.d("Book", "第-1章加载成功")
                }
            })
        //第n章时加载下一章或者上一章
        view_page2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //非最后一章时加载下一章
                if (position<CharpterList.size-1){
                if(CharpterList[position+1].Charpter_neirong.isEmpty())
                OkHttpUtils.sendRequestWithOkHttp(
                    CharpterList[position+1].Charpter_uri,
                    object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                        }
                        override fun onResponse(call: Call, response: Response) {
                            val responseData = response.body?.string()
                            CharpterList[position+1] =
                                OkHttpUtils.getCharpterInfo(responseData!!, CharpterList[position+1])
                            showResponse(CharpterList,position+1,pagerAdapter)
                            Log.d("Book", "{$position}下一章加载成功加载成功")
                        }
                    })}
                //非第一章时加载上一章
                if (position>0){
                if(CharpterList[position-1].Charpter_neirong.isEmpty())
                    OkHttpUtils.sendRequestWithOkHttp(
                        CharpterList[position-1].Charpter_uri,
                        object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                            }
                            override fun onResponse(call: Call, response: Response) {
                                val responseData = response.body?.string()
                                CharpterList[position-1] =
                                    OkHttpUtils.getCharpterInfo(responseData!!, CharpterList[position-1])
                                showResponse(CharpterList,position-1,pagerAdapter)
                                Log.d("Book", "{$position}上一 章加载成功加载成功")
                            }
                        }) }
            }
        })

        radioButton_mulu.setOnClickListener{
            val intent =Intent(this,muluActivity::class.java)
            intent.putExtra("chp",CharpterList as Serializable)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->if(resultCode== RESULT_OK){
                var position=data!!.getIntExtra("zjh",0)
                view_page2.setCurrentItem(position)
                OkHttpUtils.sendRequestWithOkHttp(
                    CharpterList[position].Charpter_uri,
                    object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                        }
                        override fun onResponse(call: Call, response: Response) {
                            val responseData = response.body?.string()
                            CharpterList[position] =
                                OkHttpUtils.getCharpterInfo(responseData!!, CharpterList[position])
                            showResponse(CharpterList,position,pagerAdapter)
                            Log.d("Book", "第{$position}章加载成功加载成功")
                        }
                    })
            }
        }
    }

    private fun showResponse(charpterlist:ArrayList<Charpter>,position:Int,pagerAdapter: ViewPagerAdapter){
        runOnUiThread {
            pagerAdapter.CharpterList[position].Charpter_neirong=charpterlist[position].Charpter_neirong
            pagerAdapter.notifyItemChanged(position)
        }
    }
}