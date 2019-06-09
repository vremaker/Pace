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
import android.support.v4.app.Fragment
import edu.washington.info448.pace.groupModel as groupModel


class group_activity : AppCompatActivity(){
    interface RecyclerViewClickListener{
        fun onClick(view: View, position: Int)
    }

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        ref = FirebaseDatabase.getInstance().reference.child("group")
        myRV.layoutManager = LinearLayoutManager(this)

        loadData()
    }



    fun loadData(){
        var onClickedListener: ((position: Int, name: String) -> Unit)? = null

        val option = FirebaseRecyclerOptions.Builder<groupModel>()
            .setQuery(ref, groupModel::class.java)
            .build()

        val myfirebaseRecyclerViewAdapter = object: FirebaseRecyclerAdapter<groupModel, ItemViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

                val itemView = LayoutInflater.from(this@group_activity).inflate(R.layout.main_group_list,parent,false)
                return ItemViewHolder(itemView)
            }



            override fun onBindViewHolder(item: ItemViewHolder, position: Int, model: groupModel) {
                //get Firebase position
                val itemId = getRef(position).key.toString()

                item.bindView(position)

                ref.child(itemId).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        item.p1.setText("Person1: ${model.person1}")
                        item.p2.setText("Person2: ${model.person2}")
                        item.p3.setText("Person3: ${model.person3}")
                        item.p4.setText("Person4: ${model.person4}")
                        item.p5.setText("Person5: ${model.person5}")
                    }
                })

                item.itemView.setOnClickListener(View.OnClickListener {
                    Log.i("asdf", position.toString())
                    var bundle = Bundle()
                    bundle.putInt("position", position)
                    var priorInstance = supportFragmentManager.findFragmentByTag("dialog")
                    val addResource = fragmentAddName.newInstance()
                    val ft = supportFragmentManager.beginTransaction()
                    addResource.arguments = bundle
                    if(priorInstance != null){
                        ft.remove(priorInstance)
                    }
                    ft.addToBackStack(null)
                    addResource.show(ft, "dialog")
                    Log.i("klj", position.toString())
                })
            }
        }

        myRV.adapter = myfirebaseRecyclerViewAdapter
        myfirebaseRecyclerViewAdapter.startListening()
    }

    inner class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var p1: TextView = itemView!!.findViewById(R.id.person1)
        var p2: TextView = itemView!!.findViewById(R.id.person2)
        var p3: TextView = itemView!!.findViewById(R.id.person3)
        var p4: TextView = itemView!!.findViewById(R.id.person4)
        var p5: TextView = itemView!!.findViewById(R.id.person5)

        var onClickedListener: ((position: Int) -> Unit)? = null

        fun bindView(position: Int){
            itemView.setOnClickListener{
                onClickedListener?.invoke(position)
            }
        }


    }
}