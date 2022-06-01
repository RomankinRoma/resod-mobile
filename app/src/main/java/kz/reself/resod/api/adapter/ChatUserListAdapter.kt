package kz.reself.resod.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import kz.reself.resod.api.data.ChatUser
import kz.reself.resod.databinding.ChatUserCardBinding

class ChatUserListAdapter(
    private var chatUserList: List<ChatUser>,
    private val onClickByChatUserListener: Listener,
    private val context: Context
) : BaseAdapter() {

    override fun getItem(position: Int): ChatUser {
        return chatUserList[position]
    }

    override fun getItemId(position: Int): Long {
        return chatUserList[position].id
    }

    override fun getCount(): Int {
        return chatUserList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            convertView?.tag as ChatUserCardBinding? ?:
            createBinding(parent.context)

        val chatUser = getItem(position)

        binding.chatUserCardBox.setOnClickListener {
            onClickByChatUserListener.onChatUserClick(chatUser)
        }

        Glide.with(context).load(chatUser.avatar).into(binding.chatUserCardUserImg)
        binding.chatUserCardUserFullName.text = chatUser.firstName +  " " + chatUser.lastName
        binding.chatUserCardLastMessage.text = chatUser.lastMessage

        return binding.root
    }

    private fun createBinding(context: Context?): ChatUserCardBinding {
        val binding = ChatUserCardBinding.inflate(LayoutInflater.from(context))

        binding.root.tag = binding

        return binding
    }

    interface Listener {
        fun onChatUserClick(chatUser: ChatUser)
    }

    fun update(list: List<ChatUser>) {
        chatUserList = list
        notifyDataSetChanged()
    }
}