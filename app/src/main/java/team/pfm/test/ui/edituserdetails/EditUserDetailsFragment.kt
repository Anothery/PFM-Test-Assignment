package team.pfm.test.ui.edituserdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerDialogFragment
import team.pfm.test.data.model.User
import team.pfm.test.databinding.FragmentEditUserDetailsBinding
import javax.inject.Inject


class EditUserDetailsFragment : DaggerDialogFragment() {
    private var _binding: FragmentEditUserDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditUserDetailsViewModel by viewModels { viewModelFactory }


    companion object {
        val USER_ID = "USER_ID"

        fun newInstance(id: Int): EditUserDetailsFragment {
            val args = Bundle()
            args.putInt(USER_ID, id)
            val fragment = EditUserDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener { this.dismiss() }
        binding.btnSave.setOnClickListener {
            viewModel.onSaveButtonClicked(
                arguments?.getInt(USER_ID)!!,
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etEmail.text.toString()
            )
        }

        viewModel.firstName.observe(viewLifecycleOwner, { binding.etFirstName.setText(it) })
        viewModel.lastName.observe(viewLifecycleOwner, { binding.etLastName.setText(it) })
        viewModel.email.observe(viewLifecycleOwner, { binding.etEmail.setText(it) })
        viewModel.editFinished.observe(viewLifecycleOwner, {
            (activity as OnEditSuccessListener).onEditSuccess(it)
            this.dismiss()
        })

        viewModel.showUserFields(requireArguments().getInt(USER_ID))
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    interface OnEditSuccessListener {
        fun onEditSuccess(updatedUser: User)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}