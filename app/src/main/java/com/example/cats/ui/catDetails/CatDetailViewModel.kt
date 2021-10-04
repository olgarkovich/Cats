package com.example.cats.ui.catDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cats.model.CatImage
import com.example.cats.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CatDetailViewModel : ViewModel() {

    private var _result: MutableSharedFlow<CatImage> = MutableSharedFlow()
    val result: SharedFlow<CatImage> get() = _result

    fun getCatDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _result.emit(NetworkModule.catsApiService.getCatById(id = id))
            } catch (e: Exception) {
                _result.emit(CatImage("", ""))
            }
        }
    }
}
