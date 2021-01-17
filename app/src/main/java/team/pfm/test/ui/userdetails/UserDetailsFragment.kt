package team.pfm.test.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.android.support.DaggerDialogFragment
import team.pfm.test.R
import team.pfm.test.databinding.FragmentUserDetailsBinding
import javax.inject.Inject

class UserDetailsFragment : DaggerDialogFragment() {
    private val binding by viewBinding(FragmentUserDetailsBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UserDetailsViewModel by viewModels { viewModelFactory }

    companion object {
        val USER_ID = "USER_ID"

        fun newInstance(id: Int): UserDetailsFragment {
            val args = Bundle()
            args.putInt(USER_ID, id)
            val fragment = UserDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner, {
            Glide.with(this).load(it.avatar).into(binding.ivAvatar)
            binding.tvFirstName.text = it.firstName
            binding.tvLastName.text = it.lastName
            binding.tvEmail.text = it.email
        })

        viewModel.showUserDetails(requireArguments().getInt(USER_ID))
    }
}