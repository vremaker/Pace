package edu.washington.info448.pace


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.response.*
import java.text.SimpleDateFormat
import java.util.*


class AnswerQuestion : DialogFragment() {
    interface DialogListener {
        fun onSelectDoneDialog(inputText: String, inputTag: String)
        fun onCancelDialog(cancelDialog: Unit)
    }
    var itemKey: String? = ""

    companion object {

        private const val ITEMKEY = "key"

        fun newInstance(key: String, classId: String): AnswerQuestion {
            return AnswerQuestion().apply {
                arguments = Bundle().apply {
                    putString(ITEMKEY, key)
                    putString("CLASS", classId)
                }
            }
        }
    }

    var onResult: ((key: String) -> Unit)? = null


    private lateinit var fb : DatabaseReference
    private val sdf = SimpleDateFormat("MM/dd/yyyy")
    private val currentDate = sdf.format(Date())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val key = arguments?.getString(ITEMKEY)
itemKey = key



        val classID = arguments!!.getString("CLASS")// get the class id i.e. info448
        fb = FirebaseDatabase.getInstance().reference.child("classes/${classID as String}/questions/${key}")
        val rootView = inflater.inflate(R.layout.response, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit_response.setOnClickListener {
            addToDatabase()
            dismiss()
        }
    }


    fun addToDatabase() {
        val answer = answer_desc.text.toString()

        fb.child("response").setValue(answer)

    }
}
