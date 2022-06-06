package com.ahmad.githubuserapplication.ui.main

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmad.githubuserapplication.R
import com.ahmad.githubuserapplication.data.Result
import com.ahmad.githubuserapplication.data.local.entity.UserEntity
import com.ahmad.githubuserapplication.databinding.FragmentHomeBinding
import com.ahmad.githubuserapplication.ui.adapter.UserAdapter
import com.ahmad.githubuserapplication.ui.viewmodel.UserViewModel
import com.ahmad.githubuserapplication.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var factory: ViewModelFactory
    private val viewModel: UserViewModel by viewModels { factory }
    private lateinit var userAdapter: UserAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())
        userAdapter = UserAdapter()

        setupViewModel()
        setupRv()
        getSearchUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupViewModel(){
        viewModel.getUsers().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                getResultData(result)
            }
        }
    }

    private fun getSearchUser(){
        binding?.apply {
            svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(query: String?): Boolean {
                    if(query != null){
                        viewModel.insertSearchUser(query).observe(viewLifecycleOwner){
                        }
                        searchUser(query)
                    }
                    return true
                }
            })
        }
    }

    private fun searchUser(query: String){
        val searchQuery = "%$query%"
        viewModel.getSearchUser(searchQuery).observe(viewLifecycleOwner){ result ->
            if(result != null){
                getResultData(result)
            }
        }
    }

    private fun getResultData(result: Result<List<UserEntity>>){
        when (result) {
            is Result.Loading -> {
                binding?.progressBar?.visibility = View.VISIBLE
            }
            is Result.Success -> {
                binding?.progressBar?.visibility = View.GONE
                val userItem = result.data
                userAdapter.submitList(userItem)
            }
            is Result.Error -> {
                binding?.progressBar?.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Terjadi kesalahan ${result.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRv(){
        binding?.rvUser?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }
}