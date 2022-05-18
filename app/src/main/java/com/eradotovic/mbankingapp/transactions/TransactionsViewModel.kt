package com.eradotovic.mbankingapp.transactions

import androidx.lifecycle.ViewModel
import com.eradotovic.mbankingapp.data.entity.Account
import com.eradotovic.mbankingapp.data.entity.Transaction
import com.eradotovic.mbankingapp.data.entity.User

class TransactionsViewModel : ViewModel() {
    private var _users : MutableList<User> = mutableListOf()
    val users: MutableList<User>
        get() = _users

    private var _userAccounts : MutableList<Account> = mutableListOf()
    val userAccounts : MutableList<Account>
        get() = _userAccounts

    private var _currentTransactions : MutableList<Transaction> = mutableListOf()
    var currentTransaction : MutableList<Transaction>
        get() = _currentTransactions
        set(list) {
            this._currentTransactions = list
        }

    /**
     * function for storing fetched data into mutableList
     * */
    fun loadAccounts(id : String){
        for(user in _users){
            if(user.user_id == id){
                for(account in user.acounts){
                    val newAccount = Account(account.id, account.IBAN, account.amount, account.currency, account.transactions)
                    _userAccounts.add(newAccount)
                }
            }
        }
    }
}