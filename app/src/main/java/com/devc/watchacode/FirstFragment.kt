package com.devc.watchacode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devc.watchacode.data.remote.dto.ItunesDto
import com.devc.watchacode.data.remote.dto.Result
import com.devc.watchacode.databinding.FragmentFirstBinding
import com.devc.watchacode.databinding.ItemListBinding
import com.devc.watchacode.domain.model.Favorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding
    private val viewModel : SharedViewModel by viewModels()
    var adapter : ItunesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getFavoriteIds()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getList()
        //결과값을 observe 하고있다가, 들어오면 그때 실행
        viewModel.searchResult.observe(viewLifecycleOwner, {
            val recyclerView = binding.recyclerView
            adapter = ItunesAdapter(viewModel.searchResult.value!!, viewModel, viewModel.favoriteIds.value!!)
            recyclerView.adapter = adapter
            adapter!!.notifyDataSetChanged()
        })
        super.onViewCreated(view, savedInstanceState)
    }

    //RecyclerView 데이터를 화면에 구성하기위한 어댑터
    class ItunesAdapter(
        private val items: ItunesDto, private val viewModel: SharedViewModel, private val likes: List<Int>,
    ) : RecyclerView.Adapter<ItunesAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        override fun getItemCount(): Int = items.results.size
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(
                items.results[position]
            )
        }
        inner class ViewHolder(
            private val binding: ItemListBinding
        ) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(data : Result) {
                if(likes.contains(data.trackId)){
                    data.like = true
                }
                binding.trackName.text = data.trackName
                binding.collectionName.text = data.collectionName
                binding.artistName.text = data.artistName
                binding.likeBtn.setOnClickListener {
                    if(data.like){
                        viewModel.deleteFavorite(data.trackId)
                        data.like = false
                        binding.likeBtn.setImageResource(R.drawable.ic_star_grey)
                    }else{
                        viewModel.insertFavorite(
                            Favorite(data.trackId,
                                data.trackName,
                                data.collectionName,
                                data.artistName,
                                data.artworkUrl60))
                        data.like = true
                        binding.likeBtn.setImageResource(R.drawable.ic_star_blue)
                    }
                }
                if(data.like){
                    binding.likeBtn.setImageResource(R.drawable.ic_star_blue)
                }else{
                    binding.likeBtn.setImageResource(R.drawable.ic_star_grey)
                }
                //이미지 삽입을 위한 GLIDE
                Glide.with(binding.artistViewUrl)
                    .load(data.artworkUrl60)
                    .centerInside()
                    .into(binding.artistViewUrl)
            }
        }
    }

}