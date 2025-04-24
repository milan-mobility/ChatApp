package com.example.chatapp.data.model

data class ChatBot(
    val id: String,
    val name: String,
    var lastMessage: String = ""
)
