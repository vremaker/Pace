package edu.washington.info448.pace

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class feedback_give : Fragment() {
    private var callback: submitListener? = null
    private val fb = FirebaseDatabase.getInstance().reference
    interface submitListener {
        fun returnHome()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? submitListener
        if (callback == null) {
            throw ClassCastException("$context must implement submitListener")
        }
    }

    companion object {
        fun newInstance(): feedback_give {
            val args = Bundle().apply {
            }
            val fragment = feedback_give().apply {
                setArguments(args)
            }
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_feedback_give, container, false)
        arguments?.let {
            val prof = rootView.findViewById<EditText>(R.id.prof)
            val feed = rootView.findViewById<EditText>(R.id.feedback)
            val course = rootView.findViewById<EditText>(R.id.courseId)
            val button = rootView.findViewById<Button>(R.id.submit)
            button.setOnClickListener() {
                if(prof.text.length > 0 && feed.text.length > 0 && course.text.length > 0) {
                    dealWithDataBase(prof.text.toString(), feed.text.toString(), course.text.toString())
                    callback!!.returnHome()
                } else {
                    Toast.makeText(this.context, "You need to fill out all fields to submit feedback", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return rootView
    }

    fun dealWithDataBase(prof:String, feed:String, course: String) {
        val course = course.trim()
        val feed = feed.trim()
        val data = mapOf("course" to course, "feed" to feed)
        fb.child("prof").child(prof.trim()).push().setValue(data)
    }
}