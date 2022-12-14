package com.chkan.clientserverappbest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.chkan.clientserverappbest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            add(R.id.container, MenuFragment())
        }
    }

    fun navigateToSimpleList(){
        supportFragmentManager.commit {
            replace(R.id.container, MainFragment())
        }
    }

    fun navigateToSearchList(){
        supportFragmentManager.commit {
            replace(R.id.container, SearchFragment())
        }
    }
}