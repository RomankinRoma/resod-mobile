package kz.reself.resod.ui.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kz.reself.resod.api.data.ChatUser
import kz.reself.resod.api.data.Specialist
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(token: String?, email: String?) : ViewModel() {
    var chatUserList: MutableLiveData<MutableList<ChatUser>> = MutableLiveData()
//    var chatUserListLocal: MutableList<ChatUser> = mutableListOf()

    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)

    init {
//        chatUserList.value = mutableListOf()
        getAllChat(token, email)

    }

    private fun getAllChat(token: String?, email: String?) {
        chatUserList.value = mutableListOf()

        val responseChatUserList = retrofit.getAllConversationByAuthor(token, email)

        responseChatUserList.enqueue(object : Callback<List<ChatUser>?> {
            override fun onResponse(call: Call<List<ChatUser>?>, response: Response<List<ChatUser>?>) {

                if (response.isSuccessful) {
                    val list = response.body()!!
                    chatUserList.value = list.toMutableList()

                    for (index in chatUserList.value!!.indices) {
                        val chatUser = chatUserList.value!!.get(index)

                        Log.println(Log.INFO,"ITEM_INDEX","INDEX: " + index)

                        Log.println(Log.INFO,"ITEM_INDEX","VAL: " + Gson().toJson(chatUser))

                        getSpecialistInfo(chatUser.responderName, index)
                    }
                }

                Log.println(Log.INFO,"FINISH","CHAT LIST: " + chatUserList.value)
            }

            override fun onFailure(call: Call<List<ChatUser>?>, t: Throwable) {
                Log.e("GET_CHAT","ERROR:" + t.message)
            }
        })
    }

    private fun getSpecialistInfo(email: String, index: Int) {
        val responseSpecialist = retrofit.getSpecialistByEmail(email)

        responseSpecialist.enqueue(object : Callback<Specialist?> {
            override fun onResponse(call: Call<Specialist?>, response: Response<Specialist?>) {

                if (response.isSuccessful) {
                    Log.println(Log.INFO,"SPECIALIST_VAL","VAL: " + Gson().toJson(response.body()))

                    chatUserList.value!!.get(index).avatar = response.body()!!.storageUrl
                    chatUserList.value!!.get(index).firstName = response.body()!!.firstName
                    chatUserList.value!!.get(index).lastName = response.body()!!.lastName
                    chatUserList.value = chatUserList.value
                }

                Log.println(Log.INFO,"SPECIALIST_RESPONSE","VAL: " + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<Specialist?>, t: Throwable) {
                Log.e("GET_CHAT","ERROR:" + t.message)
            }
        })
    }
}