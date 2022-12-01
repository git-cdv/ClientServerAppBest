package com.chkan.clientserverappbest.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.chkan.clientserverappbest.base.BaseFragment
import com.chkan.clientserverappbest.databinding.FragmentMainBinding
import com.chkan.clientserverappbest.ui.vm.MainViewModel

/**
 * @author Dmytro Chkan on 01.12.2022.
 */

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun initUI(savedInstanceState: Bundle?) {
        initViewModel()
    }

    private fun initViewModel() = with(viewModel){
        observe(lifecycleScope,list){

        }
    }
}