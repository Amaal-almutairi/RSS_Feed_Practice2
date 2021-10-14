package com.example.rss_feed_practice2

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var studentsdetails = mutableListOf<XMLParser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView= findViewById(R.id.rv)
        FechStudents().execute()
    }

    private inner class FechStudents : AsyncTask<Void, Void, MutableList<Student>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<Student> {
            val url = URL("https://stackoverflow.com/feeds/.xml")
            val urlConnection = url.openConnection() as HttpURLConnection
            studentsdetails =  urlConnection.getInputStream()?.let {
                parser.parse(it)
            }

            return studentsdetails as MutableList<Student>
        }

        override fun onPostExecute(result: MutableList<Student>?) {
            super.onPostExecute(result)

        }

    }

}