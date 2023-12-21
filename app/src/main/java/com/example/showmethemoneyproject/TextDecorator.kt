import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.LineBackgroundSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TextDecorator(private val context: Context) : DayViewDecorator {
    private val textPaint: Paint

    init {
        textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = 24f
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        //일자에 따라 정수 표시
        return true //모든 일자에 텍스트 표시
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(CustomSpan())
    }

    private inner class CustomSpan : LineBackgroundSpan {
        override fun drawBackground(
            canvas: Canvas, paint: Paint,
            left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
            charSequence: CharSequence, start: Int, end: Int, lineNum: Int
        ) {
            // 일자 아래에 텍스트를 그립니다.
            val text = "테스트"
            val x = (left + right) / 2f
            val y = bottom + textPaint.textSize
            canvas.drawText(text, x, y, textPaint)
        }
    }
}