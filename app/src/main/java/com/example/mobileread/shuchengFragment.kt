package com.example.mobileread

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_shucheng.*
import okhttp3.*
import java.io.IOException


class shuchengFragment : Fragment() {

var address="http://www.tlxs.net/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shucheng, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //加载首页
        OkHttpUtils.sendRequestWithOkHttp(address,object :Callback{
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                radioButton_shouye.isChecked=true
                val responseData = response.body?.string()
                val Books=OkHttpUtils.getBooks(responseData!!)
                showResponse(Books)
            }
        })
        //导航栏分类切换
      RadioGroup_fenglei.setOnCheckedChangeListener(
          object :RadioGroup.OnCheckedChangeListener{
          override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
              when(p1){
                  radioButton_shouye.id->address="http://www.tlxs.net/"
                  radioButton_xuanhuan.id->address="http://www.tlxs.net/xuanhuan/"
                  radioButton_xiuzhen.id->address="http://www.tlxs.net/xiuzhen/"
                  radioButton_dushi.id->address="http://www.tlxs.net/dushi/"
                  radioButton_lishi.id->address="http://www.tlxs.net/lishi/"
                  radioButton_wangyou.id->address="http://www.tlxs.net/wangyou/"
                  radioButton_kehuan.id->address="http://www.tlxs.net/kehuan/"
                  radioButton_yanqing.id->address="http://www.tlxs.net/yanqing/"
              }
              OkHttpUtils.sendRequestWithOkHttp(address,object :Callback{
                  override fun onFailure(call: Call, e: IOException) {
                  }

                  override fun onResponse(call: Call, response: Response) {
                      val responseData = response.body?.string()
                      val Books=OkHttpUtils.getBooks(responseData!!)
                      showResponse(Books)
                  }
              })
          }
      })
        //搜索
        button_sousuo.setOnClickListener {
            var new_book_name=editText_sousuo.text
            address="http://www.tlxs.net/s.php?ie=gbk&q="+new_book_name
            OkHttpUtils.sendRequestWithOkHttp(address,object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                }
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    val Books=OkHttpUtils.getsousuoBooks(responseData!!)
                    showResponse(Books)
                }
            })
        }
    }
    private fun showResponse(response: List<Book>?){
        activity?.runOnUiThread {
        val layoutManager = LinearLayoutManager(activity)
        shucheng_recycleview.layoutManager=layoutManager
        val adapter=BookAdapter(response!!,activity)
        shucheng_recycleview.adapter=adapter
        adapter.setItemClick(object :BookAdapter.itemClick{
            override fun onItemClick(view: View, position: Int) {
                val intent=Intent(activity,BookActivity::class.java)
                intent.putExtra("book",response[position])
                startActivity(intent)
            }
        })
        }
    }
}