package com.chkan.clientserverappbest.data.repos

import com.chkan.clientserverappbest.data.network.SearchService
import com.chkan.clientserverappbest.data.pagers.SearchPagingSource
import com.chkan.clientserverappbest.domain.SearchRepository
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 13.12.2022.
 */
class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService
): SearchRepository {
    override fun getPagingSource(query:String) = SearchPagingSource(searchService, query)
}