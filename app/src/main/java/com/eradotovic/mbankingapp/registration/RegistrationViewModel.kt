package com.eradotovic.mbankingapp.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eradotovic.mbankingapp.GlobalDataObject
import com.eradotovic.mbankingapp.data.entity.LocalUser
import com.eradotovic.mbankingapp.data.repository.LocalUserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val localUserRepository: LocalUserRepository = GlobalDataObject.localUserRepository
) : ViewModel() {
    private var _userFirstName : String = ""
    var userFirstName: String = ""
        set(item){ this._userFirstName = item }

    private var _userLastName : String = ""
    var userLastName: String = ""
        set(item){ this._userLastName = item }

    private var _pin : String = ""
    var pin: String = ""
        set(item){ this._pin = item }

    /**
     * function for checking registration data
     * */
    fun checkInput() : String{
        var firstNameState = "FALSE"
        var lastNameState = "FALSE"
        var pinState = "FALSE"
        var returnThis = "FALSE FALSE FALSE"

        val inputPattern = "^([A-Za-z0-9]){1,30}$".toRegex()
        val pinPattern = "^([0-9]){4}$".toRegex()
        if(inputPattern.matches(_userFirstName)){
            firstNameState = "TRUE"
        }
        if(inputPattern.matches(_userLastName)){
            lastNameState = "TRUE"
        }
        if(pinPattern.matches(_pin)){
            pinState = "TRUE"
        }

        returnThis = if(firstNameState == "TRUE" && lastNameState == "TRUE" && pinState == "TRUE"){
            viewModelScope.launch {
                localUserRepository.insertLocalUser(
                    LocalUser(
                        firstName = _userFirstName,
                        lastName = _userLastName,
                        pin = _pin
                    )
                )
            }
            "$firstNameState $lastNameState $pinState"
        } else {
            "$firstNameState $lastNameState $pinState"
        }
        return returnThis
    }
}