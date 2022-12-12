package com.chkan.clientserverappbest.domain

import androidx.paging.PagingSource

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
interface MainRepository {
    suspend fun getData(page: Int, size:Int) : List<ModelDomain>
    fun getPagingSource() : PagingSource<Int, ModelDomain>
}