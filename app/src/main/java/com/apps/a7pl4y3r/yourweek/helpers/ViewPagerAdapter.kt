package com.apps.a7pl4y3r.yourweek.helpers


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import java.util.ArrayList


class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    private val fragmentList = ArrayList<Fragment>()


    override fun getItem(position: Int): Fragment = fragmentList[position]
    override fun getCount(): Int = fragmentList.size


    fun addFrag(fragment: Fragment) {
        fragmentList.add(fragment)
    }

}
