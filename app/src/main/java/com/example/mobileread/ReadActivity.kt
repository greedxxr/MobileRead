package com.example.mobileread


import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_read.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import java.text.FieldPosition


class ReadActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        var CharpterList=intent.getSerializableExtra("charpters")as ArrayList<Charpter>
        var pagerAdapter=ViewPagerAdapter(CharpterList)
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
        //第n章时加载下一章
        view_page2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
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
                            Log.d("Book", "第{$position}章加载成功加载成功")
                        }
                    }) }
        })
    }

    private fun showResponse(charpterlist:ArrayList<Charpter>,position:Int,pagerAdapter: ViewPagerAdapter){
        runOnUiThread {
            pagerAdapter.CharpterList[position].Charpter_neirong=charpterlist[position].Charpter_neirong
            pagerAdapter.notifyItemChanged(position)
        }
    }
}