package com.chkan.clientserverappbest.domain

import androidx.paging.PagingSource
import com.chkan.clientserverappbest.data.models.Article

/**
 * @author Dmytro Chkan on 13.12.2022.
 */

interface SearchRepository {
    fun getPagingSource(query:String) : PagingSource<Int, Article>
}