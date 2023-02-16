import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sum.shop.model.BasketModel
import com.sum.shop.model.FavModel
import com.sum.shop.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository= ProductRepository(application)
    val readAllBasket: LiveData<List<BasketModel>> = repository.readAllBasket


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
        }
    }
}

