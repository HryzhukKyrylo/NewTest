package com.example.testkyrylohryzhuk.utils.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testkyrylohryzhuk.ui.tabscreen.TabFragment

class MyPagerAdapter(activity: Fragment) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        val fragment = TabFragment()
        fragment.arguments = Bundle().apply {
            putInt("object", position + 1)
        }
        return fragment
    }

    override fun getItemCount() = 2
}



