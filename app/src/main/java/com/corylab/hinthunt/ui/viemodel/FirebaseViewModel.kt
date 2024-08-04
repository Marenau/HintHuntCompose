package com.corylab.hinthunt.ui.viemodel;

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.corylab.hinthunt.data.repository.OnlineWordsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FirebaseViewModel(private val onlineWordsRepository: OnlineWordsRepository) : ViewModel() {

    private val _words = MutableStateFlow<List<String>>(emptyList())
    val words: StateFlow<List<String>> = _words.asStateFlow()

    private val _selectedWords = MutableStateFlow<List<Boolean>>(emptyList())
    val selectedWords: StateFlow<List<Boolean>> = _selectedWords.asStateFlow()

    private val _colorNums = MutableStateFlow<List<Int>>(emptyList())
    val colorNums: StateFlow<List<Int>> = _colorNums.asStateFlow()

    private val _firstNumOfCards = MutableStateFlow(0)
    val firstNumOfCards: StateFlow<Int> = _firstNumOfCards.asStateFlow()
    private val _secondNumOfCards = MutableStateFlow(0)
    val secondNumOfCards: StateFlow<Int> = _secondNumOfCards.asStateFlow()

    private val _firstScore = MutableStateFlow(0)
    val firstScore: StateFlow<Int> = _firstScore.asStateFlow()
    private val _secondScore = MutableStateFlow(0)
    val secondScore: StateFlow<Int> = _secondScore.asStateFlow()

    private val _turn = MutableStateFlow(0)
    val turn: StateFlow<Int> = _turn.asStateFlow()
    private val _winner = MutableStateFlow(0)
    val winner: StateFlow<Int> = _winner.asStateFlow()
    private val _complexity = MutableStateFlow(0)
    val complexity: StateFlow<Int> = _complexity.asStateFlow()
    private val _lang = MutableStateFlow(0)
    val lang: StateFlow<Int> = _lang.asStateFlow()

    private val _size = MutableStateFlow(0)
    val size: StateFlow<Int> = _size.asStateFlow()

    private val _teamsColors = MutableStateFlow(0)
    val teamsColors: StateFlow<Int> = _teamsColors.asStateFlow()

    fun initiateKey(key: String) = onlineWordsRepository.initiateKey(key)

    fun putWords(words: List<String>) = onlineWordsRepository.putWords(words)

    fun putNumOfCards(command: Int, num: Int) = onlineWordsRepository.putNumOfCards(command, num)

    fun putScore(command: Int, score: Int) = onlineWordsRepository.putScore(command, score)

    fun putTurn(turn: Int) = onlineWordsRepository.putTurn(turn)

    fun putColorNums(colors: List<Int>) = onlineWordsRepository.putColorNums(colors)

    fun putTeamsColors(numOfColors: Int) = onlineWordsRepository.putTeamsColors(numOfColors)

    fun putSelectedColor(selectedColors: List<Boolean>) =
        onlineWordsRepository.putSelectedColor(selectedColors)

    fun putWinner(winner: Int) = onlineWordsRepository.putWinner(winner)

    fun putSelectedColor(index: Int, state: Boolean) =
        onlineWordsRepository.putSelectedColor(index, state)

    private fun putComplexity(complexity: Int) = onlineWordsRepository.putComplexity(complexity)

    private fun putLang(lang: Int) = onlineWordsRepository.putLang(lang)

    private fun putSize(size: Int) = onlineWordsRepository.putSize(size)

    fun dropRoom(room: String, listener: () -> Unit) = onlineWordsRepository.dropRoom(room, listener)

    fun getWords() {
        viewModelScope.launch {
            onlineWordsRepository.getWords()
                .collect {
                    _words.value = it
                }
        }
    }

    fun getNumOfCards(command: Int) {
        viewModelScope.launch {
            onlineWordsRepository.getNumOfCards(command).collect {
                if (command == 1) _firstNumOfCards.value = it
                else _secondNumOfCards.value = it
            }
        }
    }

    fun getScore(command: Int) {
        viewModelScope.launch {
            onlineWordsRepository.getScore(command).collect {
                if (command == 1) _firstScore.value = it
                else _secondScore.value = it
            }
        }
    }

    fun getTurn() {
        viewModelScope.launch {
            onlineWordsRepository.getTurn().collect {
                _turn.value = it
            }
        }
    }

    fun getColorNums() {
        viewModelScope.launch {
            onlineWordsRepository.getColorNums().collect {
                _colorNums.value = it
            }
        }
    }

    fun getTeamsColors() {
        viewModelScope.launch {
            onlineWordsRepository.getTeamsColors().collect {
                _teamsColors.value = it
            }
        }
    }

    fun getWinner() {
        viewModelScope.launch {
            onlineWordsRepository.getWinner().collect {
                _winner.value = it
            }
        }
    }

    fun getSelectedColors() {
        viewModelScope.launch {
            onlineWordsRepository.getSelectedColors()
                .collect {
                    _selectedWords.value = it
                }
        }
    }

    fun getComplexity() {
        viewModelScope.launch {
            onlineWordsRepository.getComplexity()
                .collect {
                    _complexity.value = it
                }
        }
    }

    fun getLang() {
        viewModelScope.launch {
            onlineWordsRepository.getLang()
                .collect {
                    _lang.value = it
                }
        }
    }

    fun getSize() {
        viewModelScope.launch {
            onlineWordsRepository.getSize()
                .collect {
                    _size.value = it
                }
        }
    }

    class FirebaseViewModelFactory(
        private val context: Context,
        private val onlineWordsRepository: OnlineWordsRepository
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FirebaseViewModel(onlineWordsRepository) as T
    }

    fun createGame(key: String, words: List<String>, firstNumOfCard: Int, secondNumOfCard: Int, colorsNums: List<Int>, numOfColors: Int, complexity: Int, lang: Int) {
        initiateKey(key)
        putWords(words)
        putComplexity(complexity)
        putLang(lang)
        val size = words.size
        putSize(size)
        putNumOfCards(1, firstNumOfCard)
        putNumOfCards(2, secondNumOfCard)
        putScore(1, 0)
        putScore(2, 0)
        putTurn(if (colorsNums.count { it == 1 } > colorsNums.count { it == 2 }) 1 else 2)
        putWinner(0)
        putColorNums(colorsNums.toList())
        putTeamsColors(numOfColors)
        putSelectedColor(List(size) {false})
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val job = SupervisorJob()

    private var getWordsJob: Job? = null
    private var getNumOfCardsJob1: Job? = null
    private var getNumOfCardsJob2: Job? = null
    private var getScoreJob1: Job? = null
    private var getScoreJob2: Job? = null
    private var getTurnJob: Job? = null
    private var getColorNumsJob: Job? = null
    private var getTeamsColorsJob: Job? = null
    private var getWinnerJob: Job? = null
    private var getSelectedColorsJob: Job? = null
    private var getComplexityJob: Job? = null
    private var getLangJob: Job? = null
    private var getSizeJob: Job? = null
    private var getRoomJob: Job? = null

    fun cleanUp() {
        getWordsJob?.cancel()
        getNumOfCardsJob1?.cancel()
        getNumOfCardsJob2?.cancel()
        getScoreJob1?.cancel()
        getScoreJob2?.cancel()
        getTurnJob?.cancel()
        getColorNumsJob?.cancel()
        getTeamsColorsJob?.cancel()
        getWinnerJob?.cancel()
        getSelectedColorsJob?.cancel()
        getComplexityJob?.cancel()
        getLangJob?.cancel()
        getSizeJob?.cancel()
        getRoomJob?.cancel()
        job.cancel()
        coroutineScope.coroutineContext.cancelChildren()
    }
}