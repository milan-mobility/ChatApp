package com.example.chatapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.model.ChatBot
import com.example.chatapp.repository.ChatRepository

class ChatBotViewModel:ViewModel() {

    private val chatBotMutableLiveData: MutableLiveData<List<ChatBot>> = MutableLiveData()
    val chatBotLiveData: LiveData<List<ChatBot>> = chatBotMutableLiveData

    init {
        chatBotMutableLiveData.postValue(ChatRepository.getBots())
    }
}