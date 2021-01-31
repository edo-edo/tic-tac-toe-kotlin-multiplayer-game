package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.newgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.google.android.material.tabs.TabLayout

class NewCPUGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_c_p_u_game)
        setSupportActionBar(findViewById(R.id.toolbar))
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }
}

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> EasyGameModeFragment()
            else -> HardGameModeFragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        when (position) {
            0 -> "EASY"
            else -> "HARD"
        }
}