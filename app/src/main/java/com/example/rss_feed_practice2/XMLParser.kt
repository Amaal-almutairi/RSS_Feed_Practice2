package com.example.rss_feed_practice2

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream


data class Student(
    var id: Int = 0,
    var name: String? = null,
    var marks: Float = 0.toFloat() ){

    override fun toString(): String {
        return " Id = $id \n Name = $name \n Salary = $marks"
    }
}


class XMLParser {
    private val NS: String? = null

    fun parse(inputStream: InputStream): List<Student> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readSongsRssFeed(parser)
        }


    }

    private fun readSongsRssFeed(parser: XmlPullParser): List<Student> {
        val students_info = mutableListOf<Student>()
        parser.require(XmlPullParser.START_TAG, NS, "feed")

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            if (parser.name == "entry") {
                parser.require(XmlPullParser.START_TAG, NS, "entry")
                var marks: String? = null
                var name: String? = null
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue
                    }
                    when (parser.name) {
                        "title" -> marks = readTitle(parser)
                        "im:name" -> name = readName(parser)
                        else -> skip(parser)
                    }
                }
                students_info.add(Student(marks,name))
            } else {
                skip(parser)
            }
        }
        return students_info
    }

    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, NS, "title")
        val marks = readText(parser)
        parser.require(XmlPullParser.END_TAG, NS, "title")
        return marks
    }

    private fun readName(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, NS, "im:name")
        val summary = readText(parser)
        parser.require(XmlPullParser.END_TAG, NS, "im:name")
        return summary
    }

    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}