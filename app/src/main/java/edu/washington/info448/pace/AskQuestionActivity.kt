package edu.washington.info448.pace

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.question_activity.*



class AskQuestionActivity : AppCompatActivity(){
    interface RecyclerViewClickListener{
        fun onClick(view: View, position: Int)
    }

    lateinit var data: DatabaseReference

    val list: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_activity)
        val classId = intent.getStringExtra("CLASS")
        data = FirebaseDatabase.getInstance().reference.child("classes/${classId}/questions")
        questionRV.layoutManager = LinearLayoutManager(this)

        loadQuestion()

        add_question.setOnClickListener {

            var priorInstance = supportFragmentManager.findFragmentByTag("dialog")

            val addQuestion = AddQuestion.newInstance(classId)
            val ft = supportFragmentManager.beginTransaction()
            if(priorInstance != null){
                ft.remove(priorInstance)
            }
            ft.addToBackStack(null)
            addQuestion.show(ft, "dialog")
        }
    }

    fun loadQuestion(){
        Log.i("Debugging","Load question")
        var onClickedListener: ((position: Int, name: String) -> Unit)? = null

        val option = FirebaseRecyclerOptions.Builder<ModelQuestion>()
            .setQuery(data, ModelQuestion::class.java)
            .build()

        val myfirebaseRecyclerViewAdapter = object: FirebaseRecyclerAdapter<ModelQuestion, ItemViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

                val itemView = LayoutInflater.from(this@AskQuestionActivity).inflate(R.layout.question, parent, false)
                return ItemViewHolder(itemView)
            }

            override fun onBindViewHolder(item: ItemViewHolder, position: Int, model: ModelQuestion) {
                val itemId = getRef(position).key.toString()
                list.add(itemId)
                item.bindView(position)

                data.child(itemId).addValueEventListener(object: ValueEventListener {


                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {


                        item.question.setText("Question: ${model.question}")
                        item.response.setText("Answer : ${model.response}")
                    }
                })

            }
        }
        questionRV.adapter = myfirebaseRecyclerViewAdapter
        myfirebaseRecyclerViewAdapter.startListening()
    }

    inner class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var question: TextView = itemView!!.findViewById(R.id.question)
        internal var response: TextView = itemView!!.findViewById(R.id.response)

        var onClickedListener: ((position: Int, link: String) -> Unit)? = null

        fun bindView(position: Int){
            itemView.setOnClickListener{
                val classId = intent.getStringExtra("CLASS")
                data = FirebaseDatabase.getInstance().reference.child("classes/${classId}/questions/${list[position]}")
    val dialog = AnswerQuestion.newInstance(list[position], classId)
                dialog.show(supportFragmentManager, "answerQuestion")

            }
        }

    }
}


