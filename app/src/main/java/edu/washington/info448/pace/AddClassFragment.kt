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
import kotlinx.android.synthetic.main.fragment_add_resource.*
import java.text.SimpleDateFormat
import java.util.*


class AddClassFragment : DialogFragment() {

    interface DialogListener {
        fun onSelectDoneDialog(inputText: String, inputTag: String)
        fun onCancelDialog(cancelDialog: Unit)
    }

    companion object{
        fun newInstance(): AddClassFragment {
            return AddClassFragment()
        }
    }

    private lateinit var fb : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val classID = arguments!!.getString("CLASS")// get the class id i.e. info448
        fb = FirebaseDatabase.getInstance().reference.child("classes/")
        val rootView = inflater.inflate(R.layout.fragment_add_class, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_my_resource_btn.setOnClickListener {
            addToDatabase()
            dismiss()
        }

    }


    fun validationCheck() {
        resource_desc.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val userInput = resource_desc.text.toString()
                if (userInput.isNotEmpty()) {
                    resource_link.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        resource_link.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

                val userInput = resource_link.text.toString()

                if (userInput.isNotEmpty()) {
                    val check = URLUtil.isValidUrl(userInput)
                    if (check) {
                        resource_error.setText("")
                        add_my_resource_btn.isEnabled = true
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    fun addToDatabase() {
        val desc = resource_desc.text.toString()
        val link = resource_link.text.toString()
        val data = mapOf("name" to link)
        fb.child(desc).setValue(data)
    }


}
