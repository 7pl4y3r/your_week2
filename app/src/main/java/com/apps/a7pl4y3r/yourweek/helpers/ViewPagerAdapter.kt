package com.apps.a7pl4y3r.yourweek.helpers

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList


class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {


    private val fragmentList = ArrayList<Fragment>()


    override fun getItem(position: Int): Fragment = fragmentList[position]
    override fun getCount(): Int = fragmentList.size


    fun addFrag(fragment: Fragment) {
        fragmentList.add(fragment)
    }

}
