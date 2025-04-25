package com.example.chatapp.models

data class ChatBot(
    val id: String,
    val name: String,
    var lastMessage: String = ""
)
