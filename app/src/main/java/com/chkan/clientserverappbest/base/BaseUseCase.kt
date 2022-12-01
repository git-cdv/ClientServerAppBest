package com.chkan.clientserverappbest.base

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
abstract class BaseUseCase<RESULT, PARAMS> : UseCase<RESULT, PARAMS> {

    fun run(
        scope: CoroutineScope,
        result: ResultOf<RESULT>,
        params: PARAMS? = null
    ) {
        scope.launch {
            withContext(Dispatchers.Main) {
                result.onLoading?.invoke(true)
                try {
                    val resultOfWork = remoteWork(params)
                    result.onSuccess?.invoke(resultOfWork)
                    result.onLoading?.invoke(false)
                } catch (e: Throwable) {
                    Log.e("Error", e.localizedMessage ?: "")
                    result.onError?.invoke(e)
                    result.onLoading?.invoke(false)
                }
            }
        }
    }

}

class ResultOf<T>(
    val onSuccess: ((T) -> Unit)? = null,
    val onLoading: ((Boolean) -> Unit)? = null,
    val onError: ((Throwable) -> Unit)? = null
)

interface UseCase<RESULT, PARAMS> {
    suspend fun remoteWork(params: PARAMS?): RESULT
}