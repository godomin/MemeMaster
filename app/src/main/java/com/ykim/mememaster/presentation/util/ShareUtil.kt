package com.ykim.mememaster.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.shareMeme(uriList: List<Uri>) {
    uriList.forEach { uri ->
        grantUriPermission(
            packageName,
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND_MULTIPLE
        type = "image/*"
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uriList))
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(Intent.createChooser(shareIntent, "Share images"))
}