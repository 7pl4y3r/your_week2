package com.apps.a7pl4y3r.yourweek.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Alarm
import com.apps.a7pl4y3r.yourweek.independent.getMonthNameById
import kotlinx.android.synthetic.main.card_alarm.view.*

class RvAlarms(context: Context, private val items: ArrayList<Alarm>) : RecyclerView.Adapter<RvAlarms.AlarmViewHolder>() {


    private var mListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): AlarmViewHolder = AlarmViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.card_alarm, parent, false), mListener)

    override fun getItemCount(): Int = items.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: AlarmViewHolder, position: Int) {
        viewHolder.tvContent.text = "${items[position].name}\n\nDate: ${items[position].day} ${getMonthNameById(items[position].month.toInt())} ${items[position].year}\n" +
                "Time: ${items[position].hour} ${items[position].minute}"
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    class AlarmViewHolder(view: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(view) {

        val tvContent: TextView = view.findViewById(R.id.tv_alarm_content)

        init {

            view.setOnClickListener {

                if (adapterPosition != RecyclerView.NO_POSITION && listener != null)
                    listener.onItemClick(adapterPosition)

            }



        }

    }

}