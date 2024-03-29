package com.sum.shop.repository


import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.storage.FirebaseStorage
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.model.ProductModel
import com.sum.shop.room.BasketProductDao
import com.sum.shop.room.FavProductDao
import com.sum.shop.utils.constant.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val favDao: FavProductDao,
    private val basketDao: BasketProductDao,
    private val auth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
) {
    var isSuccess = MutableLiveData<Boolean>()
    var isLoading = MutableLiveData<Boolean>()
    var path = ""
    private val calendar by lazy { Calendar.getInstance() }
    val name = auth.currentUser?.uid.toString() + date() + time()

    var favList: MutableLiveData<List<FavModel>> = MutableLiveData()
    val readAllBasket: LiveData<List<BasketModel>> = basketDao.readAllBasket()

    // products upload to Firebase
    fun addProduct(
        img: Uri,
        productTitle: String,
        productPrice: String,
        productDescription: String,
        productQuantiles: String,
        productType: String,
    ) {
        isLoading.value = true

        // Start performance trace
        val trace = FirebasePerformance.getInstance().newTrace("add_product_trace")
        trace.start()

        firebaseStorage.reference.child(name).putFile(img).addOnSuccessListener {

            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->

                val product = hashMapOf(
                    Constant.ID to auth.currentUser?.uid,
                    Constant.PRODUCT_IMAGE to url,
                    Constant.PRODUCT_TITLE to productTitle,
                    Constant.PRODUCT_PRICE to productPrice,
                    Constant.PRODUCT_DESCRIPTION to productDescription,
                    Constant.PRODUCT_QUANTILES to productQuantiles,
                    Constant.PRODUCT_TYPE to productType,
                    Constant.PRODUCT_DATE to date(),
                    Constant.PRODUCT_TIME to time()
                )
                name.let {
                    firebaseFirestore.collection(productType.lowercase()).document()
                        .set(product)
                        .addOnSuccessListener {
                            // Stop performance trace
                            trace.stop()

                            isSuccess.value = true
                            isLoading.value = false
                            //  Log.d("Product", Constant.SUCCESS)
                        }
                        .addOnFailureListener { exception ->
                            // Stop performance trace
                            trace.stop()

                            isSuccess.value = false
                            isLoading.value = false
                            //  Log.w("Product", exception)
                        }
                }
            }
        }
    }

    suspend fun getProductRealtime(path: String): List<ProductModel> = withContext(Dispatchers.IO) {
        isLoading.postValue(true)

        // Start performance trace
        val trace = FirebasePerformance.getInstance().newTrace("get_product_realtime_trace")
        trace.start()

        val docRef = firebaseFirestore.collection(path).get().await()
        val favList = favDao.getFavTitles().orEmpty()
        val tempList = arrayListOf<ProductModel>()
        docRef.documents.forEach { document ->

            tempList.add(
                ProductModel(
                    document.id,
                    document.get("image") as String,
                    document.get("title") as String,
                    document.get("description") as String,// as? String ?: "No description available",
                    document.get("price") as String,
                    document.get("quantiles") as String,
                    favList.contains(document.get("title") as String)
                )
            )
        }

        // Stop performance trace
        trace.stop()

        isLoading.postValue(false) // For main thread
        tempList
    }

    //
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

    fun returnFavList(): MutableLiveData<List<FavModel>> = favList

    suspend fun getAllFav() {
        val list = favDao.getAllFav()
        favList.postValue(list)
    }

    suspend fun addToFav(product: FavModel) = favDao.addToFav(product)

    suspend fun deleteFromFav(fav: FavModel) = favDao.deleteFromFav(fav)

    suspend fun addToBasket(product: BasketModel) = basketDao.addToBasket(product)

    suspend fun deleteFromBasket(basketId: String) = basketDao.deleteFromBasket(basketId)

    suspend fun updateBasket(product: BasketModel) = basketDao.updateBasket(product)

    suspend fun totalBasket(): Double = basketDao.getTotalPrice()
}