package edu.washington.info448.pace

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_discuss.*
import kotlinx.android.synthetic.main.fragment_add_resource.*
import kotlinx.android.synthetic.main.fragment_add_resource.add_my_resource_btn
import java.text.SimpleDateFormat
import java.util.*


class AddPost : DialogFragment() {
    interface DialogListener {
        fun onSelectDoneDialog(inputText: String, inputTag: String)
        fun onCancelDialog(cancelDialog: Unit)
    }

    companion object {
        fun newInstance(classID: String): AddPost {
            return AddPost().apply {
                arguments = Bundle().apply {
                    putString("CLASS", classID)
                }
            }
        }
    }

    private lateinit var fb : DatabaseReference
    private val sdf = SimpleDateFormat("MM/dd/yyyy")
    private val currentDate = sdf.format(Date())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val classID = arguments!!.getString("CLASS")// get the class id i.e. info448
        fb = FirebaseDatabase.getInstance().reference.child("classes/${classID as String}")
        val rootView = inflater.inflate(R.layout.fragment_add_discuss, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_my_resource_btn.setOnClickListener {
            addToDatabase()
            dismiss()
        }

    }


    fun addToDatabase() {
        val header = header.text.toString()
        val content = body.text.toString()
        val data = mapOf("header" to header, "content" to content )
        fb.child("discuss").push().setValue(data)
    }


}

