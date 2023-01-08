import androidx.lifecycle.LiveData
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDao
import com.sum.shop.room.FavProductDao

class RoomProductRepository(private val favProductDao: FavProductDao,private val basketProductDao: BasketProductDao) {

    val readAllFav: LiveData<List<FavModel>> = favProductDao.readAllFav()
    val readAllBasket: LiveData<List<BasketModel>> =basketProductDao.readAllBasket()

    suspend fun addFav(product: FavModel) {
        favProductDao.addToFav(product)
    }

    suspend fun deleteFav(product: FavModel) {
        favProductDao.deleteFromFav(product)
    }

    suspend fun addBasket(product: BasketModel) {
        basketProductDao.addToBasket(product)
    }

    suspend fun deleteBasket(product: BasketModel) {
        basketProductDao.deleteFromBasket(product)
    }
}