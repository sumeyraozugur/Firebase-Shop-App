package com.sum.shop.utils

import android.app.Dialog
import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.sum.shop.R

fun Navigation.sent(v: View, id: Int) {
    findNavController(v).navigate(id)
}

fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun showSnackbar(view: View, text: Int) {
    Snackbar.make(view, text, 1000).show()
}
fun showErrorSnackBar(context: Context,view: View,message: String, errorMessage: Boolean) {
    val snackBar =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackBarView = snackBar.view



    if (errorMessage) {
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(  //snackBar.setActionTextColor(Color.BLACK) snackBar.setBackgroundTint(Color.CYAN)
                context,
                R.color.color_snack_bar_error
            )
        )
    } else {
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_snack_bar_success
            )
        )
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
    mProgressDialog.dismiss()
}





/*fun EditText.validEmail():Boolean{
    val textInputLayout = this.parent.parent as TextInputLayout
    val input = this.text.toString()
    if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
        textInputLayout.helperText="Please enter a valid email address"
        return false
    }else{
        textInputLayout.helperText=""
    }
    return true
}*/


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





