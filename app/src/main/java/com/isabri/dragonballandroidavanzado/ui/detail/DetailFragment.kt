package com.isabri.dragonballandroidavanzado.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.isabri.dragonballandroidavanzado.R
import com.isabri.dragonballandroidavanzado.databinding.FragmentDetailBinding
import com.isabri.dragonballandroidavanzado.ui.heroesList.HeroesListState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getHeroes(args.hero.name)
        observeHeroesListState()
    }

    private fun observeHeroesListState() {
        detailViewModel.state.observe(viewLifecycleOwner) { heroesListState ->
            when(heroesListState) {
                is HeroesListState.Failure -> Toast.makeText(requireContext(), heroesListState.error, Toast.LENGTH_SHORT).show()
                is HeroesListState.Success -> {
                    val hero = heroesListState.heroes.first()
                    binding.heroPhoto.load(hero.photo)
                    binding.heroName.text = hero.name
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}