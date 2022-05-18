package com.eradotovic.mbankingapp.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.eradotovic.mbankingapp.databinding.ActivityRegistrationBinding
import com.eradotovic.mbankingapp.transactions.TransactionsActivity

class RegistrationActivity : AppCompatActivity() {
    private val viewModel : RegistrationViewModel by viewModels()

    lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIcon.setOnClickListener {
            finish()
        }

        binding.buttonRegister.setOnClickListener {
            registrationVerification()
        }
    }

    /**
     * function for verification user data/starting TransactionsActivity
     * */
    private fun registrationVerification(){
        val firstNameField = binding.outlinedRegFirstnameField
        val lastNameField = binding.outlinedRegLastnameField
        val pinField = binding.outlinedRegPinField

        viewModel.userFirstName = binding.regFirstnameInputText.text.toString()
        viewModel.userLastName = binding.regLastnameInputText.text.toString()
        viewModel.pin = binding.regPinInputText.text.toString()
        viewModel.checkInput()

        val check = viewModel.checkInput()

        when(check) {
            "TRUE TRUE TRUE" -> {
                firstNameField.isErrorEnabled = false
                lastNameField.isErrorEnabled = false
                pinField.isErrorEnabled = false
                val intent = Intent(this, TransactionsActivity::class.java)
                intent.putExtra("firstName", binding.regFirstnameInputText.text.toString())
                intent.putExtra("lastName", binding.regLastnameInputText.text.toString())
                startActivity(intent)
                finish()
            }
            "FALSE TRUE TRUE" -> {
                firstNameField.error = "Wrong first name format"
                firstNameField.isErrorEnabled = true
                lastNameField.isErrorEnabled = false
                pinField.isErrorEnabled = false
            }
            "TRUE FALSE TRUE" ->{
                lastNameField.error = "Wrong last name format"
                firstNameField.isErrorEnabled = false
                lastNameField.isErrorEnabled = true
                pinField.isErrorEnabled = false
            }
            "TRUE TRUE FALSE" ->{
                pinField.error = "Must be 4 digits"
                firstNameField.isErrorEnabled = false
                lastNameField.isErrorEnabled = false
                pinField.isErrorEnabled = true
            }
            "FALSE FALSE TRUE" ->{
                firstNameField.error = "Wrong first name format"
                lastNameField.error = "Wrong last name format"
                firstNameField.isErrorEnabled = true
                lastNameField.isErrorEnabled = true
                pinField.isErrorEnabled = false
            }
            "TRUE FALSE FALSE" ->{
                lastNameField.error = "Wrong last name format"
                pinField.error = "Must be 4 digits"
                firstNameField.isErrorEnabled = false
                lastNameField.isErrorEnabled = true
                pinField.isErrorEnabled = true
            }
            "FALSE TRUE FALSE" ->{
                firstNameField.error = "Wrong first name format"
                pinField.error = "Must be 4 digits"
                firstNameField.isErrorEnabled = true
                lastNameField.isErrorEnabled = false
                pinField.isErrorEnabled = true
            }
            "FALSE FALSE FALSE" ->{
                firstNameField.error = "Wrong first name format"
                lastNameField.error = "Wrong last name format"
                pinField.error = "Must be 4 digits"
                firstNameField.isErrorEnabled = true
                lastNameField.isErrorEnabled = true
                pinField.isErrorEnabled = true
            }
        }
    }
}