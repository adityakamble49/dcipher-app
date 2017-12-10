package com.adityakamble49.mcrypt.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.adityakamble49.mcrypt.R
import com.adityakamble49.mcrypt.model.RSAKeyPair
import com.adityakamble49.mcrypt.utils.inflate
import kotlinx.android.synthetic.main.rsa_key_item.view.*

/**
 * @author Aditya Kamble
 * @since 10/12/2017
 */
class RSAKeyListAdapter : RecyclerView.Adapter<RSAKeyListAdapter.ViewHolder>() {

    lateinit var onItemClickListener: AdapterView.OnItemClickListener

    private var rsaKeyPairList = emptyList<RSAKeyPair>()

    override fun getItemCount() = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            parent.inflate(R.layout.rsa_key_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        init {
            itemView.rsa_key_options.setOnClickListener(this@ViewHolder)
        }

        fun bind(rsaKeyPair: RSAKeyPair) = with(itemView) {
            rsa_key_pair_name.text = rsaKeyPair.name
            rsa_key_options.setOnClickListener(this@ViewHolder)
        }

        override fun onClick(view: View?) {
            this@RSAKeyListAdapter.onItemClickListener.onItemClick(null, view, adapterPosition, 0)
        }

    }
}