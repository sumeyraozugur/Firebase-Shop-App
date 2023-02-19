package com.sum.shop.repository

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sum.shop.Constant
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.model.ProductModel
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ProductRepository(application: Application) {
    var isSuccess = MutableLiveData<Boolean>()
    var path = ""
    private var auth = Firebase.auth
    private var firebaseFirestore = Firebase.firestore
    private val firebaseStorage by lazy { Firebase.storage.reference }
    private val calendar by lazy { Calendar.getInstance() }
    val name = auth.currentUser?.uid.toString() + date() + time()

    val favDao = FavProductDatabase.getDatabase(application).favDao()
    val basketDao = BasketProductDatabase.getDatabase(application).basketDao()
    var favList: MutableLiveData<List<FavModel>> = MutableLiveData()
    val readAllBasket: LiveData<List<BasketModel>> = basketDao.readAllBasket()


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
                            // println(name)
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


    suspend fun getProductRealtime(path: String): List<ProductModel> = withContext(Dispatchers.IO) {

        val docRef = firebaseFirestore.collection(path).get().await()
        val favList = favDao.getFavoritesTitles().orEmpty()
        val tempList = arrayListOf<ProductModel>()
        docRef.documents.forEach {  document ->
            tempList.add(
                ProductModel(
                    document.id,
                    document.get("product image") as String,
                    document.get("product title") as String,
                    document.get("product description") as String,
                    document.get("product price") as String,
                    document.get("product quantiles") as String,
                    favList.contains(document.get("product title") as String)
                )
            )
        }

        tempList
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

//Local repo

    fun returnFavList(): MutableLiveData<List<FavModel>> {
        return favList
    }

    fun getAllFav() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            favList.value = favDao.getAllFav()
        }
    }


    suspend fun addToFav(product: FavModel) {
        favDao.addToFav(product)
    }


    suspend fun deleteFromFav(fav: FavModel) {
        favDao.deleteFromFav(fav)
    }


    suspend fun addToBasket(product: BasketModel) {
        basketDao.addToBasket(product)
    }

    suspend fun deleteFromBasket(basketId: String) {
        basketDao.deleteFromBasket(basketId)
    }

    suspend fun updateBasket(product: BasketModel){
        basketDao.update(product)
    }

     suspend fun totalBasket():Double{
        return  basketDao.getTotalPrice()
    }
}