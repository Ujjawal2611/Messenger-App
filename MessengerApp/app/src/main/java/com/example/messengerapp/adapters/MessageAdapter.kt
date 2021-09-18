package com.example.messengerapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.models.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class MessageAdapter(var list: ArrayList<Message>, val context: Context) :
    RecyclerView.Adapter<MessageViewHolder>() {


    private var auth = Firebase.auth
    inner class ReceiveViewHolder(view: View) : MessageViewHolder(view) {

        var um=itemView.findViewById<TextView>(R.id.tvRecMessage)
        var ut=itemView.findViewById<TextView>(R.id.tvRecTime)
        override fun bind(message: Message) {
            um.text = message.uMessage
            val time = SimpleDateFormat("hh:mm").format(message.uTime)
            ut.text = time

        }
    }

    inner class SenderViewHolder (view: View) : MessageViewHolder(view) {
        var rm=itemView.findViewById<TextView>(R.id.tvSendMessage)
        var mn=itemView.findViewById<TextView>(R.id.tvSendTime)
        override fun bind(message: Message) {
              rm.text = message.uMessage
                val time = SimpleDateFormat("hh:mm").format(message.uTime)
                mn.text = time

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.message_send, parent, false)
        val view1=LayoutInflater.from(context).inflate(R.layout.message_receive, parent, false)
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            SenderViewHolder(view)

        }
        else {
            ReceiveViewHolder(view1)

        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = list[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = list[position]

        return if (auth.currentUser?.uid == message.userId) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

}

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}