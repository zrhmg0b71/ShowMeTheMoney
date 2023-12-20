package com.example.showmethemoneyproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity)
{
    val fragments: List<Fragment>
    init {
        fragments= listOf(ImageFragment1(),ImageFragment2(),ImageFragment3())
    }

    override fun getItemCount(): Int =fragments.size

    override fun createFragment(position: Int): Fragment =fragments[position]
}