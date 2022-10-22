package com.dicoding.mystory.view.main


import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mystory.R
import com.dicoding.mystory.adapter.ListStoryAdapter
import com.dicoding.mystory.adapter.LoadingStateAdapter
import com.dicoding.mystory.adapter.StoryListAdapter
import com.dicoding.mystory.data.ListStory
import com.dicoding.mystory.databinding.ActivityMainBinding
import com.dicoding.mystory.model.UserPreference
import com.dicoding.mystory.factory.ViewModelFactory
import com.dicoding.mystory.view.addstory.AddStoryActivity
import com.dicoding.mystory.view.detailview.DetailViewActivity
import com.dicoding.mystory.view.login.LoginActivity
import com.dicoding.mystory.view.welcome.WelcomeActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private val viewModel by viewModels <MainViewModelForRV>()
    private lateinit var binding: ActivityMainBinding
//    private lateinit var adapter: StoryListAdapter
    private lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.rvListStory.layoutManager = LinearLayoutManager(this)
        setupView()
        setupViewModel()

         showRV()
//
//



        setupAction()
//        adapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: ListStory,viewHolder: ListStoryAdapter.ViewHolder)
//            {
//
//               val intent= Intent(this@MainActivity, DetailViewActivity::class.java).also {
//                    it.putExtra(DetailViewActivity.DETAIL_USERNAME, data.name)
//                    it.putExtra(DetailViewActivity.DETAIL_PHOTO, data.photoUrl)
//                    it.putExtra(DetailViewActivity.DETAIL_DESC, data.description)
//
//
//                }
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        this@MainActivity,
//                        Pair(viewHolder.itemImg, "photo"),
//                        Pair(viewHolder.tvItem, "name"),
//
//                    )
//                startActivity(intent, optionsCompat.toBundle())
//            }
//
//        })

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

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.tvName.text = getString(R.string.greeting, user.name)
                token = user.token
//                getData(user.token)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }

        }
    }
//    private fun getData(token:String){
//        viewModel = ViewModelProvider(this,
//            ViewModelProvider.NewInstanceFactory())[MainViewModelForRV::class.java]
//       showLoading(true)
//        viewModel.getAllStory(token)
//
//        viewModel.getStory().observe(this) {
//
//            if ( it.isEmpty())
//            {
//                binding.textView.visibility = View.VISIBLE
//                showLoading(false)
//            }
//            else
//            {
//                binding.textView.visibility = View.GONE
////                adapter.setList(it)
//                showLoading(false)
//            }
//        }
//
//        viewModel.failure.observe(this) {
//            if (it.length>1) {
//                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
//                binding.textView.visibility = View.VISIBLE
//                showLoading(false)
//            }
//        }
//    }

    private fun showRV() {
        val adapter = StoryListAdapter()
        binding.apply {
            rvListStory.setHasFixedSize(true)
            rvListStory.layoutManager =  LinearLayoutManager(this@MainActivity)
            rvListStory.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
        }
        viewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }



    private fun setupAction() {
        with(binding) {
                ibLogout.setOnClickListener {
                Intent(this@MainActivity, LoginActivity::class.java)
                    .apply {
                        startActivity(this)
                        finishAffinity()
                        mainViewModel.logout()
                    }
            }

            fabAddNewStory.setOnClickListener {
                Intent(this@MainActivity, AddStoryActivity::class.java)
                    .apply {
                        startActivity(this)
                    }
            }


        }
    }
    override fun onResume() {
        super.onResume()
       showRV()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}