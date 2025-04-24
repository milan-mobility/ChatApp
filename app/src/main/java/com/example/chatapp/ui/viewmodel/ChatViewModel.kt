package com.example.chatapp.ui.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.model.ChatMessage
import com.example.chatapp.repository.ChatRepository
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatViewModel : ViewModel() {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private val _messages = MutableLiveData<MutableList<ChatMessage>>(mutableListOf())
    val messages: LiveData<MutableList<ChatMessage>> = _messages
    private val queue = mutableListOf<String>()

    var botId:String=""

    fun assignBotId(botId:String){
        this.botId=botId
    }

    /**
     * connect with the socket.
     */
    fun connectSocket() {
        val request = Request.Builder()
            .url("wss://echo.websocket.events")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.e("WebSocket", "onOpen: ${response.message}")

                retryQueue()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.e("WebSocket", "onMessage: $text")

                if (text.contains("echo.websocket.events sponsored by Lob.com")) {
                    return
                }

                // Update the message in the livedata
                _messages.value?.add(ChatMessage(text, isSent = false,botId?:""))
                _messages.postValue(_messages.value)

                //Update the chatbot model to show the last preview message.
                ChatRepository.updateLastMessage(botId, text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "Failure: ${t.message}")
                reconnect()
            }
        })
    }

    /**
     * Reconnect on every 3 second if it;s not failed to connect.
     */
    private fun reconnect() {
        Handler(Looper.getMainLooper()).postDelayed({
            connectSocket()
        }, 3000)
    }

    /**
     * Send a message
     */
    fun sendMessage(message: String) {

        val sent = webSocket?.send(message) ?: false
        if (sent) {
            _messages.value?.add(ChatMessage(message, isSent = true,botId))
            _messages.postValue(_messages.value)
        } else {
            queue.add(message)
        }
    }

    /**
     * Set the message to Queue when device not connected with an internet.
     */
    fun retryQueue() {
        viewModelScope.launch {
            val iterator = queue.iterator()
            while (iterator.hasNext()) {
                val message = iterator.next()
                if (webSocket?.send(message) == true) {
                    _messages.value?.add(ChatMessage(message, isSent = true,botId))
                    _messages.postValue(_messages.value)
                    iterator.remove()
                }
            }
        }
    }
}
