package com.example.foodapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplication.viemodels.MainViewModel
import com.example.foodapplication.R
import com.example.foodapplication.adapters.RecipesAdapter
import com.example.foodapplication.util.Constants.Companion.API_KEY
import com.example.foodapplication.util.NetworkResult
import com.example.foodapplication.viemodels.RecipesViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val mAdapter by lazy { RecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()
    private lateinit var mView: View
    private lateinit var myShimmerLayout: ShimmerFrameLayout
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        myShimmerLayout = mView.findViewById(R.id.shimmerFrameLayout)
        recyclerView = mView.findViewById(R.id.recycler_view)


        setupRecyclerView()
        requestApiData()

        return mView
    }


    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                   hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }





    private fun setupRecyclerView() {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        myShimmerLayout.startShimmer()
        myShimmerLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

    }

    private fun hideShimmerEffect() {
        myShimmerLayout.stopShimmer()
        myShimmerLayout.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }


}