package com.adityakamble49.dcipher.ui.keys

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.adityakamble49.dcipher.R
import com.adityakamble49.dcipher.cache.PreferenceHelper
import com.adityakamble49.dcipher.di.scope.PerActivity
import com.adityakamble49.dcipher.model.EncryptionKey
import com.adityakamble49.dcipher.utils.inflate
import kotlinx.android.synthetic.main.key_item.view.*
import javax.inject.Inject

/**
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@PerActivity
class EncryptionKeyListAdapter @Inject constructor(
        val preferenceHelper: PreferenceHelper) :
        RecyclerView.Adapter<EncryptionKeyListAdapter.ViewHolder>() {

    lateinit var onItemClickListener: AdapterView.OnItemClickListener

    private val PERFOMR_CLICK_DELAY = 500L

    var encryptionKeyList = emptyList<EncryptionKey>()

    override fun getItemCount() = encryptionKeyList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            parent.inflate(R.layout.key_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position,
            encryptionKeyList[position])

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        init {
            itemView.key_options.setOnClickListener(this@ViewHolder)
        }

        fun bind(position: Int, encryptionKey: EncryptionKey) = with(itemView) {
            key_pair_name.text = encryptionKey.name
            key_options.setOnClickListener(this@ViewHolder)
            // Do for first position only
            if (position == 0) {
                // Only if no key loaded
                if (preferenceHelper.currentEncryptionKeyId ==
                        PreferenceHelper.DEFAULT_CURRENT_ENCRYPTION_KEY_ID) {
                    val performClickHandler = Handler()
                    performClickHandler.postDelayed({
                        key_options.performClick()
                    }, PERFOMR_CLICK_DELAY)
                }
            }
        }

        override fun onClick(view: View?) {
            this@EncryptionKeyListAdapter.onItemClickListener.onItemClick(null, view,
                    adapterPosition, 0)
        }

    }
}