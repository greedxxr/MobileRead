package com.example.mobileread

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(val CharpterList:ArrayList<Charpter>):
    RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    class MyViewHolder(view :View) :RecyclerView.ViewHolder(view){
        val charptername:TextView=view.findViewById(R.id.textView_zhangjie)
        val charpterneirong:TextView=view.findViewById(R.id.textView_neirong)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.charpter_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.MyViewHolder, position: Int) {
        val charpter=CharpterList[position]
        holder.charptername.text=charpter.Charpter_name
        holder.charpterneirong.text=charpter.Charpter_neirong

    }

    override fun getItemCount()=CharpterList.size
}