package com.example.testkyrylohryzhuk.utils.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

const val TAB_POSITION = "position"

class MyPagerAdapter(
    activity: FragmentActivity,
    private val fragments: List<Fragment>
    ) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment =
        fragments[position].apply {
            arguments = bundleOf(TAB_POSITION to position + 1)
        }

    override fun getItemCount() = fragments.size
}