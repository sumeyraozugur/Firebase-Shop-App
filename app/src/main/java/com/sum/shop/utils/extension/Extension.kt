package com.sum.shop.utils

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.sum.shop.R
import com.sum.shop.utils.customs.EditText

fun Navigation.sent(v: View, id: Int) {
    findNavController(v).navigate(id)
}

fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun Navigation.back(v:View){
    findNavController(v).navigateUp()

}

fun View.gone() {
    visibility = View.GONE
}

fun View.showErrorSnackBar(message: String, errorMessage: Boolean) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view

    if (errorMessage) {
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_snack_bar_error))
    } else {
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_snack_bar_success))
    }
    snackBar.show()
}

fun showProgressDialog(text: String,context: Context) {

    val mProgressDialog = Dialog(context)
    mProgressDialog.setContentView(R.layout.dialog_progress)

    mProgressDialog.findViewById<TextView>(R.id.tv_progress_text).text =text
    mProgressDialog.setCancelable(false)
    mProgressDialog.setCanceledOnTouchOutside(false)

    //Start the dialog and display it on screen.
    mProgressDialog.show()
}

fun hideProgressDialog(context: Context) {
    val mProgressDialog = Dialog(context)
    mProgressDialog.setContentView(R.layout.dialog_progress)
    if (mProgressDialog.isShowing) {
        mProgressDialog.dismiss()
    }
}




fun EditText.isValidEmail(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}


fun EditText.isNullorEmpty(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (text.toString().trim().isNotEmpty()) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun Fragment.pickImageFromGalleryWithPermission(
    activityResultLauncher: ActivityResultLauncher<Intent>,
    permissionLauncher: ActivityResultLauncher<String>
) {
    when {
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED -> {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Snackbar.make(
                    requireView(),
                    "Permission needed for gallery",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Give permission") {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        else -> {
            activityResultLauncher.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            )
        }
    }
}






