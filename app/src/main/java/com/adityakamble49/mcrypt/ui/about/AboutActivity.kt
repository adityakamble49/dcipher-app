package com.adityakamble49.mcrypt.ui.about

import android.content.Context
import com.adityakamble49.mcrypt.R
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList


/**
 * About Activity
 *
 * @author Aditya Kamble
 * @since 7/1/2018
 */
class AboutActivity : MaterialAboutActivity() {

    override fun getActivityTitle(): CharSequence? = getString(R.string.about_mcrypt)

    override fun getMaterialAboutList(p0: Context): MaterialAboutList {

        val appCardBuilder = MaterialAboutCard.Builder()

        appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.mipmap.ic_launcher)
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.version)
                .subText(getVersion())
                .icon(R.drawable.ic_version)
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.changelog)
                .icon(R.drawable.ic_changelog)
                .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.license)
                .icon(R.drawable.ic_license)
                .build())


        val developerCardBuilder = MaterialAboutCard.Builder()
        developerCardBuilder.title(R.string.developer)

        developerCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.developer_name)
                .subText(R.string.developer_country)
                .icon(R.drawable.ic_developer)
                .build())

        developerCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.view_portfolio)
                .icon(R.drawable.ic_portfolio)
                .build())

        val listBuilder = MaterialAboutList.Builder()
        listBuilder.addCard(appCardBuilder.build())
        listBuilder.addCard(developerCardBuilder.build())

        return listBuilder.build()
    }

    private fun getVersion(): String {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        return packageInfo.versionName
    }
}