package com.example.coba.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coba.R
import com.example.coba.data.model.user.User
import kotlinx.android.synthetic.main.item_user_search.view.*

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var users = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (users.size <= position) return

        holder.bindData(users[position])
    }

    fun setNewData(list: List<User>?) {
        users.apply {
            clear()
            list?.let { users.addAll(it) }
        }
        notifyDataSetChanged()
    }

    fun addData(list: List<User>?) {
        list?.let {
            users.addAll(it)
            notifyItemInserted(users.size)
        }
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(model: User) {
            view.apply {
                username_txt?.text = model.login
                Glide.with(context).load(model.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.mipmap.ic_launcher_round).into(avatar_iv)
            }
        }
    }
}