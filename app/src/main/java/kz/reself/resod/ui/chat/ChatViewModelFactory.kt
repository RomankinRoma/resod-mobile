package kz.reself.resod.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory(private var token: String?,
                           private var email: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(token, email) as T
        }

        throw IllegalArgumentException("not fount ChatViewModelFactory")
    }
}