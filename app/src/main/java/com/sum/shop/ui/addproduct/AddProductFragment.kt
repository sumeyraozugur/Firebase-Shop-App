package com.sum.shop.ui.addproduct

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sum.shop.R
import com.sum.shop.databinding.FragmentAddProductBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.isNullorEmpty
import com.sum.shop.utils.showErrorSnackBar


class AddProductFragment : Fragment(R.layout.fragment_add_product) {

    private val binding by viewBinding(FragmentAddProductBinding::bind)
    private val viewModel by lazy { AddProductViewModel() }
    private var picture: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeIsLoad()

        with(binding) {
            ivAddUpdateProduct.setOnClickListener {
                 //viewModel.addImage()
                pickImageFromGallery()
            }
            btnAdd.setOnClickListener {
               val chosenOption = binding.rgType.checkedRadioButtonId

                val productType = when (chosenOption) {
                    R.id.rbWoman -> "Woman"
                    R.id.rbMan -> "Man"
                    else -> "Home Appliances"
                }

                val productTitle = etProductTitle.text.toString().trim { it <= ' ' }
                val productPrice = etProductPrice.text.toString().trim { it <= ' ' }
                val productDescription = etProductDescription.text.toString().trim { it <= ' ' }
                val productQuantity = etProductQuantity.text.toString().trim { it <= ' ' }
                picture
                if( validAddProduct() && picture != null) {
                    if(productType =="Woman")
                        viewModel.addProduct(
                            picture!!,
                            productTitle,
                            productPrice,
                            productDescription,
                            productQuantity,
                            productType
                        )

                    if(productType == "Man")
                        viewModel.addProductMan(
                            picture!!,
                            productTitle,
                            productPrice,
                            productDescription,
                            productQuantity,
                            productType
                        )

                    if(productType == "Home Appliances")
                        viewModel.addProductAppliance(
                            picture!!,
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imageFromGalleryLauncher.launch(intent)
    }

    private var imageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                picture = result.data?.data
                Glide.with(this).load(picture).into(binding.ivProductImage)
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
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.success),
                    false
                )
            } else {
                showErrorSnackBar(requireContext(), requireView(), getString(R.string.fail), true)
            }
        })
    }


}