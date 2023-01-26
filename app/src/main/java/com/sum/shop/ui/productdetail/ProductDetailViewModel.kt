import android.app.Application
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
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


    val favDao = FavProductDatabase.getDatabase(application).favDao()
    val basketDao = BasketProductDatabase.getDatabase(application).basketDao()
    private val repository: RoomProductRepository= RoomProductRepository(favDao, basketDao)

   var kisilerListesi = MutableLiveData<List<FavModel>>()



    init {
        readAllBasket = repository.readAllBasket
        kisilerListesi = repository.kisileriGetir()
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



    fun deleteFromFav(fav: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFav(fav)
            kisileriYukle()
        }
    }


    fun kisileriYukle(){
        repository.getAll()
    }

}

