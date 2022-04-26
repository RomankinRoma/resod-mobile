package kz.reself.resod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.reself.resod.databinding.ActivitySpecialistDetailsBinding

class SpecialistDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpecialistDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpecialistDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}