package com.datingapp.amour.utils

import java.security.MessageDigest

/**
 * Utility object with helper functions for password hashing and phone normalization.
 */
object Utils {

    /**
     * Hashes a password using SHA-256.
     * Returns the hexadecimal string representation of the hash.
     */
    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Normalizes phone number for Firebase.
     * Ensures it starts with + and country code.
     * Example: "0712345678" (South Africa) -> "+27712345678"
     */
    fun normalizePhone(phone: String): String {
        var normalized = phone.trim().replace(" ", "")

        // If the user entered 0 at the start, assume local format (South Africa)
        if (normalized.startsWith("0")) {
            normalized = "+27" + normalized.drop(1)
        }

        // If no + sign at all, default to +27
        if (!normalized.startsWith("+")) {
            normalized = "+27$normalized"
        }

        return normalized
    }
}
