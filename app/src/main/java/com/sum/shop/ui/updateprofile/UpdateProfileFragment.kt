package com.sum.shop.ui.updateprofile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sum.shop.R
import com.sum.shop.databinding.FragmentUpdateProfileBinding
import com.sum.shop.delegate.viewBinding
import com.sum.shop.utils.back
import com.sum.shop.utils.showErrorSnackBar


class UpdateProfileFragment : Fragment(R.layout.fragment_update_profile) {

    private val binding by viewBinding(FragmentUpdateProfileBinding::bind)
    private val viewModel by lazy{ UpdateProfileViewModel() }
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedPicture : Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        registerLauncher()

        with(binding) {

            ibArrowBack.setOnClickListener {
                Navigation.back(it)
            }
            ivUpdate.setOnClickListener {
                selectImage()
            }

            btnUpdate.setOnClickListener {
                val firstName = etFirstName.text.toString().trim { it <= ' ' }
                val lastName = etLastName.text.toString().trim { it <= ' ' }
                val email = etEmail.text.toString().trim { it <= ' ' }


                if(validateProfile() && selectedPicture != null)
                    selectedPicture?.let { url->
                        viewModel.updateProfile(firstName, lastName, email,
                            url
                        )
                    }
            }
        }
    }
    private fun validateProfile(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_name),
                    true
                )
                false
            }

            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_lastname),
                    true
                )
                false
            }

            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    requireContext(),
                    requireView(),
                    getString(R.string.required_email),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    private fun selectImage() {
        if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(requireView(),"Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give permission", View.OnClickListener {
                    //request permission
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            }else{
                //request permission
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }else{
            //permission granted
            val intentToGallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }
    private fun registerLauncher() {

        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == Activity.RESULT_OK){
                val intentFromData=result.data
                if(intentFromData != null){
                    selectedPicture=intentFromData.data
                    selectedPicture?.let {
                        binding.ivUpdate.setImageURI(it)
                    }
                }

            }else{
                Toast.makeText(requireContext(),"Image not selected",Toast.LENGTH_LONG).show()


            }
        }

        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
                result ->
            if(result){
                val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intent)
            }else{
                Toast.makeText(requireContext(),"Permission need2",Toast.LENGTH_LONG).show();
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

            viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
                if (it) {
                    showErrorSnackBar(requireContext(), requireView(), getString(R.string.success), false)
                } else {
                    showErrorSnackBar(requireContext(), requireView(), getString(R.string.fail), true)
                }
            })
        }
    }
}