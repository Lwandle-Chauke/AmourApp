package com.datingapp.amour

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MessageAdapter(context: Context, private val messages: List<Message>) :
    ArrayAdapter<Message>(context, 0, messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val message = messages[position]
        val layoutRes = if (message.isSent) {
            R.layout.item_message_sent
        } else {
            R.layout.item_message_received
        }
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutRes, parent, false)
        val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
        tvMessage.text = message.content
        return view
    }
}
