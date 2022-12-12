package com.chkan.clientserverappbest.domain

import com.chkan.clientserverappbest.base.BaseUseCase
import com.chkan.clientserverappbest.ui.models.ModelUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 01.12.2022.
 */

class GetDataUseCase @Inject constructor(
    private val mainRepository: MainRepository,
) : BaseUseCase<List<ModelUi>, GetDataUseCase.Params>() {

    override suspend fun remoteWork(params: Params?): List<ModelUi> {
        return withContext(Dispatchers.IO) {
            mainRepository.getData(
                page = params?.page ?:0,
                size = params?.size ?:20
            ).mapToUi()
        }
    }

    class Params(
        val page: Int?,
        val size: Int?
    )
}
