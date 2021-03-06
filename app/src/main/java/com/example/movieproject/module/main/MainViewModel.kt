package com.example.movieproject.module.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.data.MovieRepository
import com.example.movieproject.model.Movie
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private var context: Context? = null
    private var repository: MovieRepository? = null

    val moviesResultLiveData = MutableLiveData<Result<List<Movie>>>()
    val moviesLiveData = MutableLiveData<List<Movie>>()

    val errorMessageLiveData = MutableLiveData<String>()

    fun setContext(context: Context){
        repository = MovieRepository(context)
    }

    fun getNowPlayingMovies(){
        viewModelScope.launch {
            moviesResultLiveData.value = repository?.fetchNowPlayingMovies()
        }
    }

    fun searchMovies(query: String){
        viewModelScope.launch {
            if(query.length >= 3){
                moviesResultLiveData.value = repository?.searchMovies(query)
            }
        }
    }

    fun getUpcomingMovies(){
        viewModelScope.launch {
            moviesResultLiveData.value = repository?.fetchUpcomingMovies()
        }
    }

    fun getLatestMovies(){
        viewModelScope.launch {
            moviesResultLiveData.value = repository?.fetchLatestMovies()
        }
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            moviesResultLiveData.value = repository?.fetchPopularMovies()
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch {
            moviesResultLiveData.value = repository?.fetchTopRatedMovies()
        }
    }

    fun handleMovieListResponse(result: Result<List<Movie>>){
        if(result.isSuccess){
            moviesLiveData.value = result.getOrDefault(emptyList())
        } else {
            errorMessageLiveData.value = result.exceptionOrNull()?.message.orEmpty()
        }
    }
}