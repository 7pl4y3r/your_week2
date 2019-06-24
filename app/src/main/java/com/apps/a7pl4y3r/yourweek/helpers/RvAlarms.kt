package com.apps.a7pl4y3r.yourweek.helpers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.independent.getMonthNameById
import kotlinx.android.synthetic.main.card_alarm.view.*

class RvAlarms(context: Context, private val items: ArrayList<Alarm>) : RecyclerView.Adapter<RvAlarms.AlarmViewHolder>() {


    private var mListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): AlarmViewHolder = AlarmViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.card_alarm, parent, false), mListener)

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(viewHolder: AlarmViewHolder, position: Int) {
        viewHolder.tvContent.text = "${items[position].name}\n\nDate: ${items[position].day} ${getMonthNameById(items[position].month.toInt())} ${items[position].year}\n" +
                "Time: ${items[position].hour} ${items[position].minute}"
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    class AlarmViewHolder(view: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(view) {
        val tvContent: TextView = view.findViewById(R.id.tv_alarm_content)
    }

}