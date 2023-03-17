package com.sum.shop.utils.customs

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class Button(context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {

    init {
        typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
    }
}
