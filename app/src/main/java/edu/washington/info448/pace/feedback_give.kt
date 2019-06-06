package edu.washington.info448.pace

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class feedback_give : Fragment() {

    private var callback: submitListener? = null
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
            val button = rootView.findViewById<Button>(R.id.submit)
            button.setOnClickListener() {
                //if exists show, otherwise toast that there is missing
                callback!!.returnHome()
            }
        }
        return rootView
    }
}