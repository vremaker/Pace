package edu.washington.info448.pace

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*


class RecyclerAdapter(var listOfNames: List<String>):  RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var onPersonClickedListener: ((position: Int, name: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewHolderType: Int): ViewHolder {
        // Creates ViewHolder to hold reference of the views
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // Size of items to load
        return listOfNames.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Sets data on view
        viewHolder.bindView(listOfNames[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(personName: String, position: Int) {
            itemView.tvName.text = personName

            itemView.setOnClickListener {
                onPersonClickedListener?.invoke(position, personName)
            }
        }
    }

    fun updateList(newListOfNames: List<String>) {
        this.listOfNames = newListOfNames

        // Call this when you change the data of Recycler View to refresh the items
        notifyDataSetChanged()
    }
}