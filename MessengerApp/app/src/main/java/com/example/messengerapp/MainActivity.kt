package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.messengerapp.adapters.ViewPagerAdapter
import com.example.messengerapp.databinding.ActivityMainBinding
import com.example.messengerapp.fragments.CallFragment
import com.example.messengerapp.fragments.ChatFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var auth:FirebaseAuth
    private val fragList=ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMainMenu)
        auth=Firebase.auth
        fragList.add(CallFragment())
        fragList.add(ChatFragment())
        binding.ViewPagerMain.adapter=ViewPagerAdapter(fragList,this)
        val arr= arrayOf("Chat","Call","Status")
        TabLayoutMediator(binding.tabLayoutMain, binding.ViewPagerMain) { tab, position ->
            tab.text = arr[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemLogout->{
                logOut()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        auth.signOut()
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}