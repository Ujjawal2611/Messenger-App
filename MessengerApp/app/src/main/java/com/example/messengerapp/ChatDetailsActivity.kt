package com.example.messengerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messengerapp.databinding.ActivityChatDetailsBinding

class ChatDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChatDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}