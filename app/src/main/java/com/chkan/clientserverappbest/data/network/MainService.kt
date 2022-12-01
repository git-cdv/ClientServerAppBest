package com.chkan.clientserverappbest.data.network

import com.chkan.clientserverappbest.data.models.DataModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
interface MainService {

    @GET(API_GET_PASSENGERS)
    suspend fun getPassengers(@Query("page") page: Int,
                              @Query("limit") limit: Int): DataModel

    companion object {
        const val API_GET_PASSENGERS = "/data/v1/user"
    }
}