package com.sum.shop.ui.loginregister.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sum.shop.databinding.TermConditionSheetBinding

class TermConditionBottomSheet : BottomSheetDialogFragment() {

    private var _binding: TermConditionSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : SignUpTermConditionViewModel

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

        viewModel = ViewModelProvider(requireActivity())[SignUpTermConditionViewModel::class.java]

        binding.btnCancel.setOnClickListener {
           dismiss()
        }

        binding.btnTermAccept.setOnClickListener {
            viewModel.checkResult()
            dismiss()
        }
    }
}