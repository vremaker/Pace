package edu.washington.info448.pace

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_resources.*
import android.content.Intent
import android.net.Uri


class feedBackViewActivity : AppCompatActivity(){
    interface RecyclerViewClickListener{
        fun onClick(view: View, position: Int)
    }

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_view)
        val prof = intent.getStringExtra("prof")
        ref = FirebaseDatabase.getInstance().reference.child("prof").child(prof)


        myRV.layoutManager = LinearLayoutManager(this)

        loadData()

    }



    fun loadData(){
        var onClickedListener: ((position: Int, name: String) -> Unit)? = null

        val option = FirebaseRecyclerOptions.Builder<ModelFeed>()
            .setQuery(ref, ModelFeed::class.java)
            .build()

        val myfirebaseRecyclerViewAdapter = object: FirebaseRecyclerAdapter<ModelFeed, ItemViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

                val itemView = LayoutInflater.from(this@feedBackViewActivity).inflate(R.layout.main_resource_bucket,parent,false)
                return ItemViewHolder(itemView)
            }



            override fun onBindViewHolder(item: ItemViewHolder, position: Int, model: ModelFeed) {
                //get Firebase position
                val itemId = getRef(position).key.toString()

                item.bindView(position)

                ref.child(itemId).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        item.title.setText("Course: ${model.course}")
                        item.link.setText(model.feed)


                    }
                })

            }
        }

        myRV.adapter = myfirebaseRecyclerViewAdapter
        myfirebaseRecyclerViewAdapter.startListening()


    }

    inner class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var title: TextView = itemView!!.findViewById(R.id.course)
        internal var link: TextView = itemView!!.findViewById(R.id.linkDis)

        var onClickedListener: ((position: Int, link: String) -> Unit)? = null

        fun bindView(position: Int){
            itemView.setOnClickListener{
                onClickedListener?.invoke(position, link.text.toString())
            }
        }
    }
}
