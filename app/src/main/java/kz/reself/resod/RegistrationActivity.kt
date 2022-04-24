package kz.reself.resod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.reself.resod.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}