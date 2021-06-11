package com.inmersoft.trinidadpatrimonial.home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.inmersoft.trinidadpatrimonial.R
import com.inmersoft.trinidadpatrimonial.core.imageloader.ImageLoader
import com.inmersoft.trinidadpatrimonial.databinding.HomeFragmentBinding
import com.inmersoft.trinidadpatrimonial.details.bottomsheet.BottomSheet
import com.inmersoft.trinidadpatrimonial.details.ui.fragments.ViewPagerDetailFragment
import com.inmersoft.trinidadpatrimonial.home.ui.adapters.HomePlaceTypeAdapter
import com.inmersoft.trinidadpatrimonial.utils.PlaceTypeFilter
import com.inmersoft.trinidadpatrimonial.viewmodels.TrinidadDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    @Inject
    lateinit var imageLoader: ImageLoader

    private val trinidadDataViewModel: TrinidadDataViewModel by activityViewModels()

    val mainAdapter: HomePlaceTypeAdapter by lazy {
        HomePlaceTypeAdapter(
            imageLoader
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(layoutInflater, container, false)

        binding.toolbar.menu.findItem(R.id.action_search)
            .setOnMenuItemClickListener {
                Log.d("TAG", "onCreateView: CLICKED")
                true
            }
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        /*  val searchView = binding.toolbar.searget(1) as androidx.appcompat.widget.SearchView
          searchView.setOnClickListener {
              Log.d("TAG", "userSearch: TESSSTS")
          }*/
/*

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                Toast.makeText(requireContext(), "User search: $query", Toast.LENGTH_SHORT).show()

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("TAG", "userSearch: $query")
                return true
            }
        })
*/

        binding.fab.setOnClickListener {

            val listDetails = listOf(
                ViewPagerDetailFragment(),
                ViewPagerDetailFragment(),
                ViewPagerDetailFragment(),
                ViewPagerDetailFragment(),
                ViewPagerDetailFragment(),
            )
            val bottomSheet = BottomSheet(listDetails)
            bottomSheet.show(requireActivity().supportFragmentManager, "TrinidadDetailsBottomSheet")

        }

        binding.mainRecycleview.setHasFixedSize(true)
        binding.mainRecycleview.adapter = mainAdapter

        trinidadDataViewModel.allPlaceTypeWithPlaces.observe(
            requireActivity(),
            { placeTypeWithPlacesList ->
                val placesFilter = PlaceTypeFilter.filterNotEmptyPlaces(placeTypeWithPlacesList)
                mainAdapter.setData(placesFilter)
            })

        binding.mainRecycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("LOAD-TRINIDAD", "onScrolled: LOAD MORE...")
                }
            }
        })

        return binding.root
    }

}

