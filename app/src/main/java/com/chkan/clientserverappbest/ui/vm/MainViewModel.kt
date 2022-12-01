package com.chkan.clientserverappbest.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chkan.clientserverappbest.base.ResultOf
import com.chkan.clientserverappbest.domain.GetDataUseCase
import com.chkan.clientserverappbest.ui.models.ModelUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
     getDataUseCase: GetDataUseCase
) : ViewModel()  {

    var list = MutableStateFlow(listOf<ModelUi>())
        private set

    init {
        getDataUseCase.run(
            scope = viewModelScope,
            result = ResultOf(
                onSuccess = {
                    list.value = it
                },
                onLoading = {},
                onError = {
                    Log.d("CHKAN", "ERROR: ${it.localizedMessage}")
                }
            )
        )
    }

}