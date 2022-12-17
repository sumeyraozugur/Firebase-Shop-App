package com.sum.shop.ui.termcondition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sum.shop.Constant
import com.sum.shop.R
import com.sum.shop.databinding.TermConditionSheetBinding
import com.sum.shop.utils.sent

class TermConditionBottomSheet : BottomSheetDialogFragment() {

    private var _binding: TermConditionSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { TermConditionBottomSheetViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TermConditionSheetBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
           dismiss()
        }

        binding.termAccept.setOnClickListener {

            setFragmentResult("checkBox",bundleOf("acceptCondition" to true))
            dismiss()

        }
    }
}