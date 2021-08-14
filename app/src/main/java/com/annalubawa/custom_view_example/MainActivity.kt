package com.annalubawa.custom_view_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.annalubawa.custom_view_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}