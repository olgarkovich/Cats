package com.example.cats.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cats.model.Cat
import com.example.cats.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CatsViewModel : ViewModel() {

    private var _result: MutableSharedFlow<List<Cat>> = MutableSharedFlow()
    val result: SharedFlow<List<Cat>> get() = _result

    fun getCats() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _result.emit(NetworkModule.catsApiService.getCats())
            } catch (e: Exception) {
                _result.emit(emptyList())
            }
        }
    }
}
