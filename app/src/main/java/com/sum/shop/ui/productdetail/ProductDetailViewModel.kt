import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.room.BasketProductDatabase
import com.sum.shop.room.FavProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {
    val readAllBasket: LiveData<List<BasketModel>>
    private val repository: RoomProductRepository

    private val _isFav = MutableLiveData<Boolean>()
    val isFav: LiveData<Boolean> = _isFav

    val favDao = FavProductDatabase.getDatabase(application).favDao()
    val basketDao = BasketProductDatabase.getDatabase(application).basketDao()

    init {
        repository = RoomProductRepository(favDao, basketDao)
        readAllBasket = repository.readAllBasket
        _isFav.value = FavModel(1, "", "", "", "").isFav
    }

    fun addToFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFav(fav)
        }
    }

fun addToBasket(basket: BasketModel) {
    viewModelScope.launch(Dispatchers.IO) {
        repository.addToBasket(basket)
    }
}

    fun updateFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFav(fav)
        }
    }

    fun deleteFromFav(favId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFav(favId)
            kisileriYukle()
        }
    }


    fun kisileriYukle() {
        repository.getAll()
    }
}

