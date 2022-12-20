package com.udacity

import android.animation.ValueAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.content_main.view.*
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var loadingBtnColor = 0
    private var btnColor = 0
    private var textColor = 0
    private var loadingCircularColor = 0
    private val anim = ValueAnimator.ofFloat(0f,360f)
    private var text = resources.getString(R.string.button_download)
    private var statusLoader = 0f


    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        if(new == ButtonState.Loading)
        {
            anim.start()
            text =  resources.getString(R.string.button_loading)
            invalidate()

        }
        else
        {
            statusLoader = 0f
            text =  resources.getString(R.string.button_download)
            anim.cancel()
            invalidate()
        }
    }


    private val paint = Paint().apply {

        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = resources.getDimension(R.dimen.default_text_size)
    }


    init {
        loadingBtnColor = ResourcesCompat.getColor(resources, android.R.color.darker_gray, null)
        btnColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        textColor = ResourcesCompat.getColor(resources, R.color.white, null)
        loadingCircularColor =ResourcesCompat.getColor(resources, R.color.colorAccent, null)

        anim.apply {
            duration = 2000
            addUpdateListener { ani ->
                statusLoader = ani.animatedValue as Float
                invalidate()
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = btnColor
        canvas?.drawRect(0f, 0f, custom_button.widthSize.toFloat(), custom_button.heightSize.toFloat(), paint)

        paint.color = loadingBtnColor
        canvas?.drawRect(0f, 0f,widthSize.toFloat()*statusLoader/360, heightSize.toFloat(), paint)

        paint.color = textColor
        canvas?.drawText(text,custom_button.widthSize/2f, custom_button.heightSize/2f+18,paint)

        paint.color = loadingCircularColor
        canvas?.drawArc(custom_button.widthSize-200f, custom_button.heightSize/2f-50, custom_button.widthSize - 100f, custom_button.heightSize/2f+50, 0f, statusLoader, true, paint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }


}