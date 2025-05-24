package com.example.retrofitpostapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitpostapp.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvIdentitasDetail.text = "235150707111043 - Ananda Dzaky Islami"

        val postId = intent.getIntExtra("POST_ID", 1)

        ApiClient.instance.getPostById(postId).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()!!
                    binding.tvTitleDetail.text = post.title
                    binding.tvBodyDetail.text = post.body
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                // handle failure
            }
        })
    }
}
