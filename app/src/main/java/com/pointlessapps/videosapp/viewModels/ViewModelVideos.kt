package com.pointlessapps.videosapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pointlessapps.videosapp.App
import com.pointlessapps.videosapp.models.Video
import com.pointlessapps.videosapp.repositories.RepositoryVideos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewModelVideos(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData(State.Empty)
    val state: LiveData<State> = _state

    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())
    private val repositoryVideos = RepositoryVideos(application as App)

    private val _events = MutableLiveData<List<Video>>()
    val events: LiveData<List<Video>> = _events
    private val _schedule = MutableLiveData<List<Video>>()
    val schedule: LiveData<List<Video>> = _schedule

    fun refreshEvents() {
        coroutineScope.launch {
            _state.postValue(State.Loading)
            _events.postValue(repositoryVideos.getEvents())
            _state.postValue(State.Empty)
        }
    }

    fun refreshSchedule() {
        coroutineScope.launch {
            repositoryVideos.getSchedule({
                _state.postValue(State.Loading)
            }).collect {
                _schedule.postValue(it)
                _state.postValue(State.Empty)
            }
        }
    }

    enum class State {
        Loading, Empty
    }
}