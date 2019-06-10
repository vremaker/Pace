package edu.washington.info448.pace


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_question.*
import kotlinx.android.synthetic.main.fragment_add_resource.*
import java.text.SimpleDateFormat
import java.util.*


class AddQuestion : DialogFragment() {
    interface DialogListener {
        fun onSelectDoneDialog(inputText: String, inputTag: String)
        fun onCancelDialog(cancelDialog: Unit)
    }

    companion object {
        fun newInstance(classID: String): AddQuestion {
            return AddQuestion().apply {
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
        val classID = arguments!!.getString("CLASS")// get the class id i.e. info448
        fb = FirebaseDatabase.getInstance().reference.child("classes/${classID as String}")
        val rootView = inflater.inflate(R.layout.add_question, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit.isEnabled = false
        validationCheck()


        submit.setOnClickListener {
            addToDatabase()
            dismiss()
        }
    }

    fun validationCheck() {
        question_desc.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val userInput = question_desc.text.toString()
                if (userInput.isNotEmpty()) {
                    submit.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })


    }


    fun addToDatabase() {
        val question = question_desc.text.toString()
        val response = "No Answer Yet."
        val data = mapOf("question" to question, "response" to response)
        fb.child("questions").push().setValue(data)
    }
}
