package kz.garage.image.preview

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kz.garage.image.load.dispose
import kz.garage.image.load.load

class ImagePreviewDialogFragment : DialogFragment() {

    companion object {
        private val TAG = ImagePreviewDialogFragment::class.java.simpleName

        fun newInstance(
            uri: Uri,
            caption: String?
        ): ImagePreviewDialogFragment {
            val fragment = ImagePreviewDialogFragment()
            fragment.arguments = bundleOf(
                "uri" to uri.toString(),
                "caption" to caption
            )
            return fragment
        }

        fun show(
            fragmentManager: FragmentManager,
            uri: Uri,
            caption: String?
        ): ImagePreviewDialogFragment {
            val instance = newInstance(uri, caption)
            fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(android.R.id.content, instance)
                .addToBackStack(null)
                .commitAllowingStateLoss()
            return instance
        }
    }

    private var imageView: AppCompatImageView? = null
    private var closeButton: AppCompatImageButton? = null
    private var textView: AppCompatTextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.image_preview_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageView)
        closeButton = view.findViewById(R.id.closeButton)
        textView = view.findViewById(R.id.textView)

        val uri = Uri.parse(arguments?.getString("uri"))
        val caption = arguments?.getString("caption")

        imageView?.load(uri)

        if (caption.isNullOrBlank()) {
            textView?.text = null
            textView?.visibility = View.GONE
        } else {
            textView?.text = caption
            textView?.visibility = View.VISIBLE
        }

        closeButton?.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onDestroyView() {
        imageView?.dispose()
        imageView = null

        closeButton?.setOnClickListener(null)
        closeButton = null

        textView = null

        super.onDestroyView()
    }

}
