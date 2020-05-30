package com.demo.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.demo.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Binding reference
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textId.setOnClickListener {
            Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show()
        }
    }
}
