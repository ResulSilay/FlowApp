package app.flow.ui.helper.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import app.flow.R

class DialogMessage(private val activity: AppCompatActivity) : DialogFragment(), View.OnClickListener {

    private lateinit var txtDesc: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (dialog != null && dialog!!.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
            params.width = LinearLayout.LayoutParams.MATCH_PARENT
            params.height = LinearLayout.LayoutParams.MATCH_PARENT
            dialog!!.window!!.attributes = params as WindowManager.LayoutParams?
        }

        val view = inflater.inflate(R.layout.dialog_message, container, false)
        txtDesc = view.findViewById(R.id.txt_dialog_message_desc)
        dialog?.setContentView(view)

        return view
    }

    fun show(message: String) {
        try {
            txtDesc.text = message
            show(activity.supportFragmentManager, "dialog")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_dialog_messsage_ok -> {
                dialog?.dismiss()
            }
        }
    }
}