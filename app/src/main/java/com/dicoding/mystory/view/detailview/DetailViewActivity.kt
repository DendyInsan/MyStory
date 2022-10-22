package com.dicoding.mystory.view.detailview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mystory.R
import com.dicoding.mystory.databinding.ActivityDetailViewBinding
import com.dicoding.mystory.model.UserPreference
import com.dicoding.mystory.factory.ViewModelFactory
import com.dicoding.mystory.view.login.LoginActivity
import com.dicoding.mystory.view.main.MainActivity

import com.dicoding.mystory.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailViewActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(DETAIL_USERNAME)
        val photo= intent.getStringExtra(DETAIL_PHOTO)
        val desc=intent.getStringExtra(DETAIL_DESC)


            Glide.with(this@DetailViewActivity)
                .load(photo)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(binding.itemPhoto)

        binding.tvName.text = name
        binding.tvDesc.text = desc

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailViewModel::class.java]

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.tvGreeting.text =getString(R.string.greeting2, user.name)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }

        }

        setupView()
        playAnimation()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setupAction() {
        with(binding) {
            ibLogout.setOnClickListener {
                Intent(this@DetailViewActivity, LoginActivity::class.java)
                    .apply {
                        startActivity(this)
                        finishAffinity()
                        viewModel.logout()
                    }
            }

            backButton.setOnClickListener {
                Intent(this@DetailViewActivity, MainActivity::class.java)
                    .apply {
                        startActivity(this)
                    }
            }


        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.itemPhoto, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val name = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.tvDesc, View.ALPHA, 1f).setDuration(500)
        val backbutton = ObjectAnimator.ofFloat(binding.backButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(name, desc, backbutton)
            startDelay = 500
        }.start()
    }
    companion object {

        const val DETAIL_USERNAME = "detail_username"
        const val DETAIL_PHOTO = "detail_photo"
        const val DETAIL_DESC = "detail_desc"

    }
}