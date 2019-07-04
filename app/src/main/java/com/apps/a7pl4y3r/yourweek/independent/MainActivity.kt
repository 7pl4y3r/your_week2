package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.dayfragments.Monday
import com.apps.a7pl4y3r.yourweek.dayfragments.Tuesday
import com.apps.a7pl4y3r.yourweek.dayfragments.Wednesday
import com.apps.a7pl4y3r.yourweek.dayfragments.Thursday
import com.apps.a7pl4y3r.yourweek.dayfragments.Friday
import com.apps.a7pl4y3r.yourweek.dayfragments.Saturday
import com.apps.a7pl4y3r.yourweek.dayfragments.Sunday
import com.apps.a7pl4y3r.yourweek.helpers.ViewPagerAdapter

import kotlinx.android.synthetic.main.activity_main.*

import java.util.Calendar


class MainActivity : AppCompatActivity() {


    private var dayID = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        setViewPagerAdapter()

        mainFab.setOnClickListener {
            dayID = viewPager.currentItem
            startActivity(Intent(this, Add::class.java).putExtra(dayExtra, dayID))
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.nav_settings -> startActivity(Intent(this, Settings::class.java))

            R.id.nav_title -> {
                dayID = viewPager.currentItem
                startActivity(Intent(this, UpdateTask::class.java).putExtra("DAYID", dayID))
            }

            R.id.nav_alarms -> startActivity(Intent(this, Alarms::class.java))

        }

        return true
    }


    override fun onResume() {

        var pref = getSharedPreferences(SettChangedTheme, Context.MODE_PRIVATE)
        if (pref.getBoolean(valueSettChangedTheme, false)) {

            pref.edit().putBoolean(valueSettChangedTheme, false).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return

        }

        pref = getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE)
        if (pref.getBoolean(valueSettTaskWasAdded, false) && dayID != -1) {

            setViewPagerAdapter()
            viewPager.currentItem = dayID
            pref.edit().putBoolean(valueSettTaskWasAdded, false).apply()

        }

        super.onResume()
    }


    //Sets the ViewPager Adapter (The fragments for each day are being added here)
    private fun setViewPagerAdapter() {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(Monday())
        adapter.addFrag(Tuesday())
        adapter.addFrag(Wednesday())
        adapter.addFrag(Thursday())
        adapter.addFrag(Friday())

		//true if the week is 7 days long | false if the week is 5 days long
        if (getSharedPreferences(settNumOfDays, Context.MODE_PRIVATE)
                .getBoolean(valueSettNumOfDays, false)) {

            adapter.addFrag(Saturday())
            adapter.addFrag(Sunday())

            viewPager.adapter = adapter
            viewPager.currentItem = getNORMALDayId()
            viewPager.offscreenPageLimit = 7

        } else {

            val currentDay = getNORMALDayId()
            viewPager.adapter = adapter
            viewPager.currentItem = if (currentDay < 5) currentDay else 0
            viewPager.offscreenPageLimit = 5
        }

    }


    private fun getNORMALDayId(): Int = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {

            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6

            else -> -1

        }

}
