package com.sum.shop.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sum.shop.Constant
import com.sum.shop.model.ProductModel
import java.util.*

class FirebaseRepository {
    var isSuccess = MutableLiveData<Boolean>()
    var categoryList = MutableLiveData<List<ProductModel>>()
    var path = ""
    private var auth = Firebase.auth
    private var firebaseFirestore = Firebase.firestore
    private val firebaseStorage by lazy { Firebase.storage.reference }
    private val calendar by lazy { Calendar.getInstance() }
    val name = auth.currentUser?.uid.toString() + date() + time()


    // products upload to Firebase
    fun addProduct(
        img: Uri,
        womanTitle: String,
        womanPrice: String,
        womanDescription: String,
        womanQuantiles: String,
        womanType: String,
    ) {
        firebaseStorage.child(name).putFile(img).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->

                val product = hashMapOf(
                    Constant.ID to auth.currentUser?.uid,
                    Constant.PRODUCT_IMAGE to url,
                    Constant.PRODUCT_TITLE to womanTitle,
                    Constant.PRODUCT_PRICE to womanPrice,
                    Constant.PRODUCT_DESCRIPTION to womanDescription,
                    Constant.PRODUCT_QUANTILES to womanQuantiles,
                    Constant.PRODUCT_TYPE to womanType,
                    Constant.PRODUCT_DATE to date(),
                    Constant.PRODUCT_TIME to time()
                )
                name.let {
                    firebaseFirestore.collection(womanType.lowercase()).document()
                        .set(product)
                        .addOnSuccessListener {
                            isSuccess.value = true
                            println(name)
                            Log.d("Product", Constant.SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            isSuccess.value = false
                            Log.w("Product", exception)
                        }
                }
            }
        }
    }


    fun getProductRealtime(path:String) {

        val docRef = firebaseFirestore.collection(path)


        docRef.get().addOnSuccessListener { documents ->
            println(documents.documents)

            tempToList(documents)

        }.addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "get failed with ", exception)
        }
    }

    private fun tempToList(
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
            categoryList.value = tempList
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