package com.adityakamble49.mcrypt.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adityakamble49.mcrypt.R
import com.adityakamble49.mcrypt.ui.about.AboutActivity
import com.adityakamble49.mcrypt.ui.common.CommonViewModel
import com.adityakamble49.mcrypt.ui.common.CommonViewModelFactory
import com.adityakamble49.mcrypt.ui.decrypt.DecryptActivity
import com.adityakamble49.mcrypt.ui.encrypt.EncryptActivity
import com.adityakamble49.mcrypt.ui.keys.KeyManagerActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Dagger Injected Fields
    @Inject lateinit var commonViewModelFactory: CommonViewModelFactory

    // ViewModel
    private lateinit var commonViewModel: CommonViewModel


    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        // Get Key Manager ViewModel from Factory
        commonViewModel = ViewModelProviders.of(this, commonViewModelFactory).get(
                CommonViewModel::class.java)

        bindView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    /*
     * Listener Functions
     */

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.encrypt_button -> startActivity(Intent(this, EncryptActivity::class.java))
            R.id.decrypt_button -> startActivity(Intent(this, DecryptActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_key_manager -> startActivity(Intent(this, KeyManagerActivity::class.java))
            R.id.action_about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return true
    }


    /*
     * Helper Functions
     */

    private fun bindView() {
        encrypt_button.setOnClickListener(this)
        decrypt_button.setOnClickListener(this)
    }
}
