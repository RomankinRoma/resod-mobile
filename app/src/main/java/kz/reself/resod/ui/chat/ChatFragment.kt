package kz.reself.resod.ui.chat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kz.reself.resod.APP_PREFERENCES
import kz.reself.resod.USER_EMAIL_KEY
import kz.reself.resod.USER_TOKEN_KEY
import kz.reself.resod.api.adapter.ChatUserListAdapter
import kz.reself.resod.api.data.ChatUser
import kz.reself.resod.databinding.FragmentChatBinding

class ChatFragment : Fragment(), ChatUserListAdapter.Listener {
    private var _binding: FragmentChatBinding? = null
    private lateinit var appSharedPreferences: SharedPreferences
    private lateinit var chatViewModel: ChatViewModel

    private var chatUserList: MutableList<ChatUser> = mutableListOf()
    private lateinit var chatUserListAdapter: ChatUserListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val rootView = binding.root

        //create adapter
        val chatUserListAdapter = ChatUserListAdapter(chatUserList, this, requireContext())
        binding.fragmentChatListView.adapter = chatUserListAdapter

        appSharedPreferences = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        val token = appSharedPreferences.getString(USER_TOKEN_KEY, "")
        val email = appSharedPreferences.getString(USER_EMAIL_KEY, "")

        val chatViewModelFactory = ChatViewModelFactory(token, email)
        chatViewModel = ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel::class.java)

        chatViewModel.chatUserList.observe(viewLifecycleOwner, Observer {it ->
            chatUserListAdapter.update(it)

            Log.w("UPDATE", "Update list")
            Log.w("UPDATE", "LIST " + Gson().toJson(chatUserList))
        })

        return rootView
    }

    override fun onChatUserClick(chatUser: ChatUser) {
        Toast.makeText(context, "CLICK CHAT user: " + chatUser.firstName, Toast.LENGTH_SHORT).show()
    }
}