package com.example.adarshindia.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


object Utils {
    private val TAG = Utils::class.simpleName

    fun hideKeyboard(context: Activity) {
        // Check if no view has focus
        val view = context.currentFocus
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showHidePassword(imageView: ImageView, editText: EditText) {
        imageView.setOnClickListener {
            if (editText.transformationMethod.javaClass.name.equals("PasswordTransformationMethod")
            ) {
                editText.transformationMethod = SingleLineTransformationMethod()
            } else {
                editText.transformationMethod = PasswordTransformationMethod()
            }
            editText.setSelection(editText.text.length)
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            var imageUri : Uri? = null
            var fos: OutputStream? = null
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //this one
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
            }

            val contentResolver = context.contentResolver
            contentResolver.also { resolver ->
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
            fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }
            contentValues.clear()
            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
            contentResolver.update(imageUri!!, contentValues, null, null)
            return imageUri as Uri
        }
        else
        {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytes)
            val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "", null)
            return Uri.parse(path.toString())
        }

    }

    @SuppressLint("HardwareIds")
    fun getDeviceID(context : Context) : String
    {
        val id: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return id
    }

    @SuppressLint("HardwareIds")
    fun getSystemDetail1(context: Context): String {
        return "Brand: ${Build.BRAND} \n" +
                "DeviceID: ${
                    Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                } \n" +
                "Model: ${Build.MODEL} \n" +
                "Brand: ${Build.BRAND} \n" +
                "Version Code: ${Build.VERSION.RELEASE}"
    }
    @SuppressLint("HardwareIds")
    fun getSystemDetail(context: Context): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        val versionRelease = Build.VERSION.RELEASE
        Log.e("VersionCode", versionRelease + " " + manufacturer + " " + model)
        val deviceModel = capitalize(manufacturer).toString() + " " + model
        return "ANDROID  " + versionRelease + " " + deviceModel
    }

    private fun capitalize(s: String?): String? {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bitmapToByteArray(bitmap : Bitmap) : String
    {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encoded: String = Base64.getEncoder().encodeToString(byteArray)
        return encoded
    }

    fun uriToBitmap(uri : String, context : Context) : Bitmap
    {
        val bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(uri))
        return bitmap
    }

    fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun getPath(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.KITKAT
        Log.i("URI", uri.toString() + "")
        val result = uri.toString() + ""
        // DocumentProvider
        //  if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isKitKat && result.contains("media.documents")) {
            val ary = result.split("/").toTypedArray()
            val length = ary.size
            val imgary = ary[length - 1]
            val dat = imgary.split("%3A").toTypedArray()
            val docId = dat[1]
            val type = dat[0]
            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
            } else if ("audio" == type) {
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(
                dat[1]
            )
            return getDataColumn(context, contentUri, selection, selectionArgs)
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }


    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun uriToByteArray(context: Context, uri : Uri) : ByteArray?
    {
        val inputData = context.contentResolver.openInputStream(uri)?.readBytes()
//        val iStream: InputStream = context.getContentResolver().openInputStream(uri)!!
//        val inputData = getBytes(iStream)
        return inputData
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    fun fileToByteArray(path:String) : ByteArray {
        var encoded : ByteArray = byteArrayOf()
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encoded = Files.readAllBytes(Paths.get(path))
            } else {

            }
        } catch (e: IOException) {

        }
        return encoded
    }

}
