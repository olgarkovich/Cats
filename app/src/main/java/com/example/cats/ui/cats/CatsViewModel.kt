package com.example.cats.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.cats.model.Cat
import com.example.cats.model.CatImage
import com.example.cats.network.CatsPagingSource
import com.example.cats.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CatsViewModel : ViewModel() {

//    private var _result: MutableSharedFlow<List<Cat>> = MutableSharedFlow()
//    val result: SharedFlow<List<Cat>> get() = _result

//    fun getCats() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                _result.emit(NetworkModule.catsApiService.getCats(page = 0, limit = 30))
//            } catch (e: Exception) {
//                _result.emit(emptyList())
//            }
//        }
//    }

    fun getCats(): Flow<PagingData<CatImage>> {
        return Pager (config = PagingConfig(pageSize = PAGE_SIZE, maxSize = MAX_SIZE),
            pagingSourceFactory = {CatsPagingSource(NetworkModule.catsApiService)}).flow.cachedIn(viewModelScope)
    }

    private companion object {
        private const val PAGE_SIZE = 10
        private const val MAX_SIZE = 100
    }
}
