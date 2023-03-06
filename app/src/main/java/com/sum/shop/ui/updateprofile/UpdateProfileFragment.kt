package com.sum.shop.ui.updateprofile


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import com.sum.shop.R
import com.sum.shop.databinding.FragmentUpdateProfileBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.profile.ProfileViewModel
import com.sum.shop.utils.back
import com.sum.shop.utils.pickImageFromGalleryWithPermission
import com.sum.shop.utils.showErrorSnackBar
import com.sum.shop.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileFragment : Fragment(R.layout.fragment_update_profile) {

    private val binding by viewBinding(FragmentUpdateProfileBinding::bind)
    private val viewModel: UpdateProfileViewModel by viewModels()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedPicture : Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        registerLauncher()

        with(binding) {
            profileToolbar.tvProductTitle.text= getString(R.string.update)

            profileToolbar.ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }
            ivUpdate.setOnClickListener {
                pickImageFromGallery()
            }

            btnUpdate.setOnClickListener {
                val firstName = etFirstName.text.toString().trim { it <= ' ' }
                val lastName = etLastName.text.toString().trim { it <= ' ' }
                val email = etEmail.text.toString().trim { it <= ' ' }

                if(validateProfile())
                    selectedPicture?.let { url->
                        viewModel.updateProfile(firstName, lastName, email, url)
                    }
            }
        }
    }

    private fun validateProfile(): Boolean {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()


        return when {
            firstName.isEmpty() -> showError(getString(R.string.required_name))
            lastName.isEmpty() -> showError(getString(R.string.required_lastname))
            email.isEmpty() -> showError(getString(R.string.required_email))
            selectedPicture == null  ->showError(getString(R.string.required_picture))
            else -> true
        }
    }

    private fun showError(errorMsg: String): Boolean {
        requireView().showErrorSnackBar(errorMsg, true)
        return false
    }

//permision
    private fun pickImageFromGallery() {
        this.pickImageFromGalleryWithPermission(activityResultLauncher,permissionLauncher)
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedPicture = result.data?.data
                selectedPicture?.let { binding.ivUpdate.setImageURI(it) }
            } else {
                requireContext().showToast("Image not selected")
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                activityResultLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
            } else {
                requireContext().showToast("Permission needed")
            }
        }
    }



    private fun initObservers(){
        with(binding){
            viewModel.profileInfo.observe(viewLifecycleOwner) {
                etFirstName.setText(it.firstName)
                etLastName.setText( it.lastName)
                etEmail.setText(it.email)
            }

            viewModel.isSuccess.observe(viewLifecycleOwner, Observer {isSuccess->
                if (isSuccess)  {
                    requireView().showErrorSnackBar(getString(R.string.success), false)
                    viewModel.navigateToHome(requireView())
                }
                 else  requireView().showErrorSnackBar(getString(R.string.success), true)
            })
            viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->

                ivUpdate.visibility = if (isLoading) View.GONE else View.VISIBLE
                ivUpdateBorder.visibility = if (isLoading) View.GONE else View.VISIBLE
                tilEmail.visibility =if (isLoading) View.GONE else View.VISIBLE
                tilFirstName.visibility= if (isLoading) View.GONE else View.VISIBLE
                tilLastName.visibility =  if (isLoading) View.GONE else View.VISIBLE
                btnUpdate.visibility =  if (isLoading) View.GONE else View.VISIBLE
                loadingLottie.visibility = if(isLoading) View.VISIBLE else View.GONE

            })
        }
    }
}