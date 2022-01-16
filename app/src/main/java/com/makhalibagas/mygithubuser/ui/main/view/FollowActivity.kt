package com.makhalibagas.mygithubuser.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.mygithubuser.data.remote.api.Status
import com.makhalibagas.mygithubuser.databinding.ActivityFollowBinding
import com.makhalibagas.mygithubuser.ui.base.BaseActivity
import com.makhalibagas.mygithubuser.ui.main.adapter.UserAdapter
import com.makhalibagas.mygithubuser.ui.main.viewmodel.UserViewModel
import com.makhalibagas.mygithubuser.utils.gone
import com.makhalibagas.mygithubuser.utils.visible
import org.koin.android.ext.android.inject

class FollowActivity : BaseActivity<ActivityFollowBinding>(ActivityFollowBinding::inflate) {

    private val userViewModel: UserViewModel by inject()
    private lateinit var userAdapter: UserAdapter
    private var page = 1
    private var currentPage = page
    private var nextPage = currentPage + 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        setupRV()
        setupObserver()
    }

    private fun setupUI() {
        val type = intent.getStringExtra("type")
        val username = intent.getStringExtra("username")
        binding.apply {
            if (type.equals("following")) {
                tvKet.text = "Following $username"
            } else {
                tvKet.text = "Followers $username"
            }
        }
    }

    private fun setupRV() {
        val type = intent.getStringExtra("type")
        val username = intent.getStringExtra("username")
        binding.apply {
            rvUsers.apply {
                layoutManager = LinearLayoutManager(this@FollowActivity)
                setHasFixedSize(true)
                userAdapter = UserAdapter { users ->
                    val intent = Intent(this@FollowActivity, DetailActivity::class.java)
                    intent.putExtra("users", users)
                    startActivity(intent)
                }
                adapter = userAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!canScrollVertically(1)) {
                            if (currentPage < 10) {
                                nextPage = currentPage + 1
                                if (type.equals("following")) {
                                    userViewModel.getFollowingUser(username.toString(), nextPage)
                                } else {
                                    userViewModel.getFollowersUser(username.toString(), nextPage)
                                }
                                currentPage = nextPage
                            }
                        }
                    }
                })
            }
        }
    }

    private fun setupObserver() {
        val type = intent.getStringExtra("type")
        val username = intent.getStringExtra("username")
        if (type.equals("following")) {
            userViewModel.apply {
                getFollowingUser(username.toString(), 1)
                following.observe(this@FollowActivity, {
                    when (it) {
                        is Status.Loading -> {
                            binding.lottieLoading.visible()
                        }
                        is Status.Success -> {
                            if (it.data.isEmpty()) {
                                Toast.makeText(
                                    this@FollowActivity,
                                    "Data Kosong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                userAdapter.addList(it.data)
                            }
                            binding.lottieLoading.gone()
                        }
                        is Status.Error -> {
                            binding.lottieLoading.gone()
                        }
                    }
                })
            }
        } else {
            userViewModel.apply {
                getFollowersUser(username.toString(), 1)
                followers.observe(this@FollowActivity, {
                    when (it) {
                        is Status.Loading -> {
                            binding.lottieLoading.visible()
                        }
                        is Status.Success -> {
                            if (it.data.isEmpty()) {
                                Toast.makeText(
                                    this@FollowActivity,
                                    "Data Kosong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                userAdapter.addList(it.data)
                            }
                            binding.lottieLoading.gone()
                        }
                        is Status.Error -> {
                            binding.lottieLoading.gone()
                        }
                    }
                })
            }
        }
    }

}