package team.pfm.test.ui.main

import androidx.recyclerview.widget.DiffUtil
import team.pfm.test.data.network.User


class UsersDiffCallback(private val oldList: List<User>, private val newList: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.hashCode() == newItem.hashCode()
    }
}