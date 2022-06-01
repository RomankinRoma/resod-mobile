package kz.reself.resod.api.data

data class ChatUser(
    val id: Long,
    val authorName: String,
    val responderName: String,
    val status: String,
    val unreadCount: Long = 0,
    var avatar: String,
    var firstName: String,
    var lastName: String,
    val lastMessage: String
) {}