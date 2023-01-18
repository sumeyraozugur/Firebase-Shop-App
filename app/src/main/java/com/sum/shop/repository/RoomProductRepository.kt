import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDao
import com.sum.shop.room.FavProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomProductRepository(
    private val favProductDao: FavProductDao,
    private val basketProductDao: BasketProductDao
) {
    var kisilerListesi: MutableLiveData<List<FavModel>> = MutableLiveData()
    val readAllBasket: LiveData<List<BasketModel>> = basketProductDao.readAllBasket()
    var isFav = MutableLiveData<Boolean>()


    fun kisileriGetir(): MutableLiveData<List<FavModel>> {
        return kisilerListesi
    }

    fun getAll() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            kisilerListesi.value = favProductDao.readAllFav()
        }
    }

    suspend fun addToFav(product: FavModel) {
        favProductDao.addToFav(product)
        withContext(Dispatchers.Main) {
            isFav.value = true
        }
    }


    suspend fun deleteFromFav(favId: Int) {
        favProductDao.deleteFromFav(favId)
        withContext(Dispatchers.Main) {
            isFav.value = false
        }
    }

    suspend fun updateFav(fav: FavModel) {
        favProductDao.updateFav(fav)
    }

    suspend fun addToBasket(product: BasketModel) {
        basketProductDao.addToBasket(product)
    }

    suspend fun deleteFromBasket(basketId: Int) {
        basketProductDao.deleteFromBasket(basketId)
    }
}