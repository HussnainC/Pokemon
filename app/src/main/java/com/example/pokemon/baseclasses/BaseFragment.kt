package com.example.pokemon.baseclasses

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokemon.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment(val layoutId: Int) : Fragment(layoutId) {
    protected val mViewModel: MainViewModel by activityViewModels()
}