package com.example.pokemon.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pokemon.R
import com.example.pokemon.baseclasses.BaseFragment
import com.example.pokemon.databinding.ActivityQuizBinding
import com.example.pokemon.quizdataclasses.QuizDataClass
import com.example.pokemon.resultclasses.Result
import com.example.pokemon.resultclasses.ResultDataClass
import com.example.pokemon.sealedclasses.DataStates
import com.example.pokemon.utils.setImage
import com.example.pokemon.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class QuizFragment : BaseFragment(R.layout.activity_quiz) {
    private lateinit var binding: ActivityQuizBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ActivityQuizBinding.bind(view)
        mViewModel.score.observe(viewLifecycleOwner) {
            binding.mHeaderLayout.tvScore.text = "Your score is: ${it.score}"
        }
        binding.mHeaderLayout.tvHeader.text="Pokemon Quiz"
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.pokemones.collectLatest {
                when (it) {
                    is DataStates.Initial -> {}
                    is DataStates.Loading -> {

                    }
                    is DataStates.Success<*> -> {
                        if (savedInstanceState == null) {
                            buildQuiz((it.value as ResultDataClass?)?.results ?: listOf())
                        }

                    }
                    is DataStates.Error -> {
                        requireContext().showToast(it.message)
                    }
                }
            }
        }

        mViewModel.quizData.observe(viewLifecycleOwner) {
            bindQuizData(it)
        }


        binding.btnSubmit.setOnClickListener {
            val radioButton =
                view.findViewById<RadioButton>(binding.radioOptions.checkedRadioButtonId)
            onOptionSelect(radioButton.text.toString())
        }


    }

    private fun bindQuizData(quizData: QuizDataClass?) {
        with(binding) {
            val kk = quizData?.result?.url?.split('/')
            val end = kk?.get(kk.lastIndex - 1)
            val uu = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                end
            }.png"
            pokiImage.setImage(uu)
            if (radioOptions.childCount != 0) {
                radioOptions.removeAllViews()
            }
            quizData?.optionsList?.forEach {
                radioOptions.addView(getRadioButton(it))
            }
            radioOptions.invalidate()
        }
    }

    private fun getRadioButton(text: String): RadioButton {
        val rn = (1000..5000).random()
        val radioButton = RadioButton(requireContext())
        radioButton.id = rn
        radioButton.text = text
        return radioButton
    }

    private fun buildQuiz(list: List<Result>) {
        val rand = (0..list.lastIndex).random()
        val optionsList: ArrayList<String> = ArrayList()
        optionsList.add(list[rand].name)
        for (i in 1 until 4) {
            val newRand = (0..list.lastIndex).random()
            optionsList.add(list[newRand].name)
        }
        optionsList.shuffle()
        val data = QuizDataClass(list[rand], optionsList)
        mViewModel.setQuiz(data)
    }

    @SuppressLint("ResourceType")
    private fun onOptionSelect(optionText: String) {
        if (mViewModel.quizData.value?.result?.name == optionText) {
            showToast(
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.correcttoastlayout, null, false)
            )

            mViewModel.addScore(mViewModel.score.value?.score?.plus(1))
            buildQuiz(
                (((mViewModel.pokemones.value as DataStates.Success<*>).value) as ResultDataClass?)?.results
                    ?: listOf()
            )

        } else {
            showToast(
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.incorrecttoastlayout, null, false)
            )


        }
    }


    private fun showToast(view: View) {
        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.BOTTOM, 0, 30)
        toast.view = view
        toast.show()
    }


}