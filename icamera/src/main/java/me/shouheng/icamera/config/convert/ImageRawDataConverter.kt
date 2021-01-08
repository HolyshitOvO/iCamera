package me.shouheng.icamera.config.convert

import android.media.Image

/**
 * The image data convert.
 *
 * @author Jeff
 * @time 2020/10/12 23:46
 */
interface ImageRawDataConverter {
    /**
     * Convert from given format to NV21.
     *
     * @param image the image data
     * @return      the image data in bytes
     */
    fun convertToNV21(image: Image): ByteArray
}