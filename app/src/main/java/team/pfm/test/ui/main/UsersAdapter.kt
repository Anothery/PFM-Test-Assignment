package team.pfm.test.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import team.pfm.test.data.model.User
import team.pfm.test.databinding.UserItemBinding

class UsersAdapter(
    private val glide: RequestManager,
    private val onItemClicked: (Int) -> Unit,
    private val onItemRemoved: (Int) -> Unit,
    private val onEditButtonClicked: (Int) -> Unit
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var users: ArrayList<User> = arrayListOf()

    fun setUsers(newList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UsersDiffCallback(users, newList))
        users.clear()
        users.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) =
        holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    override fun onViewRecycled(holder: ViewHolder) {
        holder.recycle()
        super.onViewRecycled(holder)
    }

    fun removeItemAt(position: Int) {
        onItemRemoved(users[position].id)
        users.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateUser(updatedUser: User) {
        val position = users.indexOfFirst { it.id == updatedUser.id }
        if (position >= 0) {
            users[position] = updatedUser
            notifyItemChanged(position)
        }
    }

    inner class ViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.root.setOnClickListener { onItemClicked(user.id) }
            binding.tvFirstName.text = user.firstName
            binding.tvLastName.text = user.lastName

            binding.ivAvatar.apply { glide.load(user.avatar).into(this) }

            binding.btnEdit.setOnClickListener { onEditButtonClicked(user.id) }
        }

        fun recycle() {
            binding.ivAvatar.apply { glide.clear(this) }
        }
    }
}