package team.pfm.test.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.android.support.DaggerAppCompatActivity
import team.pfm.test.R
import team.pfm.test.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val binding by viewBinding(ActivityMainBinding::bind)
    private var adapter: UsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUsersList()

        viewModel.users.observe(this, {
            adapter?.setUsers(it)
        })
    }


    private fun initUsersList() {
        adapter = UsersAdapter(
            Glide.with(this),
            viewModel::onUserItemClicked,
            viewModel::onUserItemRemoved
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

    override fun onDestroy() {
        super.onDestroy()
        binding.rvUsers.adapter = null
    }
}