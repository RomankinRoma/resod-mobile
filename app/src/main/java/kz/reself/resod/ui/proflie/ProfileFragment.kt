package kz.reself.resod.ui.proflie

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import kz.reself.resod.R
import kz.reself.resod.USER_EMAIL_KEY
import kz.reself.resod.USER_ID_KEY
import kz.reself.resod.api.data.User
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.FragmentProfileBinding
import kz.reself.resod.ui.login.APP_PREFERENCES
import kz.reself.resod.ui.login.USER_LOGIN_STATUS_KEY
import kz.reself.resod.ui.login.USER_LOGIN_TYPE_KEY
import kz.reself.resod.ui.login.USER_TOKEN_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private var _binding: FragmentProfileBinding? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var appSharedPreferences: SharedPreferences


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        var acc = GoogleSignIn.getLastSignedInAccount(requireContext())

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val root: View = binding.root

        appSharedPreferences = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val email = appSharedPreferences.getString(USER_EMAIL_KEY, "")

        if (acc == null && !email.equals("")) {
            getUserInfo(email)
        }

        binding.fragmentProfileLogoutBtn.setOnClickListener {
            val token = appSharedPreferences.getString(USER_TOKEN_KEY, "")
            logout(token)
        }

        if (acc != null) {
            binding.fragmentProfileUserName.text = acc?.displayName
            binding.fragmentProfileUserEmail.text = binding.fragmentProfileUserEmail.text.toString() + acc?.email
            Glide.with(requireContext()).load(acc?.photoUrl).into(binding.fragmentProfileUserImg)
        }

        val accessToken = AccessToken.getCurrentAccessToken()

        return root
    }

    private fun logout(token: String?) {
        clearStore()
        val responseCompanyImg = retrofit.logoutUser(token)

        responseCompanyImg.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {

                clearStore()
                Navigation.findNavController(binding.root).navigate(R.id.action_fragment_profile_nav_to_fragment_home)
//                Log.println(Log.INFO,"GET_COMPANY_IMG","COMPANY_IMG_LIST:" + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
//                Log.e("GET_COMPANY_IMG","ERROR:" + t.message)
            }
        })
    }

    private fun clearStore() {
        appSharedPreferences.edit()
            .putString(USER_ID_KEY, "")
            .putString(USER_EMAIL_KEY, "")
            .putString(USER_TOKEN_KEY, "")
            .putString(USER_LOGIN_TYPE_KEY, "")
            .putString(USER_LOGIN_STATUS_KEY, "no")
            .apply()
    }

    fun getUserInfo(email: String?) {
        if (email != null) {
            val responseCompanyImg = retrofit.getUserByEmail(email)

            responseCompanyImg.enqueue(object : Callback<User?> {
                override fun onResponse(call: Call<User?>, response: Response<User?>) {

                    if (response.isSuccessful) {
                        setUserInfos(response.body())
                    }
                    Log.println(Log.INFO,"GET_COMPANY_IMG","COMPANY_IMG_LIST:" + Gson().toJson(response.body()))
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("GET_COMPANY_IMG","ERROR:" + t.message)
                }
            })
        }
    }

    private fun setUserInfos(userDto: User?) {
        if (userDto != null) {
            Glide.with(requireContext()).load(userDto.storageUrl).into(binding.fragmentProfileUserImg)
            binding.fragmentProfileUserName.text = userDto.name + " " + userDto.surname
            binding.fragmentProfileUserEmail.text = userDto.email
//            binding.fragmentProfileUserCountryVal.text = userDto
//            binding.fragmentProfileUserCity.text = userDto
            binding.fragmentProfileUserPhoneNumberVal.text = userDto.phoneNumber
//            binding.fragmentProfileUserDateOfBirthVal.text =
        }
    }
}