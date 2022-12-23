package com.weathercast.forecast.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weathercast.R
import com.weathercast.helperclasses.NoInternetException
import com.weathercast.util.*
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_weather_forecast.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast),
    SearchView.OnQueryTextListener {

    @Inject
    lateinit var weatherForecastViewModelCreator: Lazy<WeatherForecastViewModel>

    @Inject
    lateinit var dispatcherProviderImp: DispatcherProvider

    private val weatherForecastViewModel: WeatherForecastViewModel by lazy {
        getFragmentViewModel(weatherForecastViewModelCreator)
    }
    private lateinit var searchMenuItem: MenuItem
    private lateinit var searchView: SearchView
    private lateinit var weatherForecastAdapter: WeatherForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherForecastViewModel.getWeatherForecast()
        initializeRecyclerView()
        observeCityNameChange()
        observeWeatherForecastData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        searchMenuItem = menu.findItem(R.id.action_search)
        searchView = searchMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {
        if (!searchView.isIconified) {
            searchView.isIconified = true
        }
        searchMenuItem.collapseActionView()
        weatherForecastViewModel.getWeatherForecast(searchText ?: "unknown")
        searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(seacrhText: String?): Boolean {
        return false
    }

    private fun initializeRecyclerView() {
        rvWeatherForecast?.apply {
            weatherForecastAdapter = WeatherForecastAdapter(mutableListOf())
            adapter = weatherForecastAdapter
        }
    }

    private fun observeCityNameChange() {
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherForecastViewModel.cityNameState.collectLatest { cityName ->
                    (requireActivity() as? AppCompatActivity)?.supportActionBar?.run {
                        title = getString(R.string.screen_title, cityName.capitalize())
                    }
                }
            }
        }
    }

    private fun observeWeatherForecastData() {
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherForecastViewModel.cityWeatherForecastState.collectLatest { apiCallState ->
                    when (apiCallState) {
                        is ApiCallState.Loading -> {
                            rvWeatherForecast.gone()
                            progressBar.visible()
                        }

                        is ApiCallState.Success -> {
                            progressBar.gone()
                            tvApiCallStatus.gone()
                            rvWeatherForecast.visible()
                            weatherForecastAdapter.updateWeatherForecast(apiCallState.data.list)
                            rvWeatherForecast?.smoothScrollToPosition(0)
                        }

                        is ApiCallState.Error -> {
                            progressBar.gone()
                            tvApiCallStatus.visible()
                            rvWeatherForecast.gone()
                            if (apiCallState.exception is NoInternetException) {
                                tvApiCallStatus.text = getString(R.string.message_no_connectivity)
                            } else {
                                tvApiCallStatus.text = apiCallState.exception.message
                            }
                        }
                    }
                }
            }
        }
    }
}
