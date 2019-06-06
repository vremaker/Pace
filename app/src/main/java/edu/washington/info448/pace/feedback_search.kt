package edu.washington.info448.pace

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [feedback_search.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [feedback_search.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class feedback_search : Fragment() {

    private var callback: searchListener? = null
    interface searchListener {
        fun viewFeedback()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? searchListener
        if (callback == null) {
            throw ClassCastException("$context must implement searchListener")
        }
    }

    companion object {
        fun newInstance(): feedback_search {
            val args = Bundle().apply {
            }
            val fragment = feedback_search().apply {
                setArguments(args)
            }
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_feedback_search, container, false)
        arguments?.let {
            val button = rootView.findViewById<Button>(R.id.searchRes)
            button.setOnClickListener() {

                //if exists show, otherwise toast that there is missing
                callback!!.viewFeedback()
            }
        }
        return rootView
    }
}
