package jp.arai.pingpongcounter

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
}