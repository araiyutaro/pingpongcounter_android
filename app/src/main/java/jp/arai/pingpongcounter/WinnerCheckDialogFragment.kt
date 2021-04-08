package jp.arai.pingpongcounter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class WinnerCheckDialogFragment(
    private val winner: String,
    private val positiveListener: DialogInterface.OnClickListener,
    private val negativeListener: DialogInterface.OnClickListener
) : DialogFragment() {
    companion object {
        private const val TAG = "WinnerCheck"
        fun show(fragmentManager: FragmentManager, winner: String, positiveListener: DialogInterface.OnClickListener, negativeListener: DialogInterface.OnClickListener) {
            WinnerCheckDialogFragment( winner, positiveListener, negativeListener).show(fragmentManager, TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        
        return AlertDialog.Builder(activity!!)
            .setMessage("$winner is Winner?")
            .setPositiveButton("Yes", positiveListener)
            .setNegativeButton("No", negativeListener)
            .create()
    }
}