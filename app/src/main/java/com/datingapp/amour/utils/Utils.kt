package com.datingapp.amour.utils

import java.security.MessageDigest

object Utils {
    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun normalizePhone(phone: String): String {
        var normalized = phone.trim().replace(" ", "")
        if (normalized.startsWith("0")) normalized = "+27" + normalized.drop(1)
        if (!normalized.startsWith("+")) normalized = "+27$normalized"
        return normalized
    }
}
