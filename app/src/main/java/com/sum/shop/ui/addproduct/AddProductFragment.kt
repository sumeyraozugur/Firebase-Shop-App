package com.sum.shop.ui.addproduct


import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.sum.shop.R
import com.sum.shop.databinding.FragmentAddProductBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.ui.loginregister.signup.SignUpTermConditionViewModel
import com.sum.shop.utils.*


class AddProductFragment : Fragment(R.layout.fragment_add_product) {

    private val binding by viewBinding(FragmentAddProductBinding::bind)
    private val  viewModel by lazy { AddProductViewModel(requireActivity().application) }
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedPicture : Uri? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        observeIsLoad()
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
                    R.id.rb_woman -> "Woman"
                    R.id.rb_man -> "Man"
                    else -> "Child"
                }

                val productTitle = etProductTitle.text.toString().trim { it <= ' ' }
                val productPrice = etProductPrice.text.toString().trim { it <= ' ' }
                val productDescription = etProductDescription.text.toString().trim { it <= ' ' }
                val productQuantity = etProductQuantity.text.toString().trim { it <= ' ' }

                if( validAddProduct() && selectedPicture != null) {
                    viewModel.addProduct(
                        selectedPicture!!,
                        productTitle,
                        productPrice,
                        productDescription,
                        productQuantity,
                        productType )
                }
            }
        }
    }

//Permision
    private fun pickImageFromGallery() {
       this.pickImageFromGalleryWithPermission(activityResultLauncher,permissionLauncher)
    }
    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedPicture = result.data?.data
                selectedPicture?.let { binding.ivProductImage.setImageURI(it) }
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


    private fun validAddProduct(): Boolean {
        val productTitle = binding.etProductTitle.isNullorEmpty(getString(R.string.required_field))
        val productPrice = binding.etProductPrice.isNullorEmpty(getString(R.string.required_field))
        val productDescription = binding.etProductDescription.isNullorEmpty(getString(R.string.required_field))
        val productQuantity = binding.etProductQuantity.isNullorEmpty(getString(R.string.required_field))
        return productTitle && productPrice && productDescription && productQuantity
    }

    private fun observeIsLoad() {
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                requireView().showErrorSnackBar( getString(R.string.success),false)
            } else {
                requireView().showErrorSnackBar( getString(R.string.fail), true)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            with(binding) {
                loadingLottie.visibility = if (isLoading) View.VISIBLE else View.GONE
                tilProductDescription.visibility = if (isLoading) View.GONE else View.VISIBLE
                tilProductPrice.visibility = if (isLoading) View.GONE else View.VISIBLE
                tilProductTitle.visibility = if (isLoading) View.GONE else View.VISIBLE
                tilProductQuantity.visibility = if (isLoading) View.GONE else View.VISIBLE
                ivProductImage.visibility = if (isLoading) View.GONE else View.VISIBLE
                ivAddUpdateProduct.visibility = if (isLoading) View.GONE else View.VISIBLE
                rgType.visibility = if (isLoading) View.GONE else View.VISIBLE
                btnAdd.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        })
    }
}