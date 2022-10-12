package kg.o.internlabs.omarket.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.o.internlabs.omarket.databinding.ActivitySplashScreenBinding
import kg.o.internlabs.omarket.databinding.FragmentMainBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}