package com.adityakamble49.mcrypt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adityakamble49.mcrypt.ui.DecryptActivity
import com.adityakamble49.mcrypt.ui.EncryptActivity
import com.adityakamble49.mcrypt.ui.KeyManagerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    /*
     * Lifecycle Functions
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    /*
     * Helper Functions
     */

    private fun bindView() {
        encrypt_button.setOnClickListener(this)
        decrypt_button.setOnClickListener(this)
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
        }
        return true
    }
}
