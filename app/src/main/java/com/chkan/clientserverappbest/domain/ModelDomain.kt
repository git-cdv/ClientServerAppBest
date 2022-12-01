package com.chkan.clientserverappbest.domain

import com.chkan.clientserverappbest.ui.models.ModelUi

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
data class ModelDomain(val id: String, val firstName: String, val lastName: String, val picture: String, val total: Int)

fun List<ModelDomain>.mapToUi() : List<ModelUi> =
    this.map {
        ModelUi (
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            picture = it.picture
        )
    }