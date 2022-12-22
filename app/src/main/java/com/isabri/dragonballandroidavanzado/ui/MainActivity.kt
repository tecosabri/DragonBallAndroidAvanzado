package com.isabri.dragonballandroidavanzado.ui

import android.app.Fragment
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.CaseMap.Title
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.isabri.dragonballandroidavanzado.R
import com.isabri.dragonballandroidavanzado.databinding.ActivityMainBinding
import com.isabri.dragonballandroidavanzado.ui.heroesList.HeroesListViewModel
import com.isabri.dragonballandroidavanzado.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val heroesListViewModel: HeroesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

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
        /*binding.fab.setOnClickListener { view ->
            getSharedPreferences("NAME", MODE_PRIVATE).edit().clear().apply()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }*/
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

    fun setToolBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun toggleLikeButtonVisibility() {
        when (binding.button.visibility) {
            View.VISIBLE -> binding.button.visibility = View.INVISIBLE
            else -> binding.button.visibility = View.VISIBLE
        }
    }


}