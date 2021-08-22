package com.annalubawa.custom_view_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.annalubawa.custom_view_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.firstButton.setOnClickListener {
            navController.navigate(R.id.action_global_firstFragment)
        }

        binding.secondButton.setOnClickListener {
            navController.navigate(R.id.action_global_secondFragment)
        }
    }
}