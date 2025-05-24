package com.example.retrofitpostapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitpostapp.databinding.ActivityMainBinding
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.tvIdentitasNim.text = ""
        binding.tvIdentitasNama.text = "235150707111043 | Ananda Dzaky Islami"

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = FadeInUpAnimator().apply {
                addDuration = 300
            }
        }

        loadPosts()

        binding.btnRetry.setOnClickListener {
            binding.btnRetry.visibility = View.GONE
            loadPosts()
        }
    }

    private fun loadPosts() {
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<List<Post>> = ApiClient.instance.getPosts().execute()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        response.body()?.let { posts ->
                            binding.rvPosts.adapter = PostAdapter(posts) { post ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra("POST_ID", post.id)
                                startActivity(intent)
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                            }
                        } ?: Toast.makeText(this@MainActivity, "Data kosong", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.btnRetry.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, "Gagal memuat data: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRetry.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
