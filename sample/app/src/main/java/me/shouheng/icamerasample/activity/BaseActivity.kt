package me.shouheng.icamerasample.activity

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

	private val neededPermissions = arrayOf(
		Manifest.permission.READ_EXTERNAL_STORAGE,
		Manifest.permission.CAMERA,
		Manifest.permission.WRITE_EXTERNAL_STORAGE
	)

	/** 当前是否第一次执行 onWindowFocusChanged 方法，此方法在界面渲染完成后调用  */
	protected var isOnWindowFocusChangedFirst = true

	public override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		tools.setFullScreenUI(this) // 设置全屏
//		tools.setKeepScreenOn(this) // 保持屏幕常亮
		// if (ContextCompat.checkSelfPermission(this, neededPermissions[0]) != PackageManager.PERMISSION_GRANTED ||
		// 	ContextCompat.checkSelfPermission(this, neededPermissions[1]) != PackageManager.PERMISSION_GRANTED ||
		// 	ContextCompat.checkSelfPermission(this, neededPermissions[2]) != PackageManager.PERMISSION_GRANTED
		// ) {
		// 	ActivityCompat.requestPermissions(this, neededPermissions, 1)
		// }
	}


	override fun onWindowFocusChanged(hasFocus: Boolean) {
		super.onWindowFocusChanged(hasFocus)
		if (isOnWindowFocusChangedFirst) {
			isOnWindowFocusChangedFirst = false
			onWindowFocusChangedFirst()
		}
//		tools.setFullScreenUI(this) // 设置全屏
//		tools.setKeepScreenOn(this) // 保持屏幕常亮
	}

	open fun onWindowFocusChangedFirst() {}

	override fun onResume() {
		super.onResume()
//		tools.setFullScreenUI(this) // 设置全屏
	}

	private var toast: Toast? = null

	open fun toast(text: String?) = showToast(text, true)
	open fun showToast(text: String?) = showToast(text, true)
	open fun showLongToast(text: String?) = showToast(text, false)
	open fun showToast(text: String?, isLengthShort: Boolean) {
		if (toast != null) {
			toast?.cancel()
		}
		toast = Toast.makeText(this, text, if (isLengthShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
		toast?.show()
	}

	/** 关闭Activity按钮的onClick方法  */
	open fun finishApp(view: View) = finish()

}
