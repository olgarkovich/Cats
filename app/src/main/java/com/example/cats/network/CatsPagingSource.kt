package com.example.cats.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cats.model.CatImage
import java.lang.Exception

class CatsPagingSource(private val catsApi: CatsApi): PagingSource<Int, CatImage>() {

    override fun getRefreshKey(state: PagingState<Int, CatImage>): Int? {

        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatImage> {
        return try {
            val pageNumber: Int = params.key ?: FIRST_PAGE_INDEX
            val pageSize: Int = if (params.loadSize < PAGE_SIZE) params.loadSize else PAGE_SIZE

            val result = catsApi.getCats(page = pageNumber, limit = pageSize)
            val nextPageNumber = if (result.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(result, prevPageNumber, nextPageNumber)
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        private const val FIRST_PAGE_INDEX = 1
        private const val PAGE_SIZE = 10
    }

}
