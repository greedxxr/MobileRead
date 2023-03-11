package com.example.mobileread

import android.app.Activity
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import java.lang.Exception
import kotlin.concurrent.thread

object OkHttpUtils {
    fun sendRequestWithOkHttp(address:String,callback: okhttp3.Callback){
        val client=OkHttpClient()
        val request=Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback) }

    fun getBooks(html:String):List<Book>{
        val doc = Jsoup.parse(html)
        val body=doc.getElementsByTag("body").get(0)
        val item=body.getElementsByClass("item")
        val Books= ArrayList<Book>()
        for(item_book in item){
        var book=Book()
        val name=item_book.getElementsByTag("dt").first()?.getElementsByTag("a")?.first()
        val img=item_book.getElementsByClass("image").first()?.getElementsByTag("img")?.first()
        val introduction=item_book.getElementsByTag("dd").first()
        val writer=item_book.getElementsByTag("dt").first()?.getElementsByTag("span")?.first()
        val book_name=name?.text()
        val book_src=name?.attr("href")
        val book_img=img?.attr("src")
        val book_writer=writer?.text()
        val book_introudution=introduction?.text()
        book.name=book_name!!
        book.src="https://www.23sk.net/"+book_src!!
        book.image="https://www.23sk.net/"+book_img!!
        book.introduction=book_introudution!!
        book.writer=book_writer!!
        Books.add(book)
        }
        return Books
    }

    fun getsousuoBooks(html:String):List<Book>{
        val doc = Jsoup.parse(html)
        val body=doc.getElementsByTag("body").get(0)
        val item=body.getElementsByClass("result-item result-game-item")
        val Books= ArrayList<Book>()
        for(item_book in item){
            var book=Book()
            val name=item_book.getElementsByClass("result-item-title result-game-item-title").first()
            val img=item_book.getElementsByClass("result-game-item-pic").first()?.getElementsByTag("img")?.first()
            val introduction=item_book.getElementsByClass("result-game-item-desc").first()
            val writer=item_book.getElementsByClass("result-game-item-info-tag").first()
            val book_name=name?.text()
            val book_src=name?.getElementsByTag("a")?.attr("href")
            val book_img=img?.attr("src")
            val book_writer=writer?.text()
            val book_introudution=introduction?.text()
            book.name=book_name!!
            book.src="https://www.23sk.net/"+book_src!!
            book.image="https://www.23sk.net/"+book_img!!
            book.introduction=book_introudution!!
            book.writer=book_writer!!
            Books.add(book)
        }
        return Books
    }

    fun getBookInfo(html: String,book: Book):ArrayList<Charpter>?{
        val doc=Jsoup.parse(html)
        val body=doc.getElementsByTag("body").get(0)
        val element=body.id("list").getElementsByTag("dd")
        var ChapterList= ArrayList<Charpter>()
        for (chp in element!!){
            var charpter=Charpter()
            val a=chp.getElementsByTag("a").get(0)
            charpter.Charpter_name=a.text()
            charpter.Charpter_uri="https://www.23sk.net/"+a.attr("href")
            ChapterList.add(charpter)
        }
        return  ChapterList
    }

    fun getCharpterInfo(html: String,charpter: Charpter):Charpter{
        val new_html=html.replace("<br><br>","/kg")
        val doc=Jsoup.parse(new_html)
        val body=doc.getElementsByTag("body").get(0)
        val book=body.getElementById("book")
        var content=body.getElementById("content")?.text()
        content=content!!.replace("/kg","\n")
        charpter.Charpter_neirong=content!!
        return charpter
    }

}
