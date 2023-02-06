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
        book.src="http://www.tlxs.net/"+book_src!!
        book.image="http://www.tlxs.net/"+book_img!!
        book.introduction=book_introudution!!
        book.writer=book_writer!!
        Books.add(book)
        }
        return Books
    }

    fun getsousuoBooks(html:String):List<Book>{
        val doc = Jsoup.parse(html)
        val body=doc.getElementsByTag("body").get(0)
        val item=body.getElementsByClass("bookbox")
        val Books= ArrayList<Book>()
        for(item_book in item){
            var book=Book()
            val name=item_book.getElementsByClass("bookname").first()
            val img=item_book.getElementsByClass("bookimg").first()?.getElementsByTag("img")?.first()
            val introduction=item_book.getElementsByTag("p").first()
            val writer=item_book.getElementsByClass("author").first()
            val book_name=name?.text()
            val book_src=name?.getElementsByTag("a")?.attr("href")
            val book_img=img?.attr("src")
            val book_writer=writer?.text()
            val book_introudution=introduction?.text()
            book.name=book_name!!
            book.src="http://www.tlxs.net/"+book_src!!
            book.image="http://www.tlxs.net/"+book_img!!
            book.introduction=book_introudution!!
            book.writer=book_writer!!
            Books.add(book)
        }
        return Books
    }

    fun getBookInfo(html: String,book: Book):ArrayList<Charpter>?{
        val doc=Jsoup.parse(html)
        val body=doc.getElementsByTag("body").get(0)
        val element=body.getElementsByClass("listmain").get(0).getElementsByTag("dd")
        var ChapterList= ArrayList<Charpter>()
        for (chp in element!!){
            var charpter=Charpter()
            val a=chp.getElementsByTag("a").get(0)
            charpter.Charpter_name=a.text()
            charpter.Charpter_uri="http://www.tlxs.net/"+a.attr("href")
            ChapterList.add(charpter)
        }
        return  ChapterList
    }

    fun getCharpterInfo(html: String,charpter: Charpter):Charpter{
        val new_html=html.replace("<br />","/kg").replace("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;","/hkt")
        val doc=Jsoup.parse(new_html)
        val body=doc.getElementsByTag("body").get(0)
        val book=body.getElementById("book")
        var content=body.getElementById("content")?.text()
        content=content!!.replace("/kg","\n").replace("/hkt","      ")
            .replace("\n \n","\n")
        charpter.Charpter_neirong=content!!
        return charpter
    }

}
