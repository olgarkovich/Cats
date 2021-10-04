package com.example.cats.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.cats.model.CatImage
import com.example.cats.network.CatsPagingSource
import com.example.cats.network.NetworkModule
import kotlinx.coroutines.flow.Flow

class CatsViewModel : ViewModel() {

    fun getCats(): Flow<PagingData<CatImage>> {
        return Pager (config = PagingConfig(pageSize = PAGE_SIZE, maxSize = MAX_SIZE),
            pagingSourceFactory = {CatsPagingSource(NetworkModule.catsApiService)}).flow.cachedIn(viewModelScope)
    }

    private companion object {
        private const val PAGE_SIZE = 10
        private const val MAX_SIZE = 100
    }
}
