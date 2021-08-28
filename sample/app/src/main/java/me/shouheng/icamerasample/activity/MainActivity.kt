package me.shouheng.icamerasample.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import me.shouheng.icamera.config.ConfigurationProvider
import me.shouheng.icamera.config.creator.impl.*
import me.shouheng.icamera.util.CameraHelper
import me.shouheng.icamerasample.R
import me.shouheng.icamerasample.databinding.ActivityMainBinding
import me.shouheng.utils.ktx.checkPermissions
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.start
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.SPUtils
import me.shouheng.utils.ui.BarUtils
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * Main activity
 *
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/4/13 22:42
 */
class MainActivity : CommonActivity<EmptyViewModel, ActivityMainBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun doCreateView(savedInstanceState: Bundle?) {
        L.d("MainActivity", "doCreateView")
        BarUtils.setStatusBarLightMode(window, false)
        ConfigurationProvider.get().isDebug = true
        setSupportActionBar(binding.toolbar)

        binding.rbCamera1.setOnCheckedChangeListener { _, isChecked -> if (isChecked) { switchToCameraOption(0) } }
        binding.rbCamera2.setOnCheckedChangeListener { _, isChecked -> if (isChecked) { switchToCameraOption(1) } }
        binding.rbCamera.setOnCheckedChangeListener { _, isChecked -> if (isChecked) { switchToCameraOption(2) } }

        binding.rbSurface.setOnCheckedChangeListener { _, isChecked -> if (isChecked) { switchToPreviewOption(0) } }
        binding.rbTexture.setOnCheckedChangeListener { _, isChecked -> if (isChecked) { switchToPreviewOption(1) } }
        binding.rbPlatform.setOnCheckedChangeListener { _, isChecked -> if (isChecked) { switchToPreviewOption(2) } }

        switchToCameraOption(SPUtils.get().getInt("__camera_option", 2))
        switchToPreviewOption(SPUtils.get().getInt("__preview_option", 2))

        // pre-prepare camera2 params, this option will save few milliseconds while launch camera2
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConfigurationProvider.get().prepareCamera2(this)
        }

        binding.btnOpen.onDebouncedClick { openCamera() }
    }

    private fun openCamera() {
        checkPermissions({
            val cameras = CameraHelper.getCameras(context)
            when {
                cameras.isEmpty() -> {
                    toast("No camera on this device")
                }
                cameras.size == 1 -> {
                    val face = cameras[0]
                    ConfigurationProvider.get().defaultCameraFace = face
                    start(CameraActivity::class.java)
                    toast("Device only has one camera [$face]")
                }
                else -> {
                    start(CameraActivity::class.java)
                }
            }
        }, Permission.CAMERA, Permission.STORAGE, Permission.MICROPHONE)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun switchToCameraOption(option: Int) {
        ConfigurationProvider.get().cameraManagerCreator = when(option) {
            0 -> Camera1OnlyCreator()
            1 -> Camera2OnlyCreator()
            else -> CameraManagerCreatorImpl()
        }
        when(option) {
            0 -> binding.rbCamera1
            1 -> binding.rbCamera2
            else -> binding.rbCamera
        }.isChecked = true
        SPUtils.get().put("__camera_option", option)
    }

    private fun switchToPreviewOption(option: Int) {
        ConfigurationProvider.get().cameraPreviewCreator = when(option) {
            0 -> SurfaceViewOnlyCreator()
            1 -> TextureViewOnlyCreator()
            else -> CameraPreviewCreatorImpl()
        }
        when(option) {
            0 -> binding.rbSurface
            1 -> binding.rbTexture
            else -> binding.rbPlatform
        }.isChecked = true
        SPUtils.get().put("__preview_option", option)
    }
}