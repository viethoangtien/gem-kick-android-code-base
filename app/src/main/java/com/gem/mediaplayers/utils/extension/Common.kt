package com.gem.mediaplayers.utils.extension

import android.content.Context
import com.gem.mediaplayers.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.regex.Pattern
import kotlin.math.roundToInt

fun Float.format(maximumFractionDigits: Int): Float {
    val df = DecimalFormat()
    df.maximumFractionDigits = maximumFractionDigits
    return df.format(this).replace(",", ".").toFloat()
}

// Extension method
// Get all index of character
fun CharSequence.indicesOf(input: String): List<Int> =
    Regex(Pattern.quote(input)) // build regex
        .findAll(this)          // get the matches
        .map { it.range.first } // get the index
        .toCollection(mutableListOf()) // collect the result as list

fun Int.roundInt(): Int {
    return (this * 1.0f / 10).roundToInt() * 10
}

fun String.toMd5(): String {
    val MD5 = "MD5"
    try { // Create MD5 Hash
        val digest = MessageDigest
            .getInstance(MD5)
        digest.update(this.toByteArray())
        val messageDigest = digest.digest()
        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

fun String?.getFullUrlOfInternalImage(context: Context): String {
    if (this == null) return ""
    if (this.isBlank() || this.contains("http://") || this.contains("https://"))
        return this

    return context.resources.getString(R.string.base_url) + this.substring(1, this.length)
}