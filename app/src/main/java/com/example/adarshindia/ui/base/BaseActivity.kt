package com.example.adarshindia.ui.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adarshindia.bytebuddy.BuildConfig
import com.adarshindia.bytebuddy.R
import com.example.adarshindia.utils.Constant.PERMISSION_CODE
import com.example.adarshindia.utils.Utils
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


abstract class BaseActivity<VM : ViewModel, T : ViewDataBinding> : AppCompatActivity() {

    var mViewModel: VM? = null
    lateinit var viewDataBinding: T
    val image_livedata = MutableLiveData<Uri>()

    @LayoutRes
    abstract fun getLayout(): Int
    abstract fun getViewModelClass(): Class<VM>
    abstract fun showProgressBar()
    abstract fun hideProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayout())
        mViewModel = ViewModelProvider(this).get(getViewModelClass())
        viewDataBinding.lifecycleOwner = this
    }

    protected fun bindViewData(): T {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayout())
        mViewModel = ViewModelProvider(this).get(getViewModelClass())
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding
    }

    fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            window.decorView.rootView.windowToken, 0
        )
    }

    fun showSuccessSnackBar(msg: String) {
        hideKeyboard()
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(Color.GREEN).show()
    }


    fun showSuccessToast(msg: String) {
        hideKeyboard()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


    fun showErrorSnackBar(msg: String) {
        hideKeyboard()
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission(permission: String): Boolean {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(permission), PERMISSION_CODE)
        } else {
            return true
        }
        return false
    }

    fun getUri(): Uri {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        return FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID + ".fileprovider",
            image
        );
    }

    fun errorAlertDialog(msg: String) {
        val alertdialog = AlertDialog.Builder(this)
        alertdialog.setMessage(msg)
        alertdialog.setPositiveButton("ok") { dialog, which ->
            finish()
        }
        val dialog = alertdialog.create()
        dialog.show()
    }

    fun capturePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooser = Intent.createChooser(galleryIntent, getString(R.string.chooseone))
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        imageLauncher.launch(chooser)
    }

    var imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data!!.data?.let { // Gallary
                    val uri: Uri = result.data?.data!!
                    image_livedata.value = uri
                } ?: kotlin.run { // Camera
                    val uri = Utils.getImageUriFromBitmap(
                        this,
                        result.data!!.extras?.get("data")!! as Bitmap
                    )
                    image_livedata.value = uri
                }
            }
        }

}