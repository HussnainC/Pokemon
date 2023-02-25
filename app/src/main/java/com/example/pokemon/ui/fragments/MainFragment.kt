package com.example.pokemon.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.R
import com.example.pokemon.adaptors.PokemonRecyclerAdaptor
import com.example.pokemon.baseclasses.BaseFragment
import com.example.pokemon.databinding.FragmentMainBinding
import com.example.pokemon.resultclasses.ResultDataClass
import com.example.pokemon.sealedclasses.DataStates
import com.example.pokemon.ui.activities.PokemonInfoActivity
import com.example.pokemon.utils.beGone
import com.example.pokemon.utils.beVisible
import com.example.pokemon.utils.showToast
import com.example.pokemon.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private var mAdaptor: PokemonRecyclerAdaptor? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        if (savedInstanceState == null) {
            loadPokemons()
            loadScore()
        }
        with(binding) {
            root.setOnRefreshListener {
                loadPokemons()
            }
            mHeaderLayout.tvHeader.text = getString(R.string.app_name)
            btnPlay.setOnClickListener {
                findNavController().navigate(R.id.quizFragment)
            }
            btnSearch.setOnClickListener {
                findNavController().navigate(R.id.searchFragment)
            }
        }
        initRecycler()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.pokemones.collectLatest {
                when (it) {
                    is DataStates.Initial -> {}
                    is DataStates.Loading -> {
                        showLoading()
                        hideRecycler()
                    }
                    is DataStates.Success<*> -> {
                        hideLoading()
                        showRecycler()
                        mAdaptor?.submitData((it.value as ResultDataClass?)?.results ?: listOf())
                    }
                    is DataStates.Error -> {
                        hideLoading()
                        hideRecycler()
                        requireContext().showToast(it.message)
                    }
                }
            }
        }
        mViewModel.score.observe(viewLifecycleOwner) {
            binding.mHeaderLayout.tvScore.text = "Your score is: ${it.score}"
        }
    }

    private fun loadScore() {
        mViewModel.loadMyScore()
    }

    private fun initRecycler() {
        mAdaptor = PokemonRecyclerAdaptor(requireContext())
        binding.mRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdaptor
        }
        mAdaptor?.onItemClick = { result ->
            requireActivity().startNewActivity<PokemonInfoActivity>(false) {
                it.putExtra("name", result.name)
            }
        }


    }

    private fun showLoading() {
        binding.root.isRefreshing = true
    }

    private fun hideLoading() {
        binding.root.isRefreshing = false
    }

    private fun showRecycler() {
        binding.mRecycler.beVisible()

    }

    private fun hideRecycler() {
        binding.mRecycler.beGone()
    }

    private fun loadPokemons() {
        mViewModel.loadPokemons()
    }

}