package edu.washington.info448.pace

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.add_name.*
import kotlinx.android.synthetic.main.fragment_add_resource.*
import java.text.SimpleDateFormat
import java.util.*


class fragmentAddName : DialogFragment() {
    interface DialogListener {
        fun onSelectDoneDialog(inputText: String, inputTag: String)
        fun onCancelDialog(cancelDialog: Unit)
    }

    companion object {
        fun newInstance(): fragmentAddName {
            return fragmentAddName()
        }
    }

    lateinit var ref: DatabaseReference
    private val fb = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        super.onCreate(savedInstanceState)
        val rootView = inflater.inflate(R.layout.add_name, container, false)
        var position = arguments!!.getInt("position")
        position += 1
        Log.i("123", position.toString())
        val add : Button = rootView.findViewById(R.id.add_my_name_btn)
        val name : TextView = rootView.findViewById(R.id.name)
        add.text = add.text.toString() + position.toString()
        var group = "group" + position.toString();
        ref = FirebaseDatabase.getInstance().reference.child("group")

        add.isEnabled = false
        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                add.setEnabled(getText(name).length > 0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        var count = 0
        ref.child(group).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                count = p0.childrenCount.toInt()
                Log.i("222", count.toString())

                add.setOnClickListener {
                    val name = name.text.toString()
                    count += 1
                    if (count <= 5) {
                        var number = "person" + count.toString()
                        val data = mapOf(number to name)
                        fb.child("group").child(group).updateChildren(data)
                    } else {
                        Toast.makeText(context, "The Group is Full, Try another one ", Toast.LENGTH_SHORT).show()
                    }
                    val g = Intent(context, group_activity::class.java)
                    startActivity(g)
                }
            }
        })
        Log.i("333", count.toString())


        return rootView
    }

    private fun getText(textView: TextView): String {
        return textView.text.toString().trim { it <= ' ' }
    }

}
