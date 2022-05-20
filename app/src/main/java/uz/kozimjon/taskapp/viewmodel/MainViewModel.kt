package uz.kozimjon.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import uz.kozimjon.taskapp.data_source.CharacterPagingSource
import uz.kozimjon.taskapp.model.CharacterData

class MainViewModel : ViewModel() {
    // Data Source
    fun getListData(): Flow<PagingData<CharacterData>> {
        return Pager(PagingConfig(1), pagingSourceFactory = { CharacterPagingSource() })
            .flow.cachedIn(viewModelScope)
    }
}