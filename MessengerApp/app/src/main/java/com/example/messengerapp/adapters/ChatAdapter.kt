package com.example.messengerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.databinding.ChatViewBinding

import com.example.messengerapp.models.User
import com.squareup.picasso.Picasso

class ChatAdapter(var lists: ArrayList<User>):RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private lateinit var binding: ChatViewBinding
    inner class ChatViewHolder(var binding: ChatViewBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding= ChatViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val users=lists[position]
        with(holder){
            if(users.profilePic.length>1){
                Picasso.get().load(users.profilePic).placeholder(R.drawable.contact).into(binding.profileImage)
            }

            binding.tVLastMessage.text=users.lastMessage
            binding.tVProfileName.text=users.userName

        }

    }

    override fun getItemCount(): Int {
        return lists.size
    }
}