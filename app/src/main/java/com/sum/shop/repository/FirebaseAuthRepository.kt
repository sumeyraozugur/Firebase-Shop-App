package com.sum.shop.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.storage.FirebaseStorage
import com.sum.shop.model.ProfileModel
import com.sum.shop.utils.constant.Constant.E_MAIL
import com.sum.shop.utils.constant.Constant.FIRST_NAME
import com.sum.shop.utils.constant.Constant.ID
import com.sum.shop.utils.constant.Constant.LAST_NAME
import com.sum.shop.utils.constant.Constant.PROFILE_PICTURE
import com.sum.shop.utils.constant.Constant.SIGN_UP
import com.sum.shop.utils.constant.Constant.SUCCESS
import com.sum.shop.utils.constant.Constant.USERS_PATH
import java.net.HttpURLConnection
import javax.inject.Inject
import com.google.firebase.perf.metrics.Trace

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
) {
    val profileInfo = MutableLiveData<ProfileModel>() //callback flow
    val isSignIn = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val isSuccess = MutableLiveData<Boolean>()
    val name = auth.currentUser?.uid.toString()

    //Register
    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        picture: Uri? = null,
    ) {
        isLoading.value = true
        val metric = FirebasePerformance.getInstance().newHttpMetric("/signup", FirebasePerformance.HttpMethod.POST)
        metric.start()
        auth.createUserWithEmailAndPassword(eMail, password).addOnSuccessListener {
            isLoading.value = false

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
                        metric.setHttpResponseCode(HttpURLConnection.HTTP_OK)
                        metric.setRequestPayloadSize((firstName+lastName+eMail+password).length.toLong())
                        metric.stop()
                        Log.d(SIGN_UP, SUCCESS)
                    }
                    .addOnFailureListener { exception ->
                        isSuccess.value = false
                        metric.setHttpResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                        metric.setRequestPayloadSize((firstName+lastName+eMail+password).length.toLong())
                        metric.stop()
                        Log.w(SIGN_UP, exception)
                    }
            }?.addOnFailureListener {
                isSuccess.value = false
                metric.setHttpResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                metric.setRequestPayloadSize((firstName+lastName+eMail+password).length.toLong())
                metric.stop()
                Log.w(SIGN_UP, it.toString())
            }
        }
    }


    //Login


    fun signIn(email: String, password: String) {
        val trace = FirebasePerformance.getInstance().newTrace("signIn")
        trace.start()

        isLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
            isLoading.value = false
            authResult?.let {
                isSignIn.value = true
               // trace.incrementCounter("success")
            }
        }.addOnFailureListener {
            isSignIn.value = false
            isLoading.value = false
           // trace.incrementCounter("failure")
        }.addOnCompleteListener {
            trace.stop()
        }
    }


    //Change Password
    fun changePassword(email: String) {
        isLoading.value = true
        auth.sendPasswordResetEmail(email).addOnSuccessListener {//or addOnCompleteListener
            isSuccess.value = true
            isLoading.value = false
        }.addOnFailureListener {
            isSuccess.value = false
            isLoading.value = false
        }
    }


    //Profile
    fun getProfileInfo() {
        val trace = FirebasePerformance.getInstance().newTrace("getProfileInfo")
        trace.start()

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
                        //trace.incrementCounter("success")
                    }
                }
                .addOnFailureListener { exception ->
                    isLoading.value = false
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                    //trace.incrementCounter("failure")
                }
                .addOnCompleteListener {
                    trace.stop()
                }
        }
    }


    //update Profile
    fun updateProfile(firstName: String, lastName: String, email: String, picture: Uri) {
        isLoading.value = true

        firebaseStorage.reference.child(name).putFile(picture).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->
                auth.currentUser?.let { user ->
                    firebaseFirestore.collection(USERS_PATH).document(user.uid)
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
    fun signOut() = auth.signOut()
}