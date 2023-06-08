package me.shouheng.icamera.util


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
//import me.shouheng.uix.common.UIX

/**
 * UIX Image 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object UImage {

    /**
     * 对 Drawable 进行着色
     */
    fun tintDrawable(context: Context,@DrawableRes drawableRes: Int, @ColorInt color: Int): Drawable {
        val drawable = context.resources.getDrawable(drawableRes)
        return tintDrawable(drawable, color)
    }

    /**
     * 对 Drawable 进行着色
     *
     * @param drawable 要着色的 Drawable
     * @param color    颜色
     * @return         着色之后的 Drawable
     */
    fun tintDrawable(drawable: Drawable, @ColorInt color: Int): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable.mutate())
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color))
        return wrappedDrawable
    }

    /**
     * 获取带圆角的 drawable，各个圆角都为 [radius]
     */
    fun getGradientDrawable(@ColorInt color: Int, radius: Float) =
        GradientDrawable().apply {
            setColor(color)
            cornerRadius = radius
        }

    /**
     * 获取带圆角的 drawable
     */
    fun getGradientDrawable(@ColorInt color: Int,
                            topLeftRadius: Float,
                            topRightRadius: Float,
                            bottomLeftRadius: Float,
                            bottomRightRadius: Float) =
        GradientDrawable().apply {
            setColor(color)
            cornerRadii = floatArrayOf(
                topLeftRadius, topLeftRadius,
                topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius,
                bottomLeftRadius, bottomLeftRadius
            )
        }
}
