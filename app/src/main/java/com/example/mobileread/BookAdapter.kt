package com.example.mobileread

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class BookAdapter(val BooksList: List<Book>, val context:Activity?):
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    interface itemClick {
        fun onItemClick(view: View, int: Int)
    }
    private lateinit var moitmClick: itemClick
    fun setItemClick(itemClick: itemClick) {
        moitmClick = itemClick
    }


    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val bookname:TextView=view.findViewById(R.id.book_name)
        val bookimg:ImageView=view.findViewById(R.id.imageView_book)
        val bookwriter:TextView=view.findViewById(R.id.book_writer)
        val bookjianjie:TextView=view.findViewById(R.id.book_jianjie)
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=BooksList.size
}