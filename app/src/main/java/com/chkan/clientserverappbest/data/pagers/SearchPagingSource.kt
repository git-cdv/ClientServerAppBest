package com.chkan.clientserverappbest.data.pagers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chkan.clientserverappbest.data.models.Article
import com.chkan.clientserverappbest.data.models.toArticle
import com.chkan.clientserverappbest.data.network.SearchService
import retrofit2.HttpException
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 13.12.2022.
 */
class SearchPagingSource @Inject constructor(
    private val searchService: SearchService,
    private val query: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        return try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(SearchService.MAX_PAGE_SIZE)
            val response = searchService.everything(query, pageNumber, pageSize)
            val articles = response.articles.map { it.toArticle() }
            val nextPageNumber = if (articles.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(articles, prevPageNumber, nextPageNumber)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}