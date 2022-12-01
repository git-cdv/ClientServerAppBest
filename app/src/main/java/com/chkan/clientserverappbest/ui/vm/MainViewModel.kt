package com.chkan.clientserverappbest.ui.vm

import androidx.lifecycle.ViewModel
import com.chkan.clientserverappbest.ui.models.ModelUi
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
class MainViewModel : ViewModel() {

    var list = MutableStateFlow(listOf<ModelUi>())
        private set

    init {

    }

}