package com.example.messengerapp.fragments

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengerapp.R
import com.example.messengerapp.adapters.ChatAdapter
import com.example.messengerapp.databinding.FragmentChatBinding

import com.example.messengerapp.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var database: FirebaseDatabase
    private lateinit var _binding: FragmentChatBinding
    val list=ArrayList<User>()
    val adapter=ChatAdapter(list)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentChatBinding.inflate(inflater, container, false)
        _binding.recyclerChat.layoutManager=LinearLayoutManager(activity)
        database= FirebaseDatabase.getInstance("https://messenger-app-6bebe-default-rtdb.asia-southeast1.firebasedatabase.app/")
        _binding.recyclerChat.adapter=adapter
        val myRef = database.reference.child("Users")
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
               //list.clear()
                for (snapChild in snapshot.children){
                    val user= snapChild.getValue(User::class.java)
                    if (user != null) {
                        list.add(user)
                        Log.e("child", user.userName)
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        adapter.notifyDataSetChanged()
                    }
                }


                Log.e("println", "${list[1]} ${list[0]}")


            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ggg", "Failed to read value.", error.toException())
            }

        })


        return _binding.root
    }


}

