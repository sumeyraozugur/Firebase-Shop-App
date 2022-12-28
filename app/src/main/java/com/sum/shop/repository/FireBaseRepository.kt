package com.sum.shop.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sum.shop.Constant.APPLIANCE_PATH
import com.sum.shop.Constant.E_MAIL
import com.sum.shop.Constant.FIRST_NAME
import com.sum.shop.Constant.ID
import com.sum.shop.Constant.LAST_NAME
import com.sum.shop.Constant.MAN_PATH
import com.sum.shop.Constant.PRODUCT_DATE
import com.sum.shop.Constant.PRODUCT_DESCRIPTION
import com.sum.shop.Constant.PRODUCT_IMAGE
import com.sum.shop.Constant.PRODUCT_PRICE
import com.sum.shop.Constant.PRODUCT_QUANTILES
import com.sum.shop.Constant.PRODUCT_TIME
import com.sum.shop.Constant.PRODUCT_TITLE
import com.sum.shop.Constant.PRODUCT_TYPE
import com.sum.shop.Constant.SIGN_UP
import com.sum.shop.Constant.SUCCESS
import com.sum.shop.Constant.USERS_PATH
import com.sum.shop.Constant.WOMAN_PATH
import com.sum.shop.model.ProductModel
import com.sum.shop.model.ProfileModel
import java.util.*

class FireBaseRepository {
    var profileInfo = MutableLiveData<ProfileModel>()
    var isSuccess = MutableLiveData<Boolean>()
    var isSignIn = MutableLiveData<Boolean>()
    var womanList = MutableLiveData<List<ProductModel>>()
    var manList = MutableLiveData<List<ProductModel>>()
    var applianceList = MutableLiveData<List<ProductModel>>()

    private var auth = Firebase.auth
    private var firebaseFirestore = Firebase.firestore
    private val firebaseStorage by lazy { Firebase.storage.reference }
    private val calendar by lazy { Calendar.getInstance() }
    val name = auth.currentUser?.uid.toString() + date() + time()

