package com.chkan.clientserverappbest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> T) :
    Fragment() {

    private var _binding: T? = null
    protected val b get() = _binding!!
    abstract fun initUI(savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(savedInstanceState)
    }

    protected fun <R> observe(lifecycleScope: LifecycleCoroutineScope, flow: StateFlow<R>, block: (R) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { result ->
                    block.invoke(result)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}