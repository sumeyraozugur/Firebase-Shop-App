package com.sum.shop.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sum.shop.utils.constant.Constant.E_MAIL
import com.sum.shop.utils.constant.Constant.FIRST_NAME
import com.sum.shop.utils.constant.Constant.ID
import com.sum.shop.utils.constant.Constant.LAST_NAME
import com.sum.shop.utils.constant.Constant.PROFILE_PICTURE
import com.sum.shop.utils.constant.Constant.SIGN_UP
import com.sum.shop.utils.constant.Constant.SUCCESS
import com.sum.shop.utils.constant.Constant.USERS_PATH
import com.sum.shop.model.ProfileModel

class FirebaseAuthRepository {
    var profileInfo = MutableLiveData<ProfileModel>()
    var isSignIn = MutableLiveData<Boolean>()
    var isLoading = MutableLiveData<Boolean>()
    var isSuccess = MutableLiveData<Boolean>()
    var resultOk = MutableLiveData<Boolean>()
    private var auth = Firebase.auth
    private var firebaseFirestore = Firebase.firestore
    private val firebaseStorage by lazy { Firebase.storage.reference }
    val name = auth.currentUser?.uid.toString()


    //Register
    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        picture: Uri? = null,
        isAccept: Boolean = false
    ) {
        isLoading.value = true
        auth.createUserWithEmailAndPassword(eMail, password).addOnCompleteListener { task ->
            isLoading.value = false
            if (task.isSuccessful) {
                val currentUser = auth.currentUser
                currentUser?.let { firebaseUser ->
                    val user = hashMapOf(
                        ID to firebaseUser.uid,
                        E_MAIL to eMail,
                        FIRST_NAME to firstName,
                        LAST_NAME to lastName,
                        PROFILE_PICTURE to picture
                    )
                    firebaseFirestore.collection(USERS_PATH).document(firebaseUser.uid)
                        .set(user)
                        .addOnSuccessListener {
                            isSuccess.value = true
                            Log.d(SIGN_UP, SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            isSuccess.value = false
                            Log.w(SIGN_UP, exception)
                        }
                }
            } else {
                isSuccess.value = false
                Log.w(SIGN_UP, task.exception)
            }
        }
    }

    //Login
    fun signIn(email: String, password: String) {
        isLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {authResult->
            isLoading.value = false
            authResult?.let {
                isSignIn.value = true
            }
        }.addOnFailureListener {
            isSignIn.value = false
            isLoading.value = false
        }
    }

    //Change Password
    fun changePassword(email: String) {
        isLoading.value = true
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            isSuccess.value = true
            isLoading.value = false
        }.addOnFailureListener {
            isSuccess.value = false
            isLoading.value = false
        }
    }


    //Profile
    fun getProfileInfo() {
        isLoading.value = true
        auth.currentUser?.let { user ->

            val docRef = firebaseFirestore.collection(USERS_PATH).document(user.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    isLoading.value = false
                    document?.let {
                        profileInfo.value = ProfileModel(
                            document.get("firstname") as String,
                            document.get("lastname") as String,
                            user.email,
                            document.get("picture") as String
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    isLoading.value = false
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
        }
    }

    //update Profile
    fun updateProfile(firstName: String, lastName: String, email: String, picture:Uri) {
        isLoading.value = true
        firebaseStorage.child(name).putFile(picture).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->
                auth.currentUser?.let { it ->
                    firebaseFirestore.collection(USERS_PATH).document(it.uid)
                        .update(
                            "firstname", firstName,
                            "lastname", lastName,
                            "email", email,
                            "picture", url
                        ).addOnSuccessListener {
                            isSuccess.value = true
                            isLoading.value = false

                        }.addOnFailureListener {
                            isSuccess.value = false
                            isLoading.value = false

                        }
                }
            }
        }
    }

    //Signout
    fun signOut() {
        auth.signOut()
    }

    //check radio button clicked or not
    fun checkResult() {
        resultOk.value = true
    }
}