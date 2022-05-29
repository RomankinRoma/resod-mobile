package kz.reself.resod

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.gson.Gson
import kz.reself.resod.api.data.RegistrationForm
import kz.reself.resod.api.data.User
import kz.reself.resod.api.model.CompanyDTO
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        binding.activityRegistrationTextInputEditTextPasswordConfirm.doOnTextChanged {
                text, start, before, count ->
            if (text!!.isNotEmpty() && !text.toString().equals(binding.activityRegistrationTextInputEditTextPassword.text.toString())) {
                binding.activityRegistrationTextInputLayoutPasswordConfirm.error = "Not equal!"
            } else {
                binding.activityRegistrationTextInputLayoutPasswordConfirm.error = null
            }
        }

        binding.activityRegistrationRegistrationBtn.setOnClickListener {
            registerUser()
        }

        setContentView(binding.root)
    }

    private fun registerUser() {

        if (formIsValid()) {
            val name = binding.activityRegistrationInputUserName.text.toString()
            val email = binding.activityRegistrationTextInputEditTextEmail.toString()
            val password = binding.activityRegistrationTextInputEditTextEmail.toString()

            val body = RegistrationForm(name, "", password, email, "ROLE_USER")

            val responseRegistration = retrofit.registrationUser(body)

            responseRegistration.enqueue(object : Callback<User?> {
                override fun onResponse(call: Call<User?>, response: Response<User?>) {

                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER_BODY:" + Gson().toJson(response.body()))
                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER_BODY:" + Gson().toJson(response.message()))
                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER_BODY:" + Gson().toJson(response.code()))
                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER_BODY:" + Gson().toJson(response.errorBody()))
                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER_BODY:" + Gson().toJson(response.isSuccessful))
                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER_BODY:" + Gson().toJson(response.headers()))
                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER_BODY:" + Gson().toJson(response.toString()))
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.println(Log.INFO,"REGISTRATION_USER","REGISTRATION_USER:" + Gson().toJson(t))
                    Log.e("REGISTRATION_USER_ERROR","REGISTRATION_USER_ERROR:" + t.message)
                }
            })
        }

    }

    private fun formIsValid(): Boolean {
        return binding.activityRegistrationInputUserName.text.toString() != null
                && binding.activityRegistrationTextInputEditTextEmail.toString() != null
                && binding.activityRegistrationTextInputEditTextPassword.toString() != null
                && binding.activityRegistrationTextInputLayoutPasswordConfirm.error == null
    }
}