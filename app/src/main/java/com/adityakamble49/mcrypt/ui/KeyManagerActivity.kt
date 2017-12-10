package com.adityakamble49.mcrypt.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.adityakamble49.mcrypt.R
import kotlinx.android.synthetic.main.activity_key_manager.*

/**
 * RSA Key Pair - Manager Activity
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
class KeyManagerActivity : AppCompatActivity() {

    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_manager)

        bindView()
    }


    /*
     * Listener Functions
     */

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }


    /*
     * Helper Functions
     */

    private fun bindView() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val linearLayoutManager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this, linearLayoutManager.orientation)
        val rsaKeyListAdapter = RSAKeyListAdapter()
        rsa_key_list.layoutManager = linearLayoutManager
        rsa_key_list.addItemDecoration(decorator)
        rsa_key_list.adapter = rsaKeyListAdapter
    }
}