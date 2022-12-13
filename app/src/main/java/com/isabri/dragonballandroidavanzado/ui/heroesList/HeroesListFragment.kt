package com.isabri.dragonballandroidavanzado.ui.heroesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.isabri.dragonballandroidavanzado.databinding.FragmentHeroesListBinding
import com.isabri.dragonballandroidavanzado.ui.commons.HeroesListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HeroesListFragment : Fragment() {

    private var _binding: FragmentHeroesListBinding? = null
    private val binding get() = _binding!!

    private val adapter = HeroesListAdapter {
        println("hey")
        findNavController().navigate(HeroesListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(it))
    }
    private val heroesListViewModel: HeroesListViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHeroesListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            heroesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            heroesList.adapter = adapter

            heroesListViewModel.state.observe(viewLifecycleOwner) { heroesListState ->
                when (heroesListState) {
                    is HeroesListState.Failure -> Toast.makeText(requireContext(), heroesListState.error, Toast.LENGTH_SHORT).show()
                    is HeroesListState.Success -> adapter.submitList(heroesListState.heroes)
                }
            }
            heroesListViewModel.getHeroes()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}