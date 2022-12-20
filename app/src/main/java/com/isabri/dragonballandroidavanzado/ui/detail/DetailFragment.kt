package com.isabri.dragonballandroidavanzado.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.isabri.dragonballandroidavanzado.R
import com.isabri.dragonballandroidavanzado.databinding.FragmentDetailBinding
import com.isabri.dragonballandroidavanzado.data.dataState.HeroesListState
import com.isabri.dragonballandroidavanzado.domain.models.Hero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var map: GoogleMap



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getHeroes(args.hero.name)
        observeHeroesListState()
        setUpMap()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeHeroesListState() {
        detailViewModel.state.observe(viewLifecycleOwner) { heroesListState ->
            when(heroesListState) {
                is HeroesListState.Failure -> Toast.makeText(requireContext(), heroesListState.error, Toast.LENGTH_SHORT).show()
                is HeroesListState.Success -> {
                    val hero = heroesListState.heroes.first()
                    setHeroInfo(hero)
                    setHeroLocations(hero)
                    zoomToFirstPosition(hero)
                    setListeners()
                }
            }
        }
    }

    private fun setUpMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
    }

    private fun setHeroInfo(hero: Hero) {
        setHeroName(hero)
        setHeroFavorite(hero)
    }

    private fun setHeroName(hero: Hero) {
        binding.heroName.text = hero.name
    }

    private fun setHeroFavorite(hero: Hero) {
        when (hero.favorite) {
            true -> binding.isFavorite.setImageResource(R.drawable.ic_favorite)
            false -> binding.isFavorite.setImageResource(R.drawable.ic_not_favorite)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setHeroLocations(hero: Hero) {
        detailViewModel.getMarkers(hero).forEach { map.addMarker(it) }
    }

    private fun zoomToFirstPosition(hero: Hero) {
        val firstHeroLocation = hero.locations?.first()
        firstHeroLocation?.apply {
            val position = LatLng(firstHeroLocation.latitude.toDouble(), firstHeroLocation.longitude.toDouble())
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 6F))
        }
    }

    private fun setListeners() {
        with (binding) {
            isFavorite.setOnClickListener {
                detailViewModel.toggleFavorite()
            }
        }
    }
}