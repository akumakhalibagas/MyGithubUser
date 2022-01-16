package com.makhalibagas.mygithubuser.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makhalibagas.mygithubuser.data.remote.model.Users
import com.makhalibagas.mygithubuser.databinding.ItemUserBinding
import com.makhalibagas.mygithubuser.utils.loadCircleImage

class UserAdapter(val onClick : (Users) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val userList: ArrayList<Users> = ArrayList()

    fun addList(list: List<Users>) {
        userList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.apply {
                tvName.text = users.login
                tvUsername.text = users.htmlUrl
                usersImage.loadCircleImage(users.avatarUrl.toString())
                root.setOnClickListener { onClick(users) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(userList[position])

    override fun getItemCount(): Int = userList.size
}