package com.huntergaming.classicsolitaire.data

interface Authentication {

    val isLoggedIn: Boolean

    /**
     * Validates a strong password.
     * Requirements are minimum of 8 characters, at least 1 upper case letter, at least one lower case letter, at least 1 number and one special character.
     *
     * @param password The password to check.
     * @return True if the password meets the minimum requirements.
     */
    fun validateStrongPassword(password: String): Boolean

    suspend fun createAccount(email: String, password: String): AuthenticationState
    fun deleteAccount(): AuthenticationState
    fun signIn(): AuthenticationState
    fun signOut(): AuthenticationState
}