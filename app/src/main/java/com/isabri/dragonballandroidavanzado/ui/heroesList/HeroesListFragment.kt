package com.isabri.dragonballandroidavanzado.ui.heroesList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.databinding.FragmentHeroesListBinding
import com.isabri.dragonballandroidavanzado.ui.MainActivity
import com.isabri.dragonballandroidavanzado.ui.commons.HeroesListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListFragment : Fragment() {

    private var _binding: FragmentHeroesListBinding? = null
    private val binding get() = _binding!!
    private val heroesListViewModel: HeroesListViewModel by activityViewModels()
    private val adapter = HeroesListAdapter {
        findNavController().navigate(HeroesListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(it))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHeroesListBinding.inflate(inflater, container, false)
        setTitle()
        return binding.root
    }

    private fun setTitle() {
        (activity as MainActivity).setToolBarTitle("Dragon Ball Heroes!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            heroesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            heroesList.adapter = adapter
            observeHeroesListState()
            heroesListViewModel.getHeroes()
        }
    }

    private fun observeHeroesListState() {
        heroesListViewModel.state.observe(viewLifecycleOwner) { heroesListState ->
            when (heroesListState) {
                is HeroesListState.Failure -> Toast.makeText(requireContext(), heroesListState.error, Toast.LENGTH_SHORT).show()
                is HeroesListState.Success -> adapter.submitList(heroesListState.heroes)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}