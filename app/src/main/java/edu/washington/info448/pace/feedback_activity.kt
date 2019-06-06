package edu.washington.info448.pace

import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_resources.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import android.os.PersistableBundle


/**
 * WHEN BACK BUTTON PRESSED ON MAIN DECIDER MAKE SURE IT GOES BACK TO MAIN MENU
 */
class feedback_activity : AppCompatActivity(), feedback_decide.giveFeedListener, feedback_decide.getFeedListener,
                            feedback_decide.goHomeListener, feedback_give.submitListener,
                            feedback_search.searchListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feedbackFrag = feedback_decide.newInstance()
        supportFragmentManager.beginTransaction().run {
            add(R.id.fragHere, feedbackFrag, "takes you to fragment where you make your decisions")
            addToBackStack(null)
            commit()
        }
        setContentView(R.layout.feedback_activity)
    }

    override fun viewFeedback() {
        val showFeed = feedback_view.newInstance()
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragHere, showFeed, "show professor feedback result ")
            addToBackStack(null)
            commit()
        }
    }

    override fun getFeed() {
        val getFeed = feedback_search.newInstance()
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragHere, getFeed, "get to the search bar for feedback")
            addToBackStack(null)
            commit()
        }
    }


    override fun giveFeed() {
        val giveFrag = feedback_give.newInstance()
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragHere, giveFrag, "lets you give feedback to prof. ")
            addToBackStack(null)
            commit()
        }
    }

    override fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun returnHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
