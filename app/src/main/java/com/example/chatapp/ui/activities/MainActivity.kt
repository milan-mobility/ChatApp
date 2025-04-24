package com.example.chatapp.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.ui.adapter.ChatMessageAdapter
import com.example.chatapp.ui.viewmodel.ChatViewModel
import com.example.chatapp.utils.NetworkConnection

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var adapter: ChatMessageAdapter

    private lateinit var binding: ActivityMainBinding

    private var botId: String = ""

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            botId = intent.getStringExtra("botId").toString()
        }


        setViews()
        onClick()
        setObserver()
    }

    private fun setViews() {
        val networkConnection = NetworkConnection(this@MainActivity)
        networkConnection.observe(this) { isConnected ->
            if (!isConnected) {
                Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        viewModel.assignBotId(botId)

        viewModel.connectSocket()
        adapter = ChatMessageAdapter(mutableListOf())
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = adapter
    }

    private fun onClick() {
        binding.sendButton.setOnClickListener {
            val msg = binding.messageEditText.text.toString().trim()
            if (msg.isNotEmpty()) {
                viewModel.sendMessage(msg)
                binding.messageEditText.text?.clear()
            }
        }
    }

    private fun setObserver() {
        viewModel.messages.observe(this) { msgs ->
            if (msgs.isEmpty()) {
                binding.txtNoMsg.visibility = View.VISIBLE
                binding.chatRecyclerView.visibility = View.GONE
            } else {
                binding.txtNoMsg.visibility = View.GONE
                binding.chatRecyclerView.visibility = View.VISIBLE
                adapter.updateMessages(msgs)
                binding.chatRecyclerView.scrollToPosition(msgs.size - 1)
            }
        }
    }
}