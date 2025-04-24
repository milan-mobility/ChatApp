package com.example.chatapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.data.model.ChatBot
import com.example.chatapp.data.model.ChatMessage

class ChatAdapter(
    private val bots: List<ChatBot>,
    private val onClick: (ChatBot) -> Unit
) : RecyclerView.Adapter<ChatAdapter.BotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_preview, parent, false)
        return BotViewHolder(view)
    }

    override fun onBindViewHolder(holder: BotViewHolder, position: Int) {
        val bot = bots[position]
        holder.name.text = bot.name
        holder.preview.text = bot.lastMessage
        holder.itemView.setOnClickListener { onClick(bot) }
    }

    override fun getItemCount() = bots.size

    class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.botNameTextView)
        val preview: TextView = itemView.findViewById(R.id.previewMessageTextView)
    }
}

