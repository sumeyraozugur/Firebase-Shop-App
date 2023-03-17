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
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentUpdateProfileBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileFragment : Fragment(R.layout.fragment_update_profile) {

    private val binding by viewBinding(FragmentUpdateProfileBinding::bind)
    private val viewModel: UpdateProfileViewModel by viewModels()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedPicture: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        registerLauncher()

        with(binding) {
            profileToolbar.apply {
                tvProductTitle.text = getString(R.string.update)
                ibArrowBack.setOnClickListener {
                    Navigation.back(it)
                }
            }

            ivUpdate.setOnClickListener {
                pickImageFromGallery()
            }

            btnUpdate.setOnClickListener {
                val firstName = etFirstName.trimmedText()
                val lastName = etLastName.trimmedText()
                val email = etEmail.trimmedText()

                if (validateProfile())
                    selectedPicture?.let { url ->
                        viewModel.updateProfile(firstName, lastName, email, url)
                    }
            }
        }
    }

    private fun validateProfile(): Boolean {
        with(binding) {
            val firstName = etFirstName.trimmedText()
            val lastName = etLastName.trimmedText()
            val email = etEmail.trimmedText()

            return when {
                firstName.isEmpty() -> showError(getString(R.string.required_name))
                lastName.isEmpty() -> showError(getString(R.string.required_lastname))
                email.isEmpty() -> showError(getString(R.string.required_email))
                selectedPicture == null -> showError(getString(R.string.required_picture))
                else -> true
            }
        }
    }

    private fun showError(errorMsg: String): Boolean {
        requireView().showErrorSnackBar(errorMsg, true)
        return false
    }

    //permision
    private fun pickImageFromGallery() {
        this.pickImageFromGalleryWithPermission(activityResultLauncher, permissionLauncher)
    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    selectedPicture = result.data?.data
                    selectedPicture?.let { binding.ivUpdate.setImageURI(it) }
                } else {
                    requireContext().showToast(getString(R.string.image_not_selected))
                }
            }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    activityResultLauncher.launch(
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                    )
                } else {
                    requireContext().showToast(getString(R.string.permission_needed))
                }
            }
    }


    private fun initObservers() {
        with(binding) {
            viewModel.profileInfo.observe(viewLifecycleOwner) {
                etFirstName.setText(it.firstName)
                etLastName.setText(it.lastName)
                etEmail.setText(it.email)
            }

            viewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
                if (isSuccess) {
                    requireView().showErrorSnackBar(getString(R.string.success), false)
                    Navigation.sent(
                        requireView(),
                        R.id.action_updateProfileFragment_to_homeFragment
                    )
                } else requireView().showErrorSnackBar(getString(R.string.success), true)
            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    setViewsGone(
                        ivUpdate,
                        ivUpdateBorder,
                        tilEmail,
                        tilFirstName,
                        tilLastName,
                        btnUpdate
                    )
                    loadingLottie.visible()

                } else {
                    setViewsVisible(
                        ivUpdate,
                        ivUpdateBorder,
                        tilEmail,
                        tilFirstName,
                        tilLastName,
                        btnUpdate
                    )
                    loadingLottie.gone()
                }
            }
        }
    }
}