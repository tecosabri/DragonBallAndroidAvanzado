package com.isabri.dragonballandroidavanzado.ui.heroesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.isabri.dragonballandroidavanzado.databinding.FragmentHeroesListBinding
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import com.isabri.dragonballandroidavanzado.ui.commons.HeroesListAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HeroesListFragment : Fragment() {

    private var _binding: FragmentHeroesListBinding? = null
    private val adapter = HeroesListAdapter()
    private val heroesListViewModel: HeroesListViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val heroes = listOf<String>()

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

            heroesListViewModel.heroes.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            heroesListViewModel.getHeroes()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}