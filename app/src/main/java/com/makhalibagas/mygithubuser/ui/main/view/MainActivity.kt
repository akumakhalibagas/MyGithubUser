package com.makhalibagas.mygithubuser.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.mygithubuser.data.remote.api.Status
import com.makhalibagas.mygithubuser.databinding.ActivityMainBinding
import com.makhalibagas.mygithubuser.ui.base.BaseActivity
import com.makhalibagas.mygithubuser.ui.main.adapter.UserAdapter
import com.makhalibagas.mygithubuser.ui.main.viewmodel.UserViewModel
import com.makhalibagas.mygithubuser.utils.gone
import com.makhalibagas.mygithubuser.utils.visible
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val userViewModel: UserViewModel by inject()
    private lateinit var userAdapter: UserAdapter
    private var page = 1
    private var currentPage = page
    private var nextPage = currentPage + 1
    private lateinit var queri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        setupRV()
        setupObserver()
        validasiResponse()
    }

    private fun setupUI() {
        binding.apply {
            svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    queri = query.toString()
                    userViewModel.getUser(query.toString(), page)
                    return false
                }
            })
        }
    }

    private fun setupRV() {
        binding.apply {
            rvUsers.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                userAdapter = UserAdapter { users ->
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
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
                                userViewModel.getUser(queri, nextPage)
                                currentPage = nextPage
                            }
                        }
                    }
                })
            }
        }
    }

    private fun setupObserver() {
        userViewModel.apply {
            users.observe(this@MainActivity, {
                when (it) {
                    is Status.Loading -> {
                        binding.apply {
                            lottieLoading.visible()
                            lottie.gone()
                            tvKet.gone()
                        }
                    }
                    is Status.Success -> {
                        binding.apply {
                            if (it.data.items.isNullOrEmpty()) {
                                lottie.visible()
                                tvKet.visible()
                            } else {
                                rvUsers.visible()
                                userAdapter.addList(it.data.items)
                            }
                            lottieLoading.gone()
                        }
                    }
                    is Status.Error -> {
                        binding.apply {
                            lottieLoading.gone()
                            lottie.visible()
                            tvKet.visible()
                        }
                    }
                }
            })
        }
    }

    private fun validasiResponse() {
        val code = intent.getIntExtra("code", 0)
        if (code != 0) {
            Toast.makeText(this, "Terjadi kesalahan : $code", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.svUsers.clearFocus()
    }
}