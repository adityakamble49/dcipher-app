package com.adityakamble49.dcipher.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import com.adityakamble49.dcipher.BuildConfig
import com.adityakamble49.dcipher.R
import com.adityakamble49.dcipher.utils.Constants.ReferenceUrls
import com.afollestad.materialdialogs.internal.ThemeSingleton
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity


/**
 * About Activity
 *
 * @author Aditya Kamble
 * @since 7/1/2018
 */
class AboutActivity : MaterialAboutActivity() {

    override fun getActivityTitle(): CharSequence? = getString(R.string.about_dcipher)

    override fun getMaterialAboutList(p0: Context): MaterialAboutList {

        val appCardBuilder = MaterialAboutCard.Builder()

        appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.mipmap.ic_launcher)
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.version)
                .subText(BuildConfig.VERSION_NAME)
                .icon(R.drawable.ic_version)
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.changelog)
                .icon(R.drawable.ic_changelog)
                .setOnClickAction {
                    var accentColor = ThemeSingleton.get().widgetColor
                    if (accentColor == 0)
                        accentColor = ContextCompat.getColor(this@AboutActivity,
                                R.color.colorAccent)
                    ChangelogDialog.create(accentColor)
                            .show(supportFragmentManager, "changelog")
                }
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.license)
                .icon(R.drawable.ic_license)
                .setOnClickAction {
                    startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                }
                .build())


        val developerCardBuilder = MaterialAboutCard.Builder()
        developerCardBuilder.title(R.string.developer)

        developerCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.developer_name)
                .subText(R.string.developer_country)
                .icon(R.drawable.ic_developer)
                .setOnClickAction { openUrl(ReferenceUrls.TWITTER_PROFILE) }
                .build())

        developerCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.view_portfolio)
                .icon(R.drawable.ic_portfolio)
                .setOnClickAction { openUrl(ReferenceUrls.PERSONAL_WEBSITE) }
                .build())

        val listBuilder = MaterialAboutList.Builder()
        listBuilder.addCard(appCardBuilder.build())
        listBuilder.addCard(developerCardBuilder.build())

        return listBuilder.build()
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}