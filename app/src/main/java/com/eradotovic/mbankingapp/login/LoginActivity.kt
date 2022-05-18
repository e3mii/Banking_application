package com.eradotovic.mbankingapp.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.eradotovic.mbankingapp.CIPHERTEXT_WRAPPER
import com.eradotovic.mbankingapp.SHARED_PREFS_FILENAME
import com.eradotovic.mbankingapp.databinding.ActivityLoginBinding
import com.eradotovic.mbankingapp.registration.RegistrationActivity
import com.eradotovic.mbankingapp.transactions.TransactionsActivity
import com.eradotovic.mbankingapp.utils.BiometricPromptUtils

class LoginActivity :AppCompatActivity() {
    private lateinit var cryptographyManager: CryptographyManager
    private val viewModel : LoginViewModel by viewModels()

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel

        binding.registrationText.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener{
            loginVerifications()
        }
    }

    /**
     * function for verification user data/starting biometrics auth
     * */
    private fun loginVerifications(){
        val firstNameField = binding.outlinedFirstnameField
        val lastNameField = binding.outlinedLastnameField
        viewModel.userFirstName = binding.firsnameInputText.text.toString()
        viewModel.userLastName = binding.lastnameInputText.text.toString()

        val check = viewModel.checkInput()

        when (check) {
            "TRUE TRUE" -> {
                firstNameField.isErrorEnabled = false
                lastNameField.isErrorEnabled = false

                showBiometricPromptForEncryption()
            }
            "TRUE FALSE" -> {
                lastNameField.error = "Wrong last name format"
                firstNameField.isErrorEnabled = false
                lastNameField.isErrorEnabled = true
            }
            "FALSE TRUE" -> {
                firstNameField.error = "Wrong first name format"
                firstNameField.isErrorEnabled = true
                lastNameField.isErrorEnabled = false
            }
            "FALSE FALSE" -> {
                firstNameField.error = "Wrong first name format"
                firstNameField.isErrorEnabled = true
                lastNameField.error = "Wrong last name format"
                lastNameField.isErrorEnabled = true
            }
            "dbERROR" -> {
                firstNameField.error = "User not found"
                lastNameField.error = "User not found"
                firstNameField.isErrorEnabled = true
                lastNameField.isErrorEnabled = true
            }
        }
    }

    /**
     * function for biometric prompt
     * */
    private fun showBiometricPromptForEncryption() {
        val canAuthenticate = BiometricManager.from(applicationContext).canAuthenticate()
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val secretKeyName = "biometric_fake_encryption_key"
            cryptographyManager = CryptographyManager()
            val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(this, ::encryptAndStoreServerToken)
            val promptInfo = BiometricPromptUtils.createPromptInfo(this)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

    /**
     * function for auth success
     * */
    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
        authResult.cryptoObject?.cipher?.apply {
            val fakeToken = java.util.UUID.randomUUID().toString()
            Log.d("Fake token:", "The token from server is $fakeToken")
            val encryptedServerTokenWrapper = cryptographyManager.encryptData(fakeToken, this)
            cryptographyManager.persistCiphertextWrapperToSharedPrefs(
                encryptedServerTokenWrapper,
                applicationContext,
                SHARED_PREFS_FILENAME,
                Context.MODE_PRIVATE,
                CIPHERTEXT_WRAPPER
            )
        }
        val intent = Intent(this, TransactionsActivity::class.java)
        intent.putExtra("firstName", binding.firsnameInputText.text.toString())
        intent.putExtra("lastName", binding.lastnameInputText.text.toString())
        startActivity(intent)
    }
}