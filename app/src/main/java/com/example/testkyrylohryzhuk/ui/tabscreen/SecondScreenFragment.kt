package com.example.testkyrylohryzhuk.ui.tabscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.testkyrylohryzhuk.R
import com.example.testkyrylohryzhuk.utils.adapter.MyPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_second_screen.*


class SecondScreenFragment : Fragment() {

    private lateinit var myAdapter: MyPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabs(view)
        initListeners()
    }

    private fun initListeners() {
        resultButton.setOnClickListener {
            actionToResult()
        }
    }

    private fun actionToResult() {
        val action = SecondScreenFragmentDirections.navigateToResultScreen()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setTabs(view: View) {
        myAdapter = MyPagerAdapter(this@SecondScreenFragment)
        viewPager.adapter = myAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
//                getString(R.string.from_location)
                0 -> tab.text = getString(R.string.from_place)
                1 -> tab.text = getString(R.string.to_place)
            }
        }.attach()
    }


}