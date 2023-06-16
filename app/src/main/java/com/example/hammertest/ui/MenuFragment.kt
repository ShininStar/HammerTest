package com.example.hammertest.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hammertest.R
import com.example.hammertest.adapters.MainAdapter
import com.example.hammertest.databinding.FragmentMenuBinding
import com.example.hammertest.models.Category
import com.example.hammertest.models.Dishe
import com.example.hammertest.viewmodels.BannerViewModel
import com.example.hammertest.viewmodels.DisheViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    //три адаптера для разных списков
    private lateinit var bannerAdapter: MainAdapter
    private lateinit var tagAdapter: MainAdapter
    private lateinit var disheAdapter: MainAdapter
    //две вью модели для двух моделей данных
    private val bannerModel: BannerViewModel by activityViewModels()
    private val disheModel: DisheViewModel by activityViewModels()
    //лист для обновления списка блюд по тегам
    private var tagedDishes: List<Dishe>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()
        getBanners()
    }

    /*инициализируем адаптеры и присваиваем их к спискам*/
    private fun init() = with(binding) {
        bannerAdapter = MainAdapter {item ->

        }
        //по нажатию на тэг список блюд обновляется по тегу
        tagAdapter = MainAdapter { tag ->
            onTagClick(tag)
        }
        disheAdapter = MainAdapter {dishe ->

        }
        rcViewBanner.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcViewBanner.adapter = bannerAdapter
        rcTags.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcTags.adapter = tagAdapter
        rcDishes.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcDishes.adapter = disheAdapter
    }

    //проверям есть ли соединение с интернетом, если нет берем последние данные из бд
    private fun getBanners() {
        if (isInternetAvailable(requireContext())) {
            CoroutineScope(Dispatchers.IO).launch {
                bannerModel.getBannerFromApi()
                disheModel.getBannerFromApi()
            }
        }
        else {
            CoroutineScope(Dispatchers.IO).launch {
                bannerModel.getBannerFromDb()
                disheModel.getBannerFromDb()
            }
        }
    }

    //следим за обновлением баннеров, блюд и тегов
    private fun observe() {
        bannerModel.liveDataBanner.observe(viewLifecycleOwner) { bannerModel ->
            bannerAdapter.items = bannerModel.categories
            bannerAdapter.notifyDataSetChanged()
        }
        disheModel.liveDataDishe.observe(viewLifecycleOwner) { disheModel ->
            /*когда получаем DisheModel пробегаемся по всем элементам фильтруем уникальные теги и
            добавляем их в списко*/
            val list = disheModel.dishes
                .flatMap { it.tegs }
                .distinct()
                .toList()
            //добавляем наш отфильтрованный списко тегов в адаптер
            tagAdapter.items = list
            //добавляем списко блюд в адаптер
            tagedDishes = disheModel.dishes
            disheAdapter.items = tagedDishes
            tagAdapter.notifyDataSetChanged()
            disheAdapter.notifyDataSetChanged()
        }
    }

    //по нажатию на тэг предеаем новый списко блюд по тегу в адаптер
    private fun onTagClick (tag: Any) {
        //фильтруем списко по нажатому тегу
        val filteredList = tagedDishes?.filter {
            it.tegs.contains(tag)
        }
        //отправляем в адаптер отфильтрованный список и обновляем адаптеры
        disheAdapter.items = filteredList
        disheAdapter.notifyDataSetChanged()
        tagAdapter.notifyDataSetChanged()
    }

    //проверка доступности сети интернет
    private fun isInternetAvailable(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MenuFragment()
    }
}