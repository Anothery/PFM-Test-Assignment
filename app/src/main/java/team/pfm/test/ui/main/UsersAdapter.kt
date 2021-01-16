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
    private val onItemRemoved: (Int) -> Unit
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

    inner class ViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(users: User) {
            binding.root.setOnClickListener { onItemClicked(users.id) }
            binding.tvFirstName.text = users.firstName
            binding.tvLastName.text = users.lastName

            binding.ivAvatar.apply { glide.load(users.avatar).into(this) }
        }

        fun recycle() {
            binding.ivAvatar.apply { glide.clear(this) }
        }
    }
}