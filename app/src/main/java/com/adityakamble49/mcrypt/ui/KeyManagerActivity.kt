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
import com.adityakamble49.mcrypt.utils.RSAEncryption
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_key_manager.*
import timber.log.Timber
import javax.inject.Inject

/**
 * RSA Key Pair - Manager Activity
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
class KeyManagerActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
        View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var rsaEncryption: RSAEncryption

    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_manager)

        AndroidInjection.inject(this)

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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.import_key_fab -> Timber.i("Import Key")
            R.id.generate_key_fab -> handleGenerateKey()
        }
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

        // Setup Key Manager FAB
        import_key_fab.setOnClickListener(this)
        generate_key_fab.setOnClickListener(this)
    }

    private fun showRSAKeyPairOptionMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_rsa_key_options, popupMenu.menu)
        popupMenu.show()
    }

    private fun handleGenerateKey() {
        Timber.i("handleGenerateKey ${rsaEncryption.buildKeyPair()}")
    }
}