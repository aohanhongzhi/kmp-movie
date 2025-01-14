package ui.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState

class UpcomingViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _upComingMovieResponse =
        MutableStateFlow<UiState<List<MovieItem>>>(UiState.Loading)
    val upComingMovieResponse get() = _upComingMovieResponse.asStateFlow()

    init {
        upComing(1)
    }

    fun upComing(page: Int) {
        viewModelScope.launch {
            repo.upComingMovie(page).onEach {
                _upComingMovieResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}