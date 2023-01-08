import android.app.Application

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {
    val readAllFav: LiveData<List<FavModel>>
    val readAllBasket:LiveData<List<BasketModel>>
    private val repository: RoomProductRepository



    val favDao = FavProductDatabase.getDatabase(application).favDao()
    val basketDao = BasketProductDatabase.getDatabase(application).basketDao()

    init {
        repository = RoomProductRepository(favDao,basketDao)
        readAllFav = repository.readAllFav
        readAllBasket = repository.readAllBasket

    }

    fun addFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFav(fav)
            Log.v("addViewModel", fav.productTitle)

        }
    }

    fun addFav(basket: BasketModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBasket(basket)

        }
    }

}