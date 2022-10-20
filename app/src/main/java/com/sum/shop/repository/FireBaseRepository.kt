package com.sum.shop.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sum.shop.Constant.COLLECTION_PATH
import com.sum.shop.Constant.E_MAIL
import com.sum.shop.Constant.FIRST_NAME
import com.sum.shop.Constant.ID
import com.sum.shop.Constant.LAST_NAME
import com.sum.shop.Constant.SIGN_UP
import com.sum.shop.Constant.SUCCESS

class FireBaseRepository {

    private var _isSignUp= MutableLiveData<Boolean>()
    val isSignUp:MutableLiveData<Boolean>
        get()=_isSignUp

    private var _isSignIn= MutableLiveData<Boolean>()
    val isSignIn:MutableLiveData<Boolean>
        get()=_isSignIn

    private var _isChangePassword= MutableLiveData<Boolean>()
    val isChangePassword:MutableLiveData<Boolean>
        get()=_isChangePassword

    private var _isCurrentUser= MutableLiveData<Boolean>()
    val isCurrentUser:MutableLiveData<Boolean>
        get()=_isCurrentUser


    private var auth = Firebase.auth
    private var fireStore=Firebase.firestore



   //Register
    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        isAccept: Boolean=false
    ) {
        auth.createUserWithEmailAndPassword(eMail, password).addOnCompleteListener {  task->
            if (task.isSuccessful) {
                val currentUser = auth.currentUser
                currentUser?.let { firebaseUser ->
                    val user = hashMapOf(
                        ID to firebaseUser.uid,
                        E_MAIL to eMail,
                        FIRST_NAME to firstName,
                        LAST_NAME to lastName
                    )
                    fireStore.collection(COLLECTION_PATH).document(firebaseUser.uid)
                        .set(user)
                        .addOnSuccessListener {
                            isSignUp.value=true
                            Log.d(SIGN_UP, SUCCESS)
                        }
                        .addOnFailureListener { exception->
                            isSignUp.value = false
                            Log.w(SIGN_UP,exception )
                        }
                }
            }else{
                isSignUp.value = false
                Log.w(SIGN_UP, task.exception)
            }
        }
    }

    //Login
    fun signIn(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            it?.let {
                isSignIn.value= true
            }
        }.addOnFailureListener {
            isSignIn.value= false
        }
    }

    //Change Password
    fun changePassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            _isChangePassword.value= true
        }.addOnFailureListener {
            _isChangePassword.value =false
        }
    }


    //Current user control
    fun checkCurrentUser(){
        _isCurrentUser.value=false
        auth.currentUser?.let {
            _isCurrentUser.value = true
        }
    }

    //Signout
    fun signOut(){
        auth.signOut()
    }

}