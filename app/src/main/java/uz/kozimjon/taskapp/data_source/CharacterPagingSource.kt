package uz.kozimjon.taskapp.data_source

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.kozimjon.taskapp.api.RetrofitClient
import uz.kozimjon.taskapp.model.CharacterData

class CharacterPagingSource: PagingSource<Int, CharacterData>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = RetrofitClient.instance.getDataFromAPI(nextPage)

            var nextPageNumber: Int? = null
            if(response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            var prevPageNumber: Int? = null
            if(response.info.prev != null) {
                val uri = Uri.parse(response.info.prev)
                val prevPageQuery = uri.getQueryParameter("page")

                prevPageNumber = prevPageQuery?.toInt()
            }

            LoadResult.Page(data = response.results,
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber)
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}