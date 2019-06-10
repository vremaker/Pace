package edu.washington.info448.pace

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val categories = listOf(
            "Ask A Question", "Find A Group", "Resources",
            "Professor Feedback", "Class Discussion Board"
        )

        val adapter = RecyclerAdapter(categories)
        myRecyclerView.adapter = adapter
        myRecyclerView.setHasFixedSize(true)

        adapter.onPersonClickedListener = { position, name ->
            Toast.makeText(this, "$name clicked!", Toast.LENGTH_SHORT).show()
            val i = Intent(this, ResourcesActivity::class.java)
            val f = Intent(this, feedback_activity::class.java)
            val j = Intent(this, ClassActivity::class.java)
            val g = Intent(this, group_activity::class.java)
            j.putExtra("ACTION", name)
            when (name) {
//                "Resources" -> startActivity(i)
                "Professor Feedback" -> startActivity(f)
                "Resources" -> startActivity(j)
                "Find A Group" ->  startActivity(g)
                "Ask A Question" -> startActivity(j)
            }
        }
    }
}

