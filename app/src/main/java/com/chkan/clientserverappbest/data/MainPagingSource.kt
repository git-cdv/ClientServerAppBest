package com.chkan.clientserverappbest.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chkan.clientserverappbest.data.models.mapToDomain
import com.chkan.clientserverappbest.data.network.MainService
import com.chkan.clientserverappbest.domain.ModelDomain
import retrofit2.HttpException
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 09.12.2022.
 */
class MainPagingSource @Inject constructor(
private val mainService: MainService
) : PagingSource<Int, ModelDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelDomain> {
        return try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize
            Log.d("CHKAN", "pageNumber - $pageNumber, pageSize - $pageSize")
            val result = mainService.getPassengers(pageNumber,pageSize).list.mapToDomain()
            val nextPageNumber = if (result.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(result, prevPageNumber, nextPageNumber)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ModelDomain>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}