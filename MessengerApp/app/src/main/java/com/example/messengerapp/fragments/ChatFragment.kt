package com.example.messengerapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messengerapp.ChatDetailsActivity
import com.example.messengerapp.R
import com.example.messengerapp.adapters.ChatAdapter
import com.example.messengerapp.adapters.SingleClick
import com.example.messengerapp.databinding.FragmentChatBinding
import com.example.messengerapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChatFragment : Fragment(), SingleClick.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var database: FirebaseDatabase
    private lateinit var _binding: FragmentChatBinding
    private lateinit var auth:FirebaseAuth
    val list = ArrayList<User>()
    val adapter = ChatAdapter(list)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        _binding.recyclerChat.layoutManager = LinearLayoutManager(activity)
        database =
            FirebaseDatabase.getInstance("https://messenger-app-6bebe-default-rtdb.asia-southeast1.firebasedatabase.app/")
        _binding.recyclerChat.adapter = adapter
        auth=Firebase.auth
        _binding.recyclerChat.addOnItemTouchListener(
            SingleClick(
                context,
                _binding.recyclerChat,
                this
            )
        )
        val myRef = database.reference.child("Users")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //list.clear()
                for (snapChild in snapshot.children) {
                    val user = snapChild.getValue(User::class.java)
                    if (user != null) {
                        if(auth.currentUser?.uid.toString() == user.userId){

                        }
                        else{
                            list.add(user)
                        }


                    }

                }
                GlobalScope.launch(Dispatchers.Main) {
                    //To Do ("Background processing...")

                    adapter.notifyDataSetChanged()
                }



            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ggg", "Failed to read value.", error.toException())
            }

        })


        return _binding.root
    }

    override fun onItemClick(view: View, position: Int) {
      Log.e("ids"," shgsgkgsgss ${list[position].userId}")
        Log.e("idse"," shgsgkgsgss ${list[position].userName}")
        val intent = Intent(activity?.baseContext, ChatDetailsActivity::class.java)

        intent.putExtra("UserId",list[position].userId)
        intent.putExtra("UserName",list[position].userName)
        intent.putExtra("ProfilePic",list[position].profilePic)
        activity?.startActivity(intent)

    }

    override fun onItemLongClick(view: View, position: Int) {
        val alert = AlertDialog.Builder(context)
        alert.apply {
            setCancelable(true)
            setTitle("Delete The Task")
            setIcon(R.drawable.splashscreen)
            setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

            })
            setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
        }
        alert.show()

    }


}

