package com.chkan.clientserverappbest.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chkan.clientserverappbest.data.models.Article
import com.chkan.clientserverappbest.domain.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 13.12.2022.
 */

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel()  {

    private val queryFlow = MutableStateFlow("")

    fun setQuery(query: String) {
        queryFlow.tryEmit(query)
    }

    val pagers: StateFlow<PagingData<Article>> = queryFlow
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(query: String): Pager<Int, Article> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            searchRepository.getPagingSource(query)
        }
    }
}