package com.orcaella.mymusicplayer.presentation.searchmusic

import androidx.lifecycle.ViewModel
import com.orcaella.mymusicplayer.domain.GetMusicListUseCase
import com.orcaella.mymusicplayer.presentation.State
import com.orcaella.mymusicplayer.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getMusicListUseCase: GetMusicListUseCase
) : ViewModel() {

    fun getMusicList(searchkey: String) = flow {
        emit(State.LoadingState)
        try {
            delay(1000)
            emit(State.DataState(getMusicListUseCase(searchkey)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }
}