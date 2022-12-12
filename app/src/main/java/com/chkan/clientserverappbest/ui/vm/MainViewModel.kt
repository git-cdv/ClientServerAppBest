package com.chkan.clientserverappbest.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chkan.clientserverappbest.base.ResultOf
import com.chkan.clientserverappbest.domain.GetDataUseCase
import com.chkan.clientserverappbest.domain.MainRepository
import com.chkan.clientserverappbest.domain.ModelDomain
import com.chkan.clientserverappbest.ui.models.ModelUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
     getDataUseCase: GetDataUseCase,
     mainRepository: MainRepository
) : ViewModel()  {

    val listForPaging: Flow<PagingData<ModelDomain>> = Pager(
        config = PagingConfig(pageSize = 28, //желаемое кол-во загружаемых элементов (обязательный)
            prefetchDistance = 28, //за сколько элементов до границы загруженного контента надо запустить загрузку
            initialLoadSize = 56,//кол-во элементов при первой загрузке (по умолчанию = pageSize*3)
            enablePlaceholders = false,//могут ли показываться плейсхолдер для незагруженных элементов (null прилетает в адаптер иможно обработать как скелетоны)
            maxSize = 777, //макс кол-во элементов, которое может быть в памяти (по умолчанию - НЕ ограниченно, может возникнуть out memory)
            //jumpThreshold = 42 //кол-во элементов на которые надо проскролить от загруженных данных, чтобы перепрыгнуть след страницу и продолжить с "доскроленной"
        //также можно прыгнуть на нужную позицию списка через переопределние getRefreshKey()
        ),
        pagingSourceFactory = { mainRepository.getPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)//держит кеш пока жив скоуп

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