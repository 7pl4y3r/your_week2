package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apps.a7pl4y3r.yourweek.R
import kotlinx.android.synthetic.main.activity_settings.*


class Settings : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_settings)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val roundBackground = getButtonDrawable(this)
            btSettingsTheme.background = roundBackground
            btSettingsFormats.background = roundBackground

        }


        btSettingsTheme.setOnClickListener {
            startActivity(Intent(this, SettingTheme::class.java))
        }

        btSettingsFormats.setOnClickListener {
            startActivity(Intent(this, SettingsFormats::class.java))
        }

    }

    override fun onResume() {
        super.onResume()

        val pref = getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE)
        val themeWasChanged = pref.getBoolean(valueSettTaskWasAdded, false)

        if (themeWasChanged) {
            pref.edit().putBoolean(valueSettTaskWasAdded, false).apply()
            startActivity(Intent(this, Settings::class.java))
            finish()
        }
    }
}
