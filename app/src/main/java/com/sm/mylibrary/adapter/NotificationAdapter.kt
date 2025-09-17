package com.sm.mylibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sm.mylibrary.R
import com.sm.mylibrary.model.notififation.NotificationDetails

class NotificationAdapter(private val items: List<NotificationDetails>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // val tvMsgType: TextView = itemView.findViewById(R.id.tv_message_type)
        val tvMsg: TextView = itemView.findViewById(R.id.tv_message)
        val tvAddedOn: TextView = itemView.findViewById(R.id.tvAddedOn)
       // val tvStatus: TextView = itemView.findViewById(R.id.tv_Status)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_list_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = items[position]
     //   holder.tvMsgType.text = item.msg_type
        holder.tvMsg.text = item.msg
        holder.tvAddedOn.text = item.added_on
       // holder.tvStatus.text = item.status
    }

    override fun getItemCount(): Int = items.size

}