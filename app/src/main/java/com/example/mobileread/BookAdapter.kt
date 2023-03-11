package com.example.mobileread

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.book_item.view.*


class BookAdapter(val BooksList: List<Book>, val context:Activity?,val p1:Int):
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    interface itemClick {
        fun onItemClick(view: View, int: Int)
    }
    private lateinit var moitmClick: itemClick
    fun setItemClick(itemClick: itemClick) {
        moitmClick = itemClick
    }

    interface shanchuClick {
        fun onshanchuClick(view: View, int: Int)
    }
    private lateinit var moshanchuClick: shanchuClick
    fun setshanchuClick(shanchuClick: shanchuClick) {
        moshanchuClick = shanchuClick
    }


    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val bookname:TextView=view.findViewById(R.id.book_name)
        val bookimg:ImageView=view.findViewById(R.id.imageView_book)
        val bookwriter:TextView=view.findViewById(R.id.book_writer)
        val bookjianjie:TextView=view.findViewById(R.id.book_jianjie)
        val button_shanchu:androidx.appcompat.widget.AppCompatImageButton =view.findViewById(R.id.button_shanchu)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book=BooksList[position]
        val book_uri=book.image.toUri()
        Glide.with(context!!).load(book_uri).into(holder.bookimg)
        holder.bookname.text=book.name
        holder.bookwriter.text=book.writer
        holder.bookjianjie.text=book.introduction

        holder.itemView.setOnClickListener {
            moitmClick.onItemClick(holder.itemView, position)
        }

        holder.button_shanchu.setOnClickListener {
            moshanchuClick.onshanchuClick(holder.itemView,position)
        }
        if(p1==1){
            holder.button_shanchu.visibility=View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=BooksList.size
}