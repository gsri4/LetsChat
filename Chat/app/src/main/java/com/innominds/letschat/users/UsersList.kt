package com.innominds.letschat.users

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.innominds.letschat.R
import com.innominds.letschat.adapter.CustomAdapter
import com.innominds.letschat.models.User
import kotlinx.android.synthetic.main.users_list.*
import java.util.ArrayList

class UsersList: AppCompatActivity() {



    internal var adapter: CustomAdapter? = null
    private var listContentArr = ArrayList<User>()



    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.users_list)

        val mLayoutManager = LinearLayoutManager(this)
        recyclerView_UsersList.setLayoutManager(mLayoutManager)
        recyclerView_UsersList.setItemAnimator(DefaultItemAnimator())
        recyclerView_UsersList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        //Creating adapter instance
        adapter = CustomAdapter(this)
        populateRecyclerViewValues()
    }



    private fun populateRecyclerViewValues() {

          listContentArr =  User.getAllUsers()
        //We set the array to the adapter
        adapter?.setListContent(listContentArr)
        recyclerView_UsersList.setAdapter(adapter)
    }


}