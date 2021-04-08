package jp.arai.pingpongcounter

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import jp.arai.pingpongcounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                setLifecycleOwner(this@MainActivity)
                viewModel = this@MainActivity.viewModel
            }
        viewModel.init()

        checkEditTexts()

        observeViewModel()

        setSwipeListener()
    }


    fun checkEditTexts() {
        binding.etPlayerA.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    viewModel.playerNameA.postValue(it.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.etPlayerB.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    viewModel.playerNameB.postValue(it.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun observeViewModel() {
        viewModel.winner.observe(this, Observer {
            it?.let {
                when (it) {
                    MainViewModel.Winner.A -> {
                        WinnerCheckDialogFragment.show(
                            supportFragmentManager,
                            viewModel.playerNameA.value!!,
                            DialogInterface.OnClickListener { _, _ ->
                                viewModel.incrementMatchA()
                                viewModel.resetScores()
                                viewModel.changeFirstServer()
                            },
                            DialogInterface.OnClickListener { _, _ ->
                                viewModel.decrementPointA()
                            })
                    }
                    MainViewModel.Winner.B -> {
                        WinnerCheckDialogFragment.show(
                            supportFragmentManager,
                            viewModel.playerNameB.value!!,
                            DialogInterface.OnClickListener { _, _ ->
                                viewModel.incrementMatchB()
                                viewModel.resetScores()
                                viewModel.changeFirstServer()
                            },
                            DialogInterface.OnClickListener { _, _ ->
                                viewModel.decrementPointB()
                            })
                    }
                    MainViewModel.Winner.None -> {
                        // Do Nothing
                    }
                }
            }
        })
    }

    var y1: Float = 0f
    var y2: Float = 0f
    private val MIN_DISTANCE = 150

    @SuppressLint("ClickableViewAccessibility")
    private fun setSwipeListener() {
        binding.pointA.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    y1 = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    y2 = motionEvent.y
                    val deltaY = y2- y1

                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        if (y1 > y2) {
                            viewModel.incrementPointA()
                        } else {
                            viewModel.decrementPointA()
                        }
                    } else {
                        // Do nothing
                    }
                }
            }
            true
        }
        binding.pointB.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    y1 = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    y2 = motionEvent.y
                    val deltaY = y2- y1

                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        if (y1 > y2) {
                            viewModel.incrementPointB()
                        } else {
                            viewModel.decrementPointB()
                        }
                    } else {
                        // Do nothing
                    }
                }
            }
            true
        }
        binding.matchA.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    y1 = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    y2 = motionEvent.y
                    val deltaY = y2- y1

                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        if (y1 > y2) {
                            viewModel.incrementMatchA()
                        } else {
                            viewModel.decrementMatchA()
                        }
                    } else {
                        // Do nothing
                    }
                }
            }
            true
        }
        binding.matchB.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    y1 = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    y2 = motionEvent.y
                    val deltaY = y2- y1

                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        if (y1 > y2) {
                            viewModel.incrementMatchB()
                        } else {
                            viewModel.decrementMatchB()
                        }
                    } else {
                        // Do nothing
                    }
                }
            }
            true
        }
    }
}