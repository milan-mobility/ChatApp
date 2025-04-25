package com.example.chatapp.models

data class ChatMessage(
    val message: String,
    val isSent: Boolean = true,
    val id: String,
    val timestamp: Long = System.currentTimeMillis()
)
