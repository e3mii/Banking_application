package com.eradotovic.mbankingapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eradotovic.mbankingapp.GlobalDataObject
import com.eradotovic.mbankingapp.data.entity.LocalUser
import com.eradotovic.mbankingapp.data.repository.LocalUserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val localUserRepository: LocalUserRepository = GlobalDataObject.localUserRepository
) : ViewModel() {
    private var _userFirstName : String = ""
    var userFirstName: String = ""
        set(item){ this._userFirstName = item }

    private var _userLastName : String = ""
    var userLastName: String = ""
        set(item){ this._userLastName = item }

    private var localUsers : List<LocalUser> = emptyList()

    /**
     * getting local db data
     * */
    init{
        viewModelScope.launch {
            localUserRepository.getLocalUsers().collect(){ list ->
                localUsers = list
            }
        }
    }

    /**
     * function for checking login data
     * */
    fun checkInput() : String{
        var firstNameState = "FALSE"
        var lastNameState = "FALSE"
        var returnThis = ""
        var hit = false

        val inputPattern = "^([A-Za-z0-9]){1,30}$".toRegex()
        if(inputPattern.matches(_userFirstName)){
            firstNameState = "TRUE"
        }
        if(inputPattern.matches(_userLastName)){
            lastNameState = "TRUE"
        }

        if(firstNameState == "TRUE" && lastNameState == "TRUE"){
            for(user in localUsers){
                if(user.firstName == _userFirstName && user.lastName == _userLastName){
                    hit = true
                    break
                }
            }
            returnThis = if(hit){
                "$firstNameState $lastNameState"
            } else {
                "dbERROR"
            }
        } else {
            returnThis = "$firstNameState $lastNameState"
        }
        return returnThis
    }
}