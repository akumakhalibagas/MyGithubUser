package com.makhalibagas.mygithubuser.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.makhalibagas.mygithubuser.data.remote.api.Status
import com.makhalibagas.mygithubuser.data.remote.model.Users
import com.makhalibagas.mygithubuser.databinding.ActivityDetailBinding
import com.makhalibagas.mygithubuser.ui.base.BaseActivity
import com.makhalibagas.mygithubuser.ui.main.adapter.ReposAdapter
import com.makhalibagas.mygithubuser.ui.main.viewmodel.UserViewModel
import com.makhalibagas.mygithubuser.utils.gone
import com.makhalibagas.mygithubuser.utils.loadCircleImage
import com.makhalibagas.mygithubuser.utils.visible
import org.koin.android.ext.android.inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate) {

    private val userViewModel: UserViewModel by inject()
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObserver()
    }

    private fun setupUI(users: Users) {
        binding.apply {
            usersImage.loadCircleImage(users.avatarUrl.toString())
            tvName.text = users.name
            tvUsername.text = "@${users.login}"
            tvCountRepos.text = users.publicRepos.toString()
            tvCountStars.text = users.publicGists.toString()
            tvCountFollowers.text = users.followers.toString()
            tvCountFollowing.text = users.following.toString()
            tvCountFollowing.setOnClickListener {
                val intent = Intent(this@DetailActivity, FollowActivity::class.java)
                intent.putExtra("type", "following")
                intent.putExtra("username", users.login)
                startActivity(intent)
            }

            tvCountFollowers.setOnClickListener {
                val intent = Intent(this@DetailActivity, FollowActivity::class.java)
                intent.putExtra("type", "followers")
                intent.putExtra("username", users.login)
                startActivity(intent)
            }
        }
    }

    private fun setupRV() {
        binding.apply {
            rvRepos.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                setHasFixedSize(true)
                reposAdapter = ReposAdapter()
                adapter = reposAdapter
            }
        }
    }

    private fun setupObserver() {
        val users = intent.getParcelableExtra<Users>("users")
        userViewModel.apply {
            getDetailUser(users?.login.toString())
            usersDetail.observe(this@DetailActivity, {
                when (it) {
                    is Status.Loading -> {
                        binding.linear2.gone()
                    }
                    is Status.Success -> {
                        binding.apply {
                            linear2.visible()
                            usersImage.visible()
                        }
                        setupUI(it.data)
                    }
                    is Status.Error -> {
                        binding.linear2.visible()
                    }
                }
            })

            getReposUser(users?.login.toString(), 1)
            repos.observe(this@DetailActivity, {
                when (it) {
                    is Status.Loading -> {
                        binding.lottieLoading.visible()
                    }
                    is Status.Success -> {
                        binding.lottieLoading.gone()
                        setupRV()
                        reposAdapter.addList(it.data)
                    }
                    is Status.Error -> {
                        binding.lottieLoading.gone()
                    }
                }
            })
        }
    }
}