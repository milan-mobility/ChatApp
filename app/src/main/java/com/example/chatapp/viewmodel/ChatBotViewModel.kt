package com.example.chatapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.models.ChatBot
import com.example.chatapp.repository.ChatRepository

class ChatBotViewModel:ViewModel() {

    private val chatBotMutableLiveData: MutableLiveData<List<ChatBot>> = MutableLiveData()
    val chatBotLiveData: LiveData<List<ChatBot>> = chatBotMutableLiveData

    init {
        chatBotMutableLiveData.postValue(ChatRepository.getBots())
    }
}