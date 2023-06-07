package me.shouheng.icamerasample.widget


import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

/**
 * UIX Color 工具类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 11:57
 */
object UColor {

    private const val ENABLE_ATTR = android.R.attr.state_enabled
    private const val CHECKED_ATTR = android.R.attr.state_checked
    private const val PRESSED_ATTR = android.R.attr.state_pressed

    /**
     * 根据比例，在两个 color 值之间计算出一个 color 值
     * **注意该方法是 ARGB 通道分开计算比例的**
     *
     * @param fromColor 开始的 color 值
     * @param toColor   最终的 color 值
     * @param fraction  比例，取值为 [0,1]，为 0 时返回 fromColor， 为 1 时返回 toColor
     * @return          计算出的 color 值
     */
    fun computeColor(@ColorInt fromColor: Int, @ColorInt toColor: Int, @FloatRange(from = 0.0, to = 1.0) fraction: Float): Int {
        var fractionInner = fraction
        fractionInner = fractionInner.coerceAtMost(1f).coerceAtLeast(0f)

        val minColorA = Color.alpha(fromColor)
        val maxColorA = Color.alpha(toColor)
        val resultA = ((maxColorA - minColorA) * fractionInner).toInt() + minColorA

        val minColorR = Color.red(fromColor)
        val maxColorR = Color.red(toColor)
        val resultR = ((maxColorR - minColorR) * fractionInner).toInt() + minColorR

        val minColorG = Color.green(fromColor)
        val maxColorG = Color.green(toColor)
        val resultG = ((maxColorG - minColorG) * fractionInner).toInt() + minColorG

        val minColorB = Color.blue(fromColor)
        val maxColorB = Color.blue(toColor)
        val resultB = ((maxColorB - minColorB) * fractionInner).toInt() + minColorB

        return Color.argb(resultA, resultR, resultG, resultB)
    }

    fun setColorAlpha(@ColorInt color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float, override: Boolean): Int {
        val origin = if (override) 0xff else color shr 24 and 0xff
        return color and 0x00ffffff or ((alpha * origin).toInt() shl 24)
    }

    fun generateThumbColorWithTintColor(tintColor: Int): ColorStateList {
        val states = arrayOf(intArrayOf(-ENABLE_ATTR, CHECKED_ATTR), intArrayOf(-ENABLE_ATTR), intArrayOf(PRESSED_ATTR, -CHECKED_ATTR), intArrayOf(PRESSED_ATTR, CHECKED_ATTR), intArrayOf(CHECKED_ATTR), intArrayOf(-CHECKED_ATTR))

        val colors = intArrayOf(tintColor - -0x56000000, -0x454546, tintColor - -0x67000000, tintColor - -0x67000000, tintColor or -0x1000000, -0x111112)
        return ColorStateList(states, colors)
    }

    fun generateBackColorWithTintColor(tintColor: Int): ColorStateList {
        val states = arrayOf(intArrayOf(-ENABLE_ATTR, CHECKED_ATTR), intArrayOf(-ENABLE_ATTR), intArrayOf(CHECKED_ATTR, PRESSED_ATTR), intArrayOf(-CHECKED_ATTR, PRESSED_ATTR), intArrayOf(CHECKED_ATTR), intArrayOf(-CHECKED_ATTR))

        val colors = intArrayOf(tintColor - -0x1f000000, 0x10000000, tintColor - -0x30000000, 0x20000000, tintColor - -0x30000000, 0x20000000)
        return ColorStateList(states, colors)
    }
}
