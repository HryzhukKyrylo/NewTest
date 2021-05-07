package com.example.testkyrylohryzhuk.ui.tabscreen

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.testkyrylohryzhuk.BaseFragment
import com.example.testkyrylohryzhuk.R
import com.example.testkyrylohryzhuk.utils.adapter.MyPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_second_screen.*

class SecondScreenFragment : BaseFragment() {

    override var fragmentLayout = R.layout.fragment_second_screen

    override fun initUI(savedInstanceState: Bundle?) {
        setTabs()
        initListeners()
    }

    private fun initListeners() {
        resultButton.setOnClickListener {
            actionToResult()
        }
    }

    private fun actionToResult() {
        NavHostFragment.findNavController(this).navigate(R.id.navigateToResultScreen)
    }

    private fun setTabs() = with(viewPager) {
        adapter?.let {} ?: run {
            adapter = MyPagerAdapter(
                requireActivity(),
                listOf(TabFragment(), TabFragment())
            )
            TabLayoutMediator(tabLayout, this) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.from_place)
                    1 -> tab.text = getString(R.string.to_place)
                }
            }.attach()
        }
    }
}