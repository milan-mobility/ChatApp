package com.example.chatapp.repository

import com.example.chatapp.models.ChatBot

object ChatRepository {
    //prepare a mutable list of the pre defined chat bot
    private val bots = mutableListOf(
        ChatBot("support", "SupportBot", ""),
        ChatBot("sales", "SalesBot", ""),
        ChatBot("faq", "FAQBot", "")
    )

    /**
     * Update the last message to specific chatbot
     */
    fun updateLastMessage(botId: String, lastMessage: String) {
        bots.find { it.id == botId }?.lastMessage = lastMessage
    }

    /**
     * Get all the chatbot
     */
    fun getBots(): List<ChatBot> = bots
}
