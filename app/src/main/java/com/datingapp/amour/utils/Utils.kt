package com.datingapp.amour.utils

import java.security.MessageDigest

object Utils {

    /**
     * Hashes a password using SHA-256
     */
    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Normalizes phone number for Firebase (ensures it starts with + and country code).
     * Example: "0712345678" (South Africa) → "+27712345678"
     */
    fun normalizePhone(phone: String): String {
        var normalized = phone.trim().replace(" ", "")

        // If the user entered 0 at the start, assume local format (South Africa in this example)
        if (normalized.startsWith("0")) {
            normalized = "+27" + normalized.drop(1) // Replace leading 0 with +27
        }

        // If no + sign at all, you can default to +27 or any other country code you want
        if (!normalized.startsWith("+")) {
            normalized = "+27$normalized"
        }

        return normalized
    }
}
