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


class responseActivity : AppCompatActivity(){
    interface RecyclerViewClickListener{
        fun onClick(view: View, position: Int)
    }

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)
        val classId = intent.getStringExtra("CLASS")
        val quest = intent.getStringExtra("quest")
        ref = FirebaseDatabase.getInstance().reference.child("classes/${classId}/discuss/${quest}")
        myRV.layoutManager = LinearLayoutManager(this)

        loadData()

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



    fun loadData(){
        var onClickedListener: ((position: Int, name: String) -> Unit)? = null

        val option = FirebaseRecyclerOptions.Builder<ModelDisc>()
            .setQuery(ref, ModelDisc::class.java)
            .build()

        val myfirebaseRecyclerViewAdapter = object: FirebaseRecyclerAdapter<ModelDisc, ItemViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

                val itemView = LayoutInflater.from(this@responseActivity).inflate(R.layout.main_resource_bucket,parent,false)
                return ItemViewHolder(itemView)
            }



            override fun onBindViewHolder(item: ItemViewHolder, position: Int, model: ModelDisc) {
                //get Firebase position
                val itemId = getRef(position).key.toString()

                item.bindView(position)

                ref.child(itemId).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        item.title.setText("${model.header}")
                        item.link.setText(model.content)
                    }
                })

                item.itemView.setOnClickListener(View.OnClickListener {

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
