package com.sm.mylibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sm.mylibrary.R
import com.sm.mylibrary.model.manageleave.LeaveData

class ManageLeaveAdapter(private val items: List<LeaveData>) : RecyclerView.Adapter<ManageLeaveAdapter.ManageLeaveViewHolder>() {

    inner class ManageLeaveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // val tvMsgType: TextView = itemView.findViewById(R.id.tv_message_type)
        val leavedays: TextView = itemView.findViewById(R.id.tv_leave_days)
        val tvstartdate: TextView = itemView.findViewById(R.id.tv_startdate)
        val tvenddate: TextView = itemView.findViewById(R.id.tvenddate)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_Status)
        val tvapprovedate: TextView = itemView.findViewById(R.id.tv_Approvedate)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageLeaveViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.leave_list_item, parent, false)
        return ManageLeaveViewHolder(view)
    }

    override fun onBindViewHolder(holder: ManageLeaveViewHolder, position: Int) {
        val item = items[position]
     //   holder.tvMsgType.text = item.msg_type
        holder.leavedays.text = item.days.toString()
        holder.tvstartdate.text = item.fromdate
        holder.tvenddate.text = item.todate
        holder.tvStatus.text = item.status
        holder.tvapprovedate.text = item.appoveddate
       // holder.tvStatus.text = item.status
    }

    override fun getItemCount(): Int = items.size

}