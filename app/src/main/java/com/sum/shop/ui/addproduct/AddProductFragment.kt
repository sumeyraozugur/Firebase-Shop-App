package com.sum.shop.ui.addproduct

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
import com.sum.shop.databinding.FragmentAddProductBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.extension.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : Fragment(R.layout.fragment_add_product) {

    private val binding by viewBinding(FragmentAddProductBinding::bind)
    private val viewModel: AddProductViewModel by viewModels()

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private var selectedPicture: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        registerLauncher()

        with(binding) {
            ivAddUpdateProduct.setOnClickListener {
                pickImageFromGallery()
            }
            //Back
            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }

            btnAdd.setOnClickListener {

                val productType = when (binding.rgType.checkedRadioButtonId) {
                    R.id.rb_woman -> getString(R.string.woman)
                    R.id.rb_man -> getString(R.string.man)
                    else -> getString(R.string.child)
                }

                val productTitle = etProductTitle.trimmedText()
                val productPrice = etProductPrice.trimmedText()
                val productDescription = etProductDescription.trimmedText()
                val productQuantity = etProductQuantity.trimmedText()

                if (validAddProduct() && selectedPicture != null) {
                    viewModel.addProduct(
                        selectedPicture!!,
                        productTitle,
                        productPrice,
                        productDescription,
                        productQuantity,
                        productType
                    )
                }
            }
        }
    }

    //Permision
    private fun pickImageFromGallery() {
        this.pickImageFromGalleryWithPermission(activityResultLauncher, permissionLauncher)
    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    selectedPicture = result.data?.data
                    selectedPicture?.let { binding.ivProductImage.setImageURI(it) }
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


    private fun validAddProduct(): Boolean {
        with(binding) {
            return checkEditTexts(
                etProductTitle,
                etProductPrice,
                etProductDescription,
                etProductQuantity
            )
        }
    }


    private fun initObservers() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it.equals(true)) requireView().showErrorSnackBar(
                getString(R.string.success),
                false
            )
            else requireView().showErrorSnackBar(getString(R.string.fail), true)

        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    setViewsGone(
                        tilProductDescription, tilProductPrice, tilProductTitle, tilProductQuantity,
                        ivProductImage, ivAddUpdateProduct, rgType, btnAdd
                    )
                    loadingLottie.visible()
                } else {
                    setViewsVisible(
                        tilProductDescription, tilProductPrice, tilProductTitle, tilProductQuantity,
                        ivProductImage, ivAddUpdateProduct, rgType, btnAdd
                    )
                    loadingLottie.gone()
                }
            }

        }
    }
}
