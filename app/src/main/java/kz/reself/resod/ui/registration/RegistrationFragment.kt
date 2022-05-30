package kz.reself.resod.ui.registration

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.gson.Gson
import kz.reself.resod.R
import kz.reself.resod.api.data.RegistrationForm
import kz.reself.resod.api.data.User
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationFragment : Fragment() {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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

        return binding.root
    }

    private fun registerUser() {

        if (formIsValid()) {
            val name = binding.activityRegistrationInputUserName.text.toString()
            val email = binding.activityRegistrationTextInputEditTextEmail.text.toString()
            val password = binding.activityRegistrationTextInputEditTextPassword.text.toString()

            val body = RegistrationForm(name, "", password, email, "ROLE_USER")

            Log.println(Log.INFO,"GET_SPECIALISTS","name: " + body.name)
            Log.println(Log.INFO,"GET_SPECIALISTS","surname: " + body.surname)
            Log.println(Log.INFO,"GET_SPECIALISTS","email: " + body.email)
            Log.println(Log.INFO,"GET_SPECIALISTS","password: " + body.password)
            Log.println(Log.INFO,"GET_SPECIALISTS","role" + body.role)

            val responseRegistration = retrofit.registrationUser(body)

            responseRegistration.enqueue(object : Callback<User?> {
                override fun onResponse(call: Call<User?>, response: Response<User?>) {
                    if (response.isSuccessful) {
                        showResultAlert(response.body())
                    } else {
                        val dialog = AlertDialog.Builder(requireContext())
                            .setTitle("Ошибка!")
                            .setMessage("попробуйте еще раз!")
                            .create()
                        dialog.show()
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.e("REGISTRATION_USER_ERROR","REGISTRATION_USER_ERROR:" + t.message)
                }
            })
        }

    }

    private fun showResultAlert(user: User?) {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE ->
                    Navigation.findNavController(binding.root).navigate(R.id.action_fragment_registration_nav_to_fragment_login)
            }

        }

        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle("Успешная регистрация")
            .setMessage("Пользователь " + user?.email + " зарегестрирован. Для дальнейшего использования аккаунтом, необходимо подтвердить регистрацию по ссылке на почте!")
            .setPositiveButton("OK", listener)
            .create()
        dialog.show()
    }

    private fun formIsValid(): Boolean {
        return binding.activityRegistrationInputUserName.text.toString() != null
                && binding.activityRegistrationTextInputEditTextEmail.toString() != null
                && binding.activityRegistrationTextInputEditTextPassword.toString() != null
                && binding.activityRegistrationTextInputLayoutPasswordConfirm.error == null
    }
}