package com.example.chatapp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatBoatBinding
import com.example.chatapp.ui.adapter.ChatAdapter
import com.example.chatapp.ui.viewmodel.ChatBotViewModel

class ChatBoatActivity : AppCompatActivity() {


    private lateinit var binding: ActivityChatBoatBinding
    private var adapter: ChatAdapter?=null
    private lateinit var viewModel: ChatBotViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBoatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[ChatBotViewModel::class.java]

        viewModel.chatBotLiveData.observe(this){chatBotList->
            adapter = ChatAdapter(chatBotList) { selectedBot ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("botId", selectedBot.id)
                startActivity(intent)
            }
            binding.recyclerView.adapter = adapter
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }
}