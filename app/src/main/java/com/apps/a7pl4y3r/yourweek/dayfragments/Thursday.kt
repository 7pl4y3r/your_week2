package com.apps.a7pl4y3r.yourweek.dayfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Daydb


class Thursday : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_day, container, false)
        val name = getString(R.string.thursday)

        setDayFragmentRecyclerView(view.findViewById(R.id.dayRecyclerView), context!!, Daydb(context!!, name).getData())
        view.findViewById<TextView>(R.id.dayName).text = name

        return view
    }

}