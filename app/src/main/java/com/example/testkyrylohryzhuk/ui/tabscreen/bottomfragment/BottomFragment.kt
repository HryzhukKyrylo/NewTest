package com.example.testkyrylohryzhuk.ui.tabscreen.bottomfragment

import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.testkyrylohryzhuk.R
import com.example.testkyrylohryzhuk.ui.tabscreen.bottomfragment.epoxy.DataController
import com.example.testkyrylohryzhuk.ui.viewmodel.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom.*

class BottomFragment : BottomSheetDialogFragment(), DataController.OnItemClickedListener {

    private val controller = DataController()
    private val viewModel: SharedViewModel by activityViewModels()
    private var tabListener: OnSelectionsListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachToParentFragment(requireParentFragment())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvListGlobal.adapter = controller.adapter
        rvListGlobal.setHasFixedSize(true)
        controller.setListener(this@BottomFragment)
        controller.setData(viewModel.listGlobal.distinct())
    }

    private fun onAttachToParentFragment(fragment: Fragment) {
        tabListener = fragment as? OnSelectionsListener
    }

    override fun onEntryClicked(data: Address) {
        tabListener?.addSelectionsMarker(data)
        dismiss()
    }

}