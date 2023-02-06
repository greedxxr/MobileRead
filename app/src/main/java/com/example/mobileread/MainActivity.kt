package com.example.mobileread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.concurrent.thread

class MainActivity : BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initview()
    }

    private fun initview(){
        val sjfragment= shujiaFragment()
        val scfragment=shuchengFragment()
        val wdfragment=wodeFragment()

        //传递用户信息给wdfragment
        val LtoMaccount=intent.getStringExtra("account")
        val bundle=Bundle()
        bundle.putString("account",LtoMaccount)
        wdfragment.arguments=bundle

        radioButton_shujia.isChecked=true
        val fragmentlist = arrayListOf<Fragment>().apply {
            add(sjfragment)
            add(scfragment)
            add(wdfragment)
        }
        val pagerAdapter = MyFragmentPagerAdapter(this)
        pagerAdapter.fragments=fragmentlist
        view_page.adapter=pagerAdapter
        
        //viewpage监听事件
        view_page.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 ->radioButton_shujia.isChecked=true
                    1 ->radioButton_shucheng.isChecked=true
                    2 ->radioButton_wode.isChecked=true
                    else->radioButton_shujia.isChecked=true
                }
            } })

        //导航栏监听事件
        radiogroup.setOnCheckedChangeListener( object :RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when(p1){
                    radioButton_shujia.id->view_page.setCurrentItem(0)
                    radioButton_shucheng.id->view_page.setCurrentItem(1)
                    radioButton_wode.id->view_page.setCurrentItem(2)
                }
            }
        })
    }

}