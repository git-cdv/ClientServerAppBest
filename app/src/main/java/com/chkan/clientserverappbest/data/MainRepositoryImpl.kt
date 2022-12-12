package com.chkan.clientserverappbest.data

import com.chkan.clientserverappbest.data.models.mapToDomain
import com.chkan.clientserverappbest.data.network.MainService
import com.chkan.clientserverappbest.domain.MainRepository
import com.chkan.clientserverappbest.domain.ModelDomain
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService
): MainRepository {
    override suspend fun getData(page: Int, size:Int): List<ModelDomain> {
        return mainService.getPassengers(page,size).mapToDomain()
    }
    override fun getPagingSource() = MainPagingSource(mainService)
}