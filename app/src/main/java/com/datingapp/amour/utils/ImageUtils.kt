package com.datingapp.amour.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

fun Context.saveImageLocally(uri: Uri, filename: String): String? {
    return try {
        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        val file = File(this.filesDir, "$filename.jpg")
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        fos.flush()
        fos.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
