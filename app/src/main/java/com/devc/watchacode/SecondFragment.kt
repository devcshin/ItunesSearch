package com.devc.watchacode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devc.watchacode.databinding.FragmentSecondBinding
import com.devc.watchacode.databinding.ItemLikeListBinding
import com.devc.watchacode.domain.model.Favorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private lateinit var binding : FragmentSecondBinding
    private val viewModel : SharedViewModel by viewModels()
    var adapter : LikesAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getFavorites()
        viewModel.favoriteResult.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()){
                //binding.emptyContainer.visibility = View.GONE
            }
            val recyclerView = binding.recyclerView
            adapter = LikesAdapter(viewModel.favoriteResult.value!!, viewModel)
            recyclerView.adapter = adapter
        })

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter?.notifyDataSetChanged()
        super.onViewCreated(view, savedInstanceState)

    }

    class LikesAdapter(
        private val items: List<Favorite>, private val viewModel: SharedViewModel
    ) : RecyclerView.Adapter<LikesAdapter.ViewHolder>() {

        private var listData: MutableList<Favorite> = items as MutableList<Favorite>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            ItemLikeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        override fun getItemCount(): Int = listData.size
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bind(
                listData[position], position
            )
        }
        inner class ViewHolder(
            private val binding: ItemLikeListBinding
        ) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(data: Favorite, position: Int) {
                binding.trackName.text = data.trackName
                binding.collectionName.text = data.collectionName
                binding.artistName.text = data.artistName
                binding.likeBtn.setOnClickListener {
                    //delete
                    viewModel.deleteFavorite(data.trackId)
                    listData.removeAt(position)
                    notifyDataSetChanged()

                }
                Glide.with(binding.artistViewUrl)
                    .load(data.ImageUrl)
                    .centerInside()
                    .into(binding.artistViewUrl)

            }
        }
    }

}