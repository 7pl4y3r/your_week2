package com.apps.a7pl4y3r.yourweek.dayfragments

import android.content.Context
import android.database.Cursor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.a7pl4y3r.yourweek.helpers.RecyclerViewAdapter
import com.example.alin.yourweek.helpers.ItemOfRV


fun setDayFragmentRecyclerView(recyclerView: RecyclerView, context: Context, res: Cursor) {

    val itemList = ArrayList<ItemOfRV>()
    res.moveToFirst()

    if (res.count == 0) {
        itemList.add(ItemOfRV("No tasks for today", -1))

    } else {

        do {

            itemList.add(
                ItemOfRV(
                    "${res.getString(5)}\n${res.getString(1)}:" +
                            "${res.getString(2)} - ${res.getString(3)}:${res.getString(4)}", 0
                )
            )

        } while (res.moveToNext())

    }

    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = RecyclerViewAdapter(context, itemList)
}