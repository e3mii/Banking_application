package com.eradotovic.mbankingapp.transactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.eradotovic.mbankingapp.R
import com.eradotovic.mbankingapp.adapter.AccountItemAdapter
import com.eradotovic.mbankingapp.adapter.TransactionItemAdapter
import com.eradotovic.mbankingapp.data.entity.Account
import com.eradotovic.mbankingapp.data.entity.Transaction
import com.eradotovic.mbankingapp.data.entity.User
import com.eradotovic.mbankingapp.databinding.ActivityTransactionsBinding
import com.google.gson.GsonBuilder

class TransactionsActivity : AppCompatActivity() {
    private val viewModel : TransactionsViewModel by viewModels()

    private lateinit var binding: ActivityTransactionsBinding
    private lateinit var slider1: ImageView
    private lateinit var slider2: ImageView
    private lateinit var slider3: ImageView
    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private var change : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userName.text = intent.getStringExtra("firstName").toString()
        binding.userLastName.text = intent.getStringExtra("lastName").toString()


        val url = "https://mportal.asseco-see.hr/builds/ISBD_public/Zadatak_1.json"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val gson = GsonBuilder().create()
                val user = gson.fromJson(response.toString(), User::class.java)
                viewModel.users.add(user)
                runOnUiThread {
                    bindings()
                    dataInitialization()
                }
            },
            {
                println("ERROR with fetching data")
            }
        )
        queue.add(jsonObjectRequest)

        binding.sortIcon.setOnClickListener {
            val transactionDataset: List<Transaction>
            if(!change){
                transactionDataset = viewModel.currentTransaction.asReversed()
                change = !change
            } else {
                transactionDataset = viewModel.currentTransaction
                    .sortedBy { it
                        .amount.split(" ")[0]
                        .replace(",", ".").toDouble() }
                change = !change
            }
            recyclerView.adapter = TransactionItemAdapter(transactionDataset)
        }

        binding.logoutIcon.setOnClickListener {
            finish()
        }
    }

    /**
     * function for manual viewpager "slider" color change
     * */
    private fun changeSliderColor(){
        when(viewPager.currentItem){
            0->{
                slider1.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.appBlueColor))
                slider2.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
                slider3.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            }
            1->{
                slider1.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
                slider2.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.appBlueColor))
                slider3.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            }
            2->{
                slider1.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
                slider2.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
                slider3.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.appBlueColor))
            }
        }
    }

    /**
     * function where data is initialized, and "maintained" (example sorting view)
     * */
    private fun dataInitialization() {
        var accountsDataset : List<Account> = emptyList()
        var transactionDataset : List<Transaction> = emptyList()
        for(user in viewModel.users){
            if(user.user_id == "1"){
                viewModel.loadAccounts(user.user_id)
                accountsDataset = user.acounts
                for(account in user.acounts){
                    if(account.id == "1"){
                        transactionDataset = account.transactions
                            .sortedBy { it
                                .amount.split(" ")[0]
                                .replace(",", ".").toDouble() }
                        viewModel.currentTransaction = transactionDataset.toMutableList()
                    }
                }
            }
        }

        viewPager.adapter = AccountItemAdapter(accountsDataset)
        recyclerView.adapter = TransactionItemAdapter(transactionDataset)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeSliderColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                for(account in viewModel.userAccounts){
                    if(account.id == (position+1).toString()){
                        transactionDataset = account.transactions
                            .sortedBy { it
                                .amount.split(" ")[0]
                                .replace(",", ".").toDouble() }
                        viewModel.currentTransaction = transactionDataset.toMutableList()
                    }
                }
                recyclerView.adapter = TransactionItemAdapter(transactionDataset)
            }

            /*override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }*/

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeSliderColor()
            }

        })
    }

    /**
     * function for controlling presented UI and binding views
     * */
    private fun bindings(){
        binding.viewPager.isVisible = true
        binding.linearLayout.isVisible = true
        binding.sortIcon.isVisible = true
        binding.recyclerView.isVisible = true
        binding.progressBar.isVisible = false

        viewPager = binding.viewPager
        slider1 = binding.slider1
        slider2 = binding.slider2
        slider3 = binding.slider3
        recyclerView = binding.recyclerView
    }
}