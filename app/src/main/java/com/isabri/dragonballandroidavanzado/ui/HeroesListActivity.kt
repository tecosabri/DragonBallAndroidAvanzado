package com.isabri.dragonballandroidavanzado.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.View
import androidx.activity.viewModels
import com.isabri.dragonballandroidavanzado.R
import com.isabri.dragonballandroidavanzado.databinding.ActivityHeroesListBinding
import com.isabri.dragonballandroidavanzado.ui.heroesList.HeroesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListActivity : AppCompatActivity(), ToolBarActivityWithLikeButton {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHeroesListBinding
    private val heroesListViewModel: HeroesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHeroesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setLikeButtonListener()
        /*binding.fab.setOnClickListener { view ->
            getSharedPreferences("NAME", MODE_PRIVATE).edit().clear().apply()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }*/
    }

    private fun setLikeButtonListener() {
        binding.button.setOnClickListener {
            when (isShowingFavorites()) {
                true -> {
                    heroesListViewModel.getHeroes()
                    binding.button.setImageResource(R.drawable.ic_not_favorite)
                    binding.button.setTag(binding.button.id, "Unpressed")
                }
                false -> {
                    heroesListViewModel.filterLikedHeroes()
                    binding.button.setImageResource(R.drawable.ic_favorite)
                    binding.button.setTag(binding.button.id, "Pressed")
                }
            }

        }
    }

    private fun isShowingFavorites(): Boolean {
        return binding.button.getTag(binding.button.id) == "Pressed"
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        binding.button.setImageResource(R.drawable.ic_not_favorite)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun setToolBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun toggleLikeButtonVisibility() {
        when (binding.button.visibility) {
            View.VISIBLE -> binding.button.visibility = View.INVISIBLE
            else -> binding.button.visibility = View.VISIBLE
        }
    }
}

interface ToolBarActivityWithLikeButton {
    fun setToolBarTitle(title: String);
    fun toggleLikeButtonVisibility();
}