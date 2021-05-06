package com.example.testkyrylohryzhuk.ui.splachscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testkyrylohryzhuk.R
import kotlinx.coroutines.*

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GlobalScope.launch {
            delay(TIME_OUT)
            withContext(Dispatchers.Main) {
                moveToNext()
            }
        }
    }

    private fun moveToNext() {
        with(findNavController()) {
            popBackStack(R.id.splashScreenFragment, true)
            navigate(R.id.secondScreenFragment)
        }
    }

    companion object {
        private const val TIME_OUT: Long = 2000
    }
}