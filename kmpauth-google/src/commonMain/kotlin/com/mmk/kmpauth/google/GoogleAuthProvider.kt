package com.mmk.kmpauth.google

import androidx.compose.runtime.Composable
import com.mmk.kmpauth.core.di.KMPKoinComponent
import com.mmk.kmpauth.core.di.LibDependencyInitializer
import com.mmk.kmpauth.google.di.googleAuthModule
import org.koin.core.component.get

public interface GoogleAuthProvider {

    public companion object {
        public fun create(credentials: GoogleAuthCredentials): GoogleAuthProvider {
            return GoogleAuthProviderImpl.create(credentials)
        }

        internal fun get(): GoogleAuthProvider {
            return GoogleAuthProviderImpl.get()
        }
    }

    @Composable
    public fun getUiProvider(): GoogleAuthUiProvider

    public suspend fun signOut()

    private object GoogleAuthProviderImpl : KMPKoinComponent() {
        fun create(credentials: GoogleAuthCredentials): GoogleAuthProvider {
            LibDependencyInitializer.initialize(googleAuthModule(credentials = credentials))
            return (this as KMPKoinComponent).get()
        }

        fun get(): GoogleAuthProvider {
            try {
                return (this as KMPKoinComponent).get()
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Make sure you invoked GoogleAuthProvider #create method with providing credentials")
            }

        }

    }
}
