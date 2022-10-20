package com.sum.shop.ui.updateprofile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sum.shop.R
import com.sum.shop.databinding.FragmentUpdateProfileBinding
import com.sum.shop.delegate.viewBinding


class UpdateProfileFragment : Fragment(R.layout.fragment_update_profile) {
    private val binding by viewBinding(FragmentUpdateProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}