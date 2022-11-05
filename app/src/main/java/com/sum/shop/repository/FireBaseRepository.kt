package com.sum.shop.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sum.shop.Constant.E_MAIL
import com.sum.shop.Constant.FIRST_NAME
import com.sum.shop.Constant.ID
import com.sum.shop.Constant.LAST_NAME
import com.sum.shop.Constant.PRODUCTS_PATH
import com.sum.shop.Constant.PRODUCT_DESCRIPTION
import com.sum.shop.Constant.PRODUCT_IMAGE
import com.sum.shop.Constant.PRODUCT_PRICE
import com.sum.shop.Constant.PRODUCT_QUANTILES
import com.sum.shop.Constant.PRODUCT_TITLE
import com.sum.shop.Constant.PRODUCT_TYPE
import com.sum.shop.Constant.SIGN_UP
import com.sum.shop.Constant.SUCCESS
import com.sum.shop.Constant.USERS_PATH
import com.sum.shop.model.ProfileModel

class FireBaseRepository {

    var isSignUp = MutableLiveData<Boolean>()
    var isSignIn = MutableLiveData<Boolean>()
    var isChangePassword = MutableLiveData<Boolean>()
    var isCurrentUser = MutableLiveData<Boolean>()
    var profileInfo = MutableLiveData<ProfileModel>()
    var updateInfo = MutableLiveData<Boolean>()
    var isLoadProduct = MutableLiveData<Boolean>()

    //val selectedPicture : Uri ?=null

    private var auth = Firebase.auth
    private var firebaseFirestore = Firebase.firestore
    private val firebaseStorage by lazy { Firebase.storage.reference }


    //Register
    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        isAccept: Boolean = false
    ) {
        auth.createUserWithEmailAndPassword(eMail, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val currentUser = auth.currentUser
                currentUser?.let { firebaseUser ->
                    val user = hashMapOf(
                        ID to firebaseUser.uid,
                        E_MAIL to eMail,
                        FIRST_NAME to firstName,
                        LAST_NAME to lastName
                    )
                    firebaseFirestore.collection(USERS_PATH).document(firebaseUser.uid)
                        .set(user)
                        .addOnSuccessListener {
                            isSignUp.value = true
                            Log.d(SIGN_UP, SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            isSignUp.value = false
                            Log.w(SIGN_UP, exception)
                        }
                }
            } else {
                isSignUp.value = false
                Log.w(SIGN_UP, task.exception)
            }
        }
    }

    //Login
    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            it?.let {
                isSignIn.value = true
            }
        }.addOnFailureListener {
            isSignIn.value = false
        }
    }

    //Change Password
    fun changePassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            isChangePassword.value = true
        }.addOnFailureListener {
            isChangePassword.value = false
        }
    }


    //Current user control
    fun checkCurrentUser() {
        isCurrentUser.value = false
        auth.currentUser?.let {
            isCurrentUser.value = true
        }
    }

    //Profile
    fun getProfileInfo() {
        auth.currentUser?.let { user ->

            val docRef = firebaseFirestore.collection("users").document(user.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    document?.let {
                        profileInfo.value = ProfileModel(
                            document.get("firstname") as String,
                            document.get("lastname") as String,
                            user.email,
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
        }
    }

    //update Profile
    fun updateProfile(firstName: String, lastName: String, email: String) {
        auth.currentUser?.let {
            firebaseFirestore.collection(USERS_PATH).document(it.uid)
                .update(
                    "firstname", firstName,
                    "lastname", lastName,
                    "email", email
                ).addOnSuccessListener {
                    updateInfo.value = true

                }.addOnFailureListener {
                    updateInfo.value = false

                }
        }
    }


    //Signout
    fun signOut() {
        auth.signOut()
    }

    fun addProduct(
        img: Uri,
        productTitle: String,
        productPrice: String,
        productDescription: String,
        productQuantiles: String,
        productType: String = "Woman"
    ) {
        firebaseStorage.child(auth.currentUser?.uid.toString()).putFile(img).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->


                val product = hashMapOf(
                    ID to auth.currentUser?.uid,
                    PRODUCT_IMAGE to url,
                    PRODUCT_TITLE to productTitle,
                    PRODUCT_PRICE to productPrice,
                    PRODUCT_DESCRIPTION to productDescription,
                    PRODUCT_QUANTILES to productQuantiles,
                    PRODUCT_TYPE to productType
                )

                auth.currentUser?.uid?.let {
                    firebaseFirestore.collection(PRODUCTS_PATH).document(it)
                        .set(product)
                        .addOnSuccessListener {
                            isLoadProduct.value = true
                            Log.d("Product", SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            isLoadProduct.value = false
                            Log.w("Product", exception)
                        }
                }

            }
        }
    }

}