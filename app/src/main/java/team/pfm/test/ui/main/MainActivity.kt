package team.pfm.test.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.android.support.DaggerAppCompatActivity
import team.pfm.test.R
import team.pfm.test.data.model.User
import team.pfm.test.databinding.ActivityMainBinding
import team.pfm.test.ui.edituserdetails.EditUserDetailsFragment
import team.pfm.test.ui.userdetails.UserDetailsFragment
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(R.layout.activity_main),
    EditUserDetailsFragment.OnEditSuccessListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(ActivityMainBinding::bind)
    private var adapter: UsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUsersList()

        initSwipeRefresh()
        viewModel.refreshing.observe(this, { binding.srlUsers.isRefreshing = it })

        viewModel.users.observe(this, {
            adapter?.setUsers(it)
        })

        viewModel.editDetails.observe(this, { userId ->
            val tag = EditUserDetailsFragment::class.simpleName
            if (supportFragmentManager.findFragmentByTag(tag) == null) {
                val dialog = EditUserDetailsFragment.newInstance(userId)
                dialog.show(supportFragmentManager, tag)
            }
        })

        viewModel.showDetails.observe(this, { userId ->
            val tag = UserDetailsFragment::class.simpleName
            if (supportFragmentManager.findFragmentByTag(tag) == null) {
                val dialog = UserDetailsFragment.newInstance(userId)
                dialog.show(supportFragmentManager, tag)
            }
        })
    }

    private fun initUsersList() {
        adapter = UsersAdapter(
            Glide.with(this),
            viewModel::onUserItemClicked,
            viewModel::onUserItemRemoved,
            viewModel::onEditButtonClicked
        )

        binding.rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUsers.adapter = adapter

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter?.removeItemAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvUsers)
    }

    private fun initSwipeRefresh() {
        binding.srlUsers.setOnRefreshListener { viewModel.onSwipeRefresh() }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvUsers.adapter = null
    }

    override fun onEditSuccess(updatedUser: User) {
        adapter?.updateUser(updatedUser)
    }
}