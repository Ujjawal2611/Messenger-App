package com.example.messengerapp

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengerapp.adapters.MessageAdapter
import com.example.messengerapp.databinding.ActivityChatDetailsBinding
import com.example.messengerapp.models.Message
import com.example.messengerapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class ChatDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChatDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    var arrayList = ArrayList<Message>()
    private lateinit var recId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //  setSupportActionBar(binding.toolbarChatDetails)
        val uName = intent.getStringExtra("UserName")
        binding.tvUserName.text = uName
        val uProfile = intent.getStringExtra("ProfilePic")
        if (uProfile != null && uProfile.length > 1) {
            Picasso.get().load(uProfile).placeholder(R.drawable.contact).into(binding.iVProfilePic)
        }
        database= FirebaseDatabase.getInstance("https://messenger-app-6bebe-default-rtdb.asia-southeast1.firebasedatabase.app/")
        recId = intent.getStringExtra("UserId").toString()
        auth = Firebase.auth
        val sid = auth.currentUser?.uid
        binding.ivBackButton.setOnClickListener(this)

        val adapter=MessageAdapter(arrayList,this)
        binding.rVChat.layoutManager=LinearLayoutManager(this)
        binding.rVChat.adapter=adapter
        binding.ivSend.setOnClickListener(this)
        database.getReference("chat").child(sid+recId).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (snapChild in snapshot.children){
                    val message = snapChild.getValue(Message::class.java)
                    if(message!=null){
                        arrayList.add(message)
                        Log.e("arraylist","${message.uMessage}")
                    }
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {
              //  ("Not yet implemented")
            }

        })



    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButton -> {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.ivSend ->{
                sendMessage()


            }
        }
    }

    private fun sendMessage() {
        val message=binding.edTextMessage.text.toString()
        val sid=auth.currentUser?.uid
        val time=Date().time
        var model=Message(sid.toString(),message,time)
        binding.edTextMessage.text.clear()
        val senderRoom=sid+recId
        Log.e("rid","  $recId   --->   $sid")
        val receiverRoom=recId+sid
        database.reference.child("chat").child(senderRoom).push().setValue(model).addOnSuccessListener {

        }
        database.reference.child("chat").child(receiverRoom).push().setValue(model).addOnSuccessListener {  }
    }
}