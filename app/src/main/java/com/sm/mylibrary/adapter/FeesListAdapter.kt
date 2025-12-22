package com.sm.mylibrary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sm.mylibrary.R
import com.sm.mylibrary.model.refund.FeesDetails
import com.sm.mylibrary.utils.Utils

class FeesListAdapter(private val items: ArrayList<FeesDetails>) : RecyclerView.Adapter<FeesListAdapter.RefundViewHolder>() {

    inner class RefundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // val tvMsgType: TextView = itemView.findViewById(R.id.tv_message_type)
        val tvdate: TextView = itemView.findViewById(R.id.date)
       // val tvrefund: TextView = itemView.findViewById(R.id.refund)
        val tvamountpaid: TextView = itemView.findViewById(R.id.fees_amount_paid)
        val tvmode: TextView = itemView.findViewById(R.id.mode)
        val tvremarks: TextView = itemView.findViewById(R.id.remarks)
        val monthName  : TextView = itemView.findViewById(R.id.monthname)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fees_item_list, parent, false)
        return RefundViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: RefundViewHolder, position: Int) {
        val item = items[position]
     //   holder.tvMsgType.text = item.msg_type
       // holder.tvMsg.text = item.msg
       // holder.tvAddedOn.text = item.added_on
       // holder.tvStatus.text = item.status
        if (item.from_date != null && item.from_date.isNotEmpty())
          holder.tvdate.text = Utils.changeDateFormat(item.from_date)
      //  holder.tvrefund.text = item.status
        holder.tvamountpaid.text = item.online.toString()
        if (item.cash ==0)
          holder.tvmode.text = "Online"
        else
            holder.tvmode.text = "Cash"

        holder.tvremarks.text = item.fees_type

        //if (position==0) {
               if (item.month_abbr != null && item.month_abbr.isNotEmpty())
               // holder.monthName.text = Utils.getMonthNamefromaDate(item.approve_date)
               holder.monthName.text = item.month_abbr
      //  }else
           // holder.monthName.visibility = View.GONE
    }

    override fun getItemCount(): Int = items.size

}