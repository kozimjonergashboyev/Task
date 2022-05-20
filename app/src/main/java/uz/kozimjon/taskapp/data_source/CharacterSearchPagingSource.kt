package uz.kozimjon.taskapp.data_source

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.kozimjon.taskapp.api.RetrofitClient
import uz.kozimjon.taskapp.model.CharacterData

class CharacterSearchPagingSource(
    private val userSearch: String,
    private val localExceptionCallback: (LocalException) -> Unit
) : PagingSource<Int, CharacterData>() {

    sealed class LocalException(val title: String): Exception() {
        object EmptySearch : LocalException(title = "Izlashni boshlashingiz mumkin")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {

        if (userSearch.isEmpty()) {
            val exception = LocalException.EmptySearch
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = RetrofitClient.instance.getCharactersPage(userSearch, nextPage)

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

    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}