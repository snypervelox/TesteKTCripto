package com.example.testektcripto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.testektcripto.authentication.EncryptionServices
import com.example.testektcripto.encryption.CipherWrapper
import com.example.testektcripto.encryption.KeyStoreWrapper
import java.security.PublicKey

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cripto = EncryptionServices()
        val ks = KeyStoreWrapper()
        val wrap = CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC)
        val texto = "ISSO É UM TESTE"
        val tv: TextView = findViewById(R.id.tv)

        val chavecripto = "CHAVE_FLAVIO"
        val chavedescripto = "CHAVE_FLAVIO"

        // cria as chaves no container seguro do Android (serão sobrescritas se já existirem)
        cripto.createMasterKey()

        Log.d("CHAVE FLAVIO >>>", ks.getPublicKeyAsString("CHAVE_FLAVIO"))
        Log.d("CHAVE RAFAEL >>>", ks.getPublicKeyAsString("CHAVE_RAFAEL"))
        Log.d("CHAVE EDUARDO >>>", ks.getPublicKeyAsString("CHAVE_EDUARDO"))

        // transforma a PublicKey em uma string
        val spubkey: String = ks.getPublicKeyAsString(chavecripto)

        // transforma uma string em PublicKey
        val pubkey: PublicKey = ks.getPublicKeyFromString(spubkey)


        try {
            // criptografa com a chave pública construída com base na string armazenada em spubkey
            val cipher = wrap.encrypt(texto, pubkey)

            // descriptografa com a chave privada armazenada no KeyStore Android
            val dcipher = cripto.decrypt(cipher, chavedescripto)

            tv.text = dcipher
        } catch (e: Exception) {
            Log.d("Error", "Chave errada!")
            tv.text = "Chave errada!"
        }

        // remove as chaves do container seguro do Android (não remover se for reutilizar as chaves no futuro)
        cripto.removeMasterKey()
    }
}