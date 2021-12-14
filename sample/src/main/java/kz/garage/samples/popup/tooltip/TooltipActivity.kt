package kz.garage.samples.popup.tooltip

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kz.garage.R
import kz.garage.activity.view.bind
import kz.garage.kotlin.simpleName
import kz.garage.popup.tooltip.Position
import kz.garage.popup.tooltip.Tooltip
import kz.garage.window.dp2Px
import kotlin.math.roundToInt

class TooltipActivity : AppCompatActivity() {

    companion object {
        private val TAG = simpleName()
    }

    private val button by bind<MaterialButton>(R.id.button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_tooltip)

        button.setOnClickListener {
            Tooltip.on(it)
                .animation(R.anim.fade_in, R.anim.fade_out)
                .arrowSize(7.5F.dp2Px().roundToInt(), 12.5F.dp2Px().roundToInt())
                .borderMargin(30F.dp2Px().roundToInt())
                .clickToHide(true)
                .color(Color.parseColor("#0F0F2B"))
                .corner(10F.dp2Px().roundToInt())
                .padding(
                    10F.dp2Px().roundToInt(),
                    14F.dp2Px().roundToInt(),
                    10F.dp2Px().roundToInt(),
                    14F.dp2Px().roundToInt()
                )
                .position(Position.TOP)
                .text("Tooltip text")
                .textSize(13F)
                .shadow(0F, Color.parseColor("#00000000"))
                .show(5 * 1000L)
        }
    }

}