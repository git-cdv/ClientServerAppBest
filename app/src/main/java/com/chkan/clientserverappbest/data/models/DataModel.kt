package com.chkan.clientserverappbest.data.models

import com.chkan.clientserverappbest.domain.ModelDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Dmytro Chkan on 01.12.2022.
 */

@Serializable
data class DataModel(
    @SerialName("data")
    val list: List<Data>,
    @SerialName("limit")
    val limit: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("total")
    val total: Int
)

@Serializable
data class Data(
    @SerialName("firstName")
    val firstName: String,
    @SerialName("id")
    val id: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("picture")
    val picture: String
)

fun DataModel.mapToDomain() : List<ModelDomain> =
    this.list.map {
        ModelDomain (
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            picture = it.picture
        )
    }

fun List<Data>.mapToDomain() : List<ModelDomain> =
    this.map {
        ModelDomain (
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            picture = it.picture
        )
    }