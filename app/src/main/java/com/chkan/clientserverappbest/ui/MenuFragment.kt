package com.chkan.clientserverappbest.ui

import android.os.Bundle
import com.chkan.clientserverappbest.base.BaseFragment
import com.chkan.clientserverappbest.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate)  {

    override fun initUI(savedInstanceState: Bundle?) {

        b.btnList.setOnClickListener {
            (activity as MainActivity).navigateToSimpleList()
        }
        b.btnSearch.setOnClickListener {
            (activity as MainActivity).navigateToSearchList()
        }
    }

}