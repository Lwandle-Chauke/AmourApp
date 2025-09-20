package com.datingapp.amour

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MessagingActivity : AppCompatActivity() {

    private lateinit var lvMessages: ListView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var tvMatchName: TextView

    private val messages = mutableListOf<Message>()
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        lvMessages = findViewById(R.id.lvMessages)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        tvMatchName = findViewById(R.id.tvMatchName)

        val matchName = intent.getStringExtra("matchName") ?: "Alex"
        tvMatchName.text = matchName

        messages.add(Message("Hey, how’s it going?", false))
        messages.add(Message("Pretty good, how about you?", true))

        adapter = MessageAdapter(this, messages)
        lvMessages.adapter = adapter

        btnSend.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val text = etMessage.text.toString().trim()
        if (text.isNotEmpty()) {
            messages.add(Message(text, true))
            adapter.notifyDataSetChanged()
            lvMessages.setSelection(messages.size - 1)
            etMessage.text.clear()
        }
    }
}

data class Message(val content: String, val isSent: Boolean)
