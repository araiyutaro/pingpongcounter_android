package jp.arai.pingpongcounter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val firstServerIsA: MutableLiveData<Boolean> = MutableLiveData()
    val serverIsA: MutableLiveData<Boolean> = MutableLiveData()
    val matchA: MutableLiveData<Int> = MutableLiveData()
    val matchB: MutableLiveData<Int> = MutableLiveData()
    val pointA: MutableLiveData<Int> = MutableLiveData()
    val pointB: MutableLiveData<Int> = MutableLiveData()
    val playerNameA: MutableLiveData<String> = MutableLiveData()
    val playerNameB: MutableLiveData<String> = MutableLiveData()
    val winner: MutableLiveData<Winner> = MutableLiveData()

    enum class Winner {
        A, B, None
    }

    fun changeFirstServer() {
        firstServerIsA.value = !firstServerIsA.value!!
        serverIsA.value = firstServerIsA.value
    }

    fun init() {
        playerNameA.value = "たろ"
        playerNameB.value = "きよ"

        resetAll()
    }

    fun resetAll() {
        firstServerIsA.value = true
        serverIsA.value = true
        matchA.value = 0
        matchB.value = 0
        pointA.value = 0
        pointB.value = 0
    }

    fun resetScores() {
        pointA.value = 0
        pointB.value = 0
    }

    fun changeEnds() {
        var tmp = pointA.value
        pointA.value = pointB.value
        pointB.value = tmp

        tmp = matchA.value
        matchA.value = matchB.value
        matchB.value = tmp

        val tmpName = playerNameA.value
        playerNameA.value = playerNameB.value
        playerNameB.value = tmpName
    }

    fun incrementMatchA() {
        matchA.value = matchA.value!! + 1
    }

    fun incrementMatchB() {
        matchB.value = matchB.value!! + 1
    }

    fun incrementPointA() {
        val sum = pointA.value!! + pointB.value!!
        if (sum < 20 && pointA.value!! >= 11) {
            return
        }
        if (sum >= 20 && pointA.value!! >= pointB.value!! + 2) {
            return
        }

        pointA.value = pointA.value!! + 1
        checkServer()
        checkWinner()
    }

    fun incrementPointB() {
        val sum = pointA.value!! + pointB.value!!
        if (sum < 20 && pointB.value!! >= 11) {
            return
        }
        if (sum >= 20 && pointB.value!! >= pointA.value!! + 2) {
            return
        }

        pointB.value = pointB.value!! + 1
        checkServer()
        checkWinner()
    }

    fun decrementPointA() {
        pointA.value = pointA.value!! - 1
        checkServer()
    }

    fun decrementPointB() {
        pointB.value = pointB.value!! - 1
        checkServer()
    }


    private fun checkServer() {
        val sum = pointA.value!! + pointB.value!!
        if (sum >= 20) {
            if (sum % 2 == 0) {
                serverIsA.value = firstServerIsA.value
            } else {
                serverIsA.value = !firstServerIsA.value!!
            }
            return
        }

        if (sum % 4 <= 1) {
            serverIsA.value = firstServerIsA.value
        } else {
            serverIsA.value = !firstServerIsA.value!!
        }
    }

    private fun checkWinner() {
        val pA = pointA.value!!
        val pB = pointB.value!!
        val sum = pA + pB

        if (sum < 20) {
            when {
                pA >= 11 -> {
                    winner.value = Winner.A
                }
                pB >= 11 -> {
                    winner.value = Winner.B
                }
                else -> {
                    winner.value = Winner.None
                }
            }
        } else {
            when {
                pA >= pB + 2 -> {
                    winner.value = Winner.A
                }
                pB >= pA + 2 -> {
                    winner.value = Winner.B
                }
                else -> {
                    winner.value = Winner.None
                }
            }
        }
    }
}