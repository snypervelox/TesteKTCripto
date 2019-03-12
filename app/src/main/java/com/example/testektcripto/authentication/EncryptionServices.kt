package com.example.testektcripto.authentication

import com.example.testektcripto.encryption.CipherWrapper
import com.example.testektcripto.encryption.KeyStoreWrapper

class EncryptionServices {

    companion object {
        const val CHAVE_EDUARDO = "CHAVE_EDUARDO"
        const val CHAVE_FLAVIO = "CHAVE_FLAVIO"
        const val CHAVE_RAFAEL = "CHAVE_RAFAEL"
    }

    private val keyStoreWrapper = KeyStoreWrapper()

    fun createMasterKey() {
        keyStoreWrapper.createAndroidKeyStoreAsymmetricKey(CHAVE_EDUARDO)
        keyStoreWrapper.createAndroidKeyStoreAsymmetricKey(CHAVE_FLAVIO)
        keyStoreWrapper.createAndroidKeyStoreAsymmetricKey(CHAVE_RAFAEL)
    }

    fun removeMasterKey() {
        keyStoreWrapper.removeAndroidKeyStoreKey(CHAVE_EDUARDO)
        keyStoreWrapper.removeAndroidKeyStoreKey(CHAVE_FLAVIO)
        keyStoreWrapper.removeAndroidKeyStoreKey(CHAVE_RAFAEL)
    }

    fun encrypt(data: String, MASTER_KEY: String): String {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC).encrypt(data, masterKey?.public)
    }

    fun decrypt(data: String, MASTER_KEY: String): String {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC).decrypt(data, masterKey?.private)
    }
}