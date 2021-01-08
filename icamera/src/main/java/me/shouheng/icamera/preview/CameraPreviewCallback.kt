package me.shouheng.icamera.preview

/**
 * Camera preview callback
 *
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/4/14 9:47
 */
interface CameraPreviewCallback {
    /**
     * The method will be called when the preview is available.
     *
     * @param cameraPreview the camera preview.
     */
    fun onAvailable(cameraPreview: CameraPreview)
}