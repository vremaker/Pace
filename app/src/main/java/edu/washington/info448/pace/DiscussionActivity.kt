package edu.washington.info448.pace

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
import kotlinx.android.synthetic.main.activity_resources.*
import android.content.Intent
import android.net.Uri
import android.util.Log


class DiscussionActivity : AppCompatActivity(){


    lateinit var ref: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)
        val classId = intent.getStringExtra("CLASS")
        ref = FirebaseDatabase.getInstance().reference.child("classes/${classId}/discuss")
        myRV.layoutManager = LinearLayoutManager(this)

        loadData(classId)

        fab_add.setOnClickListener {
            var priorInstance = supportFragmentManager.findFragmentByTag("dialog")
            val addPost = AddPost.newInstance(classId)
            val ft = supportFragmentManager.beginTransaction()
            if(priorInstance != null){
                ft.remove(priorInstance)
            }
            ft.addToBackStack(null)
            addPost.show(ft, "dialog")

        }
    }



    fun loadData(classId: String){
        var onClickedListener: ((position: Int, name: String) -> Unit)? = null

        val option = FirebaseRecyclerOptions.Builder<ModelDisc>()
            .setQuery(ref, ModelDisc::class.java)
            .build()

        val myfirebaseRecyclerViewAdapter = object: FirebaseRecyclerAdapter<ModelDisc, ItemViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

                val itemView = LayoutInflater.from(this@DiscussionActivity).inflate(R.layout.main_resource_bucket,parent,false)
                return ItemViewHolder(itemView)
            }



            override fun onBindViewHolder(item: ItemViewHolder, position: Int, model: ModelDisc) {
                //get Firebase position
                val itemId = getRef(position).key.toString()

                item.bindView(position)

                ref.child(itemId).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        item.title.setText(model.header)
                        item.link.setText(model.content)
                    }
                })

                item.itemView.setOnClickListener(View.OnClickListener {
                    val intent = Intent(this@DiscussionActivity, responseActivity::class.java)
                    intent.putExtra("quest", itemId)
                    intent.putExtra("CLASS", classId)
                    startActivity(intent)
                //Toast.makeText(this@DiscussionActivity, itemId.toString(), Toast.LENGTH_SHORT).show()

                })


            }
        }
        myRV.adapter = myfirebaseRecyclerViewAdapter
        myfirebaseRecyclerViewAdapter.startListening()
    }

    inner class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var title: TextView = itemView!!.findViewById(R.id.course)
        var link: TextView = itemView!!.findViewById(R.id.linkDis)

        var onClickedListener: ((position: Int, link: String) -> Unit)? = null

        fun bindView(position: Int){
            itemView.setOnClickListener{
                onClickedListener?.invoke(position, link.text.toString())

            }
        }
    }
}
