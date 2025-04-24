package com.example.chatapp.repository

import com.example.chatapp.data.model.ChatBot

object ChatRepository {
    private val bots = mutableListOf(
        ChatBot("support", "SupportBot", ""),
        ChatBot("sales", "SalesBot", ""),
        ChatBot("faq", "FAQBot", "")
    )

    fun updateLastMessage(botId: String, lastMessage: String) {
        bots.find { it.id == botId }?.lastMessage = lastMessage
    }

    fun getBots(): List<ChatBot> = bots
}