    //Register
    fun signUp(
        firstName: String,
        lastName: String,
        eMail: String,
        password: String,
        isAccept: Boolean = false,
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
            isSuccess.value = true
        }.addOnFailureListener {
            isSuccess.value = false
        }
    }

    //Current user control
    fun checkCurrentUser() {
        isSuccess.value = false
        auth.currentUser?.let {
            isSuccess.value = true
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
                    isSuccess.value = true

                }.addOnFailureListener {
                    isSuccess.value = false

                }
        }
    }

    //Signout
    fun signOut() {
        auth.signOut()
    }

    //Product upload to Firebase
    fun addProductWoman(
        img: Uri,
        womanTitle: String,
        womanPrice: String,
        womanDescription: String,
        womanQuantiles: String,
        womanType: String = "Woman",
    ) {
        firebaseStorage.child(name).putFile(img).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->

                val woman = hashMapOf(
                    ID to auth.currentUser?.uid,
                    PRODUCT_IMAGE to url,
                    PRODUCT_TITLE to womanTitle,
                    PRODUCT_PRICE to womanPrice,
                    PRODUCT_DESCRIPTION to womanDescription,
                    PRODUCT_QUANTILES to womanQuantiles,
                    PRODUCT_TYPE to womanType,
                    PRODUCT_DATE to date(),
                    PRODUCT_TIME to time()
                )
                name.let {
                    firebaseFirestore.collection(WOMAN_PATH).document()
                        .set(woman)
                        .addOnSuccessListener {
                            isSuccess.value = true
                            println(name)
                            Log.d("Product", SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            isSuccess.value = false
                            Log.w("Product", exception)
                        }
                }
            }
        }
    }

    fun addProductMan(
        img: Uri,
        manTitle: String,
        manPrice: String,
        manDescription: String,
        manQuantiles: String,
        manType: String = "Man",
    ) {
        firebaseStorage.child(name).putFile(img).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->

                val man = hashMapOf(
                    ID to auth.currentUser?.uid,
                    PRODUCT_IMAGE to url,
                    PRODUCT_TITLE to manTitle,
                    PRODUCT_PRICE to manPrice,
                    PRODUCT_DESCRIPTION to manDescription,
                    PRODUCT_QUANTILES to manQuantiles,
                    PRODUCT_TYPE to manType,
                    PRODUCT_DATE to date(),
                    PRODUCT_TIME to time()
                )
                name.let {
                    firebaseFirestore.collection(MAN_PATH).document()
                        .set(man)
                        .addOnSuccessListener {
                            isSuccess.value = true
                            println(name)
                            Log.d("Product", SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            isSuccess.value = false
                            Log.w("Product", exception)
                        }
                }
            }
        }
    }


    fun addProductAppliances(
        img: Uri,
        applianceTitle: String,
        appliancePrice: String,
        applianceDescription: String,
        applianceQuantiles: String,
        applianceType: String = "Home Appliances",
    ) {
        firebaseStorage.child(name).putFile(img).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->

                val appliance = hashMapOf(
                    ID to auth.currentUser?.uid,
                    PRODUCT_IMAGE to url,
                    PRODUCT_TITLE to applianceTitle,
                    PRODUCT_PRICE to appliancePrice,
                    PRODUCT_DESCRIPTION to applianceDescription,
                    PRODUCT_QUANTILES to applianceQuantiles,
                    PRODUCT_TYPE to applianceType,
                    PRODUCT_DATE to date(),
                    PRODUCT_TIME to time()
                )
                name.let {
                    firebaseFirestore.collection(APPLIANCE_PATH).document()
                        .set(appliance)
                        .addOnSuccessListener {
                            isSuccess.value = true
                            println(name)
                            Log.d("Product", SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            isSuccess.value = false
                            Log.w("Product", exception)
                        }
                }
            }
        }
    }

    fun getProductAppliancesRealtime() {

        val docRef = firebaseFirestore.collection("appliance")

        docRef.get().addOnSuccessListener { documents ->
            println(documents.documents)

            homeAppliancesToList(documents)

        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
    }


    fun getProductManRealtime() {

        val docRef = firebaseFirestore.collection("man")

        docRef.get().addOnSuccessListener { documents ->
            println(documents.documents)

            manToList(documents)

        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
    }


    fun getProductWomanRealtime() {

        val docRef = firebaseFirestore.collection("woman")

        docRef.get().addOnSuccessListener { documents ->
            womanToList(documents)
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
    }

    private fun womanToList(
        querySnapshot: QuerySnapshot?,
    ) {
        val tempList = arrayListOf<ProductModel>()
        querySnapshot?.let {
            it.forEach { document ->
                tempList.add(
                    ProductModel(
                        document.id,
                        document.get("product image") as String,
                        document.get("product title") as String,
                        document.get("product description") as String,
                        document.get("product price") as String,
                        document.get("product quantiles") as String
                    )
                )
            }
            womanList.value = tempList
        }
    }


    private fun manToList(
        querySnapshot: QuerySnapshot?,
    ) {

        val manTempList = arrayListOf<ProductModel>()
        querySnapshot?.let {
            it.forEach { document ->
                manTempList.add(
                    ProductModel(
                        document.id,
                        document.get("product image") as String,
                        document.get("product title") as String,
                        document.get("product description") as String,
                        document.get("product price") as String,
                        document.get("product quantiles") as String
                    )
                )
            }
            manList.value = manTempList
        }
    }

    private fun homeAppliancesToList(
        querySnapshot: QuerySnapshot?,
    ) {

        val homeAppliancesTempList = arrayListOf<ProductModel>()
        querySnapshot?.let {
            it.forEach { document ->
                homeAppliancesTempList.add(
                    ProductModel(
                        document.id,
                        document.get("product image") as String,
                        document.get("product title") as String,
                        document.get("product description") as String,
                        document.get("product price") as String,
                        document.get("product quantiles") as String
                    )
                )
            }
            applianceList.value = homeAppliancesTempList
        }
    }


    private fun date(): Int {
        val date = calendar[Calendar.DAY_OF_MONTH].toString() +
                calendar[Calendar.MONTH].toString() +
                calendar[Calendar.YEAR].toString()

        return date.toInt()
    }

    private fun time(): Int {

        val time = calendar[Calendar.MILLISECOND].toString() +
                calendar[Calendar.MINUTE].toString() +
                calendar[Calendar.HOUR_OF_DAY].toString()

        return time.toInt()
    }

}