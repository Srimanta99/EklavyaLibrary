package com.sm.mylibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sm.mylibrary.R
import com.sm.mylibrary.model.refund.RefundDetail
import com.sm.mylibrary.utils.Utils

class RefundListAdapter(private val items: ArrayList<RefundDetail>) : RecyclerView.Adapter<RefundListAdapter.RefundViewHolder>() {

    inner class RefundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // val tvMsgType: TextView = itemView.findViewById(R.id.tv_message_type)
        val tvdate: TextView = itemView.findViewById(R.id.date)
        val tvrefund: TextView = itemView.findViewById(R.id.refund)
        val tvamountpaid: TextView = itemView.findViewById(R.id.fees_amount_paid)
        val tvmode: TextView = itemView.findViewById(R.id.mode)
        val tvremarks: TextView = itemView.findViewById(R.id.remarks)
     //   val monthName  : TextView = itemView.findViewById(R.id.monthname)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.refund_item_list, parent, false)
        return RefundViewHolder(view)
    }


    override fun onBindViewHolder(holder: RefundViewHolder, position: Int) {
        val item = items[position]
     //   holder.tvMsgType.text = item.msg_type
       // holder.tvMsg.text = item.msg
       // holder.tvAddedOn.text = item.added_on
       // holder.tvStatus.text = item.status
        if (item.added_on != null && item.added_on.isNotEmpty())
         holder.tvdate.text = Utils.changeDateFormatwithtime(item.added_on)

        holder.tvrefund.text = item.status
        holder.tvamountpaid.text = item.amount
        holder.tvmode.text = item.paid_by
        holder.tvremarks.text = item.detail
        //holder.monthName.text = Utils.getMonthNamefromaDate(item.added_on)

    }

    override fun getItemCount(): Int = items.size

}