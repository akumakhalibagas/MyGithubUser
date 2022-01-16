package com.makhalibagas.mygithubuser.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.mygithubuser.data.remote.model.RepositoryItem
import com.makhalibagas.mygithubuser.databinding.ItemReposBinding

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    private val reposList: ArrayList<RepositoryItem> = ArrayList()

    fun addList(list: List<RepositoryItem>) {
        reposList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemReposBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repos: RepositoryItem) {
            binding.apply {
                tvNameRepos.text = repos.name
                tvDecsRepos.text = repos.description
                tvLanguageRepos.text = repos.language
                tvCountStars.text = repos.stargazersCount.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemReposBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(reposList[position])

    override fun getItemCount(): Int = reposList.size
}