package com.alexym.android.zamiboda

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alexym.android.zamiboda.databinding.ActivityMainBinding
import com.alexym.android.zamiboda.ui.startAnimation
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_location
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val animation = AnimationUtils.loadAnimation(this, R.anim.circle_explotion_anim).apply {
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
        }
        val anim_fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out_circle).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        }

        binding.appBarMain.contentMain.fab.setOnClickListener { view ->
            binding.appBarMain.contentMain.fab.isVisible = false
            binding.appBarMain.contentMain.circleV.isVisible = true
            binding.appBarMain.contentMain.circleV.startAnimation(animation) {
                binding.appBarMain.contentMain.fab.isVisible = true
                binding.appBarMain.contentMain.circleV.isVisible = false
                binding.appBarMain.contentMain.circleV.startAnimation(anim_fade_out) {
                    val gmmIntentUri =
                        Uri.parse("google.navigation:q=Thesan+Jardín+Temixco+Morelos")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}