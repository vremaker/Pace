package edu.washington.info448.pace

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_class.*

class ClassActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)

        val action = intent.getStringExtra("ACTION")

        database = FirebaseDatabase.getInstance().reference.child("classes")
        recycler.layoutManager = LinearLayoutManager(this)
        getClasses(action)

    }

    fun getClasses(action: String) {
        val options = FirebaseRecyclerOptions.Builder<ClassItem>().setQuery(database, ClassItem::class.java).build()
        val adapter = object : FirebaseRecyclerAdapter<ClassItem, ClassViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
                val itemView =
                    LayoutInflater.from(this@ClassActivity).inflate(R.layout.item, parent, false)
                return ClassViewHolder(itemView)
            }


            override fun onBindViewHolder(item: ClassViewHolder, position: Int, model: ClassItem) {
                //get Firebase position
                val itemId = getRef(position).key.toString()

                item.bindView(position)

                database.child(itemId).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        item.tvName.setText(model.name)
//                        item.link.setText("")
                    }
                })

                // handle the put extra action to determine next intent
                item.itemView.setOnClickListener(View.OnClickListener {
                    //                    Toast.makeText(this@ClassActivity, itemId, Toast.LENGTH_SHORT).show()
                    val intent: Intent
                    when (action) {
                        "Resources" -> {
                            intent = Intent(this@ClassActivity, ResourcesActivity::class.java)
                            intent.putExtra("CLASS", itemId)
                            startActivity(intent)
                        }
                        "Ask A Question" -> {
                            intent = Intent(this@ClassActivity, AskQuestionActivity::class.java)
                            intent.putExtra("CLASS", itemId)
                            startActivity(intent)
                        }
                        "Class Discussion Board" -> {
                            intent = Intent(this@ClassActivity, DiscussionActivity::class.java)
                            intent.putExtra("CLASS", itemId)
                            startActivity(intent)
                        }
                    }
                })

            }

        }
        recycler.adapter = adapter
        adapter.startListening()
    }

    inner class ClassViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        internal var tvName: TextView = itemView!!.findViewById(R.id.tvName)
        var onClickedListener: ((position: Int, link: String) -> Unit)? = null

        fun bindView(position: Int) {
            itemView.setOnClickListener {
                onClickedListener?.invoke(position, tvName.text.toString())
            }
        }
    }
}
