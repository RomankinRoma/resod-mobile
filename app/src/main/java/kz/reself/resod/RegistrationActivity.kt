package kz.reself.resod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import kz.reself.resod.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        binding.fragmentRegistrationTextInputEditTextPasswordConfirm.doOnTextChanged {
                text, start, before, count ->
            if (text!!.isNotEmpty() && !text.toString().equals(binding.fragmentRegistrationTextInputEditTextPassword.text.toString())) {
                binding.fragmentRegistrationTextInputLayoutPasswordConfirm.error = "Not equal!"
            } else {
                binding.fragmentRegistrationTextInputLayoutPasswordConfirm.error = null
            }
        }

        setContentView(binding.root)
    }
}