package edu.washington.info448.pace

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast



class feedback_decide : Fragment() {
    private var callback: giveFeedListener? = null
    private var callbackHome: goHomeListener? = null
    private var callbackGet: getFeedListener? = null
    interface getFeedListener {
        fun getFeed()
    }
    interface goHomeListener {
        fun goHome()
    }
    interface giveFeedListener {
        fun giveFeed()
        //fun toQuestion(topic: String, index:Int)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? giveFeedListener
        if (callback == null) {
             throw ClassCastException("$context must implement giveFeedListener")
        }
        callbackHome = context as? goHomeListener
        if (callbackHome == null) {
            throw ClassCastException("$context must implement goHomeListener")
        }
        callbackGet = context as? getFeedListener
        if (callbackGet == null) {
            throw ClassCastException("$context must implement getFeedListener")
        }

    }

    companion object {
        fun newInstance(): feedback_decide {
            val args = Bundle().apply {
            }
            val fragment = feedback_decide().apply {
                setArguments(args)
            }
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_feedback_decide, container, false)
        arguments?.let {
                val home = rootView.findViewById<Button>(R.id.goHomeYo)
                home.setOnClickListener(){
                   callbackHome!!.goHome()

                }
                val give = rootView.findViewById<Button>(R.id.viewBtn)
                give.setOnClickListener(){
                    callbackGet!!.getFeed()

                }
                val get = rootView.findViewById<Button>(R.id.giveBtn)
                get.setOnClickListener() {
                    callback!!.giveFeed()
                }
        }
        return rootView
    }
}
