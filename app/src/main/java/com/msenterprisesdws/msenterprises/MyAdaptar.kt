package com.msenterprisesdws.msenterprises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msenterprisesdws.msenterprises.Utils.TimeStamp

class MyAdapter(private val userList : ArrayList<Report>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adminuser,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]


        holder.name.setText(currentitem.name)
        holder.number.setText(currentitem.number)
        holder.message.setText(currentitem.message)
        holder.address.setText(currentitem.location)
        holder.time.setText("(${TimeStamp.getTimeAgo(currentitem.time!!)})")




//        holder.firstName.text = currentitem.firstName
//        holder.lastName.text = currentitem.lastName
//        holder.age.text = currentitem.age

    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.acard_nameview)
        val number : TextView = itemView.findViewById(R.id.acard_numberview)
        val message : TextView = itemView.findViewById(R.id.acard_messageview)
        val address : TextView = itemView.findViewById(R.id.acard_addressview)
        val time : TextView = itemView.findViewById(R.id.acard_timeview)

    }

}