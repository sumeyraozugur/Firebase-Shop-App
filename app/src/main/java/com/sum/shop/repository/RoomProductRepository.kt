import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDao
import com.sum.shop.room.FavProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomProductRepository(private val favProductDao: FavProductDao,private val basketProductDao: BasketProductDao) {

    val readAllFav: LiveData<List<FavModel>> = favProductDao.readAllFav()
    val readAllBasket: LiveData<List<BasketModel>> =basketProductDao.readAllBasket()
     //val isFav= MutableLiveData(false)

    suspend fun addFav(product: FavModel) {
        favProductDao.addToFav(product)
        //isFav.value =true
    }

    suspend fun deleteFav(favId:Int) {
        favProductDao.deleteFromFav(favId)
    }

    suspend fun updateTodo(fav: FavModel) {
        favProductDao.updateFav(fav)
    }

    suspend fun addBasket(product: BasketModel) {
        basketProductDao.addToBasket(product)
    }

    suspend fun deleteBasket(basketId:Int) {
        basketProductDao.deleteFromBasket(basketId)
    }
}