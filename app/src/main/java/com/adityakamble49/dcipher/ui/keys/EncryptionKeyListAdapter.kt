package com.adityakamble49.dcipher.ui.keys

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.adityakamble49.dcipher.R
import com.adityakamble49.dcipher.model.EncryptionKey
import com.adityakamble49.dcipher.utils.inflate
import kotlinx.android.synthetic.main.key_item.view.*

/**
 * @author Aditya Kamble
 * @since 10/12/2017
 */
class EncryptionKeyListAdapter : RecyclerView.Adapter<EncryptionKeyListAdapter.ViewHolder>() {

    lateinit var onItemClickListener: AdapterView.OnItemClickListener

    var encryptionKeyList = emptyList<EncryptionKey>()

    override fun getItemCount() = encryptionKeyList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            parent.inflate(R.layout.key_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(
            encryptionKeyList[position])

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        init {
            itemView.key_options.setOnClickListener(this@ViewHolder)
        }

        fun bind(encryptionKey: EncryptionKey) = with(itemView) {
            key_pair_name.text = encryptionKey.name
            key_options.setOnClickListener(this@ViewHolder)
        }

        override fun onClick(view: View?) {
            this@EncryptionKeyListAdapter.onItemClickListener.onItemClick(null, view, adapterPosition, 0)
        }

    }
}