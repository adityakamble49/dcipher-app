package com.adityakamble49.mcrypt.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.adityakamble49.mcrypt.R
import kotlinx.android.synthetic.main.activity_key_manager.*
import timber.log.Timber

/**
 * RSA Key Pair - Manager Activity
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
class KeyManagerActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

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

    override fun onItemClick(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
        showRSAKeyPairOptionMenu(view, position)
    }


    /*
     * Helper Functions
     */

    private fun bindView() {

        // Setup Support Action Bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup RSA Key List RecyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this, linearLayoutManager.orientation)
        val rsaKeyListAdapter = RSAKeyListAdapter()
        rsaKeyListAdapter.onItemClickListener = this
        rsa_key_list.layoutManager = linearLayoutManager
        rsa_key_list.addItemDecoration(decorator)
        rsa_key_list.adapter = rsaKeyListAdapter
    }

    private fun showRSAKeyPairOptionMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_rsa_key_options, popupMenu.menu)
        popupMenu.show()
    }
}