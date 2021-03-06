package com.example.maps_task.ui.point_details_bottom_sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.os.bundleOf
import com.example.maps_task.R
import com.example.maps_task.model.GeoLocationDetailsModel
import com.example.maps_task.model.GeoSearchModel
import com.example.maps_task.model.Routes
import com.example.ui_core.extensions.setHtmlText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.bottom_point_details_dialog_fragment.*

class BottomSheetDialogDetails : BottomSheetDialogFragment() {

    var drawRouteListener: (() -> Unit)? = null

    companion object {
        private const val GEO_SEARCH_MODEL = "GEO_SEARCH_MODEL"
        private const val GEO_SEARCH_DESCRIPTION = "GEO_SEARCH_DESCRIPTION"
        private const val ROUTES = "ROUTES"


        @JvmStatic
        fun newInstance(
                geoLocationDetailsModel: GeoLocationDetailsModel,
                routes: Routes,
                geoSearchModel: GeoSearchModel
        ): BottomSheetDialogDetails =
                BottomSheetDialogDetails().apply {
                    arguments = bundleOf(
                            ROUTES to routes,
                            GEO_SEARCH_DESCRIPTION to geoLocationDetailsModel,
                            GEO_SEARCH_MODEL to geoSearchModel
                    )
                }
    }

    private var geoLocationDetailsModel: GeoLocationDetailsModel? = null
    private var routes: Routes? = null
    private var geoSearchModel: GeoSearchModel? = null
    private var isExpanded: Boolean = false

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        routes = args?.getSerializable(ROUTES) as? Routes
        geoLocationDetailsModel = args?.getSerializable(GEO_SEARCH_DESCRIPTION) as? GeoLocationDetailsModel
        geoSearchModel = args?.getSerializable(GEO_SEARCH_MODEL) as? GeoSearchModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupBehavior(bottomSheetDialog)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_point_details_dialog_fragment, container, false)
    }

    private fun setupBehavior(bottomSheetDialog: BottomSheetDialog) {
        val behavior = bottomSheetDialog.behavior

        if (!isExpanded) {
            llMainRoot.viewTreeObserver
                    .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            llMainRoot.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            val hidden = llMainRoot.getChildAt(1)
                            behavior.peekHeight = hidden.top
                        }
                    })
        } else {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                isExpanded = slideOffset == 1f
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {}
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPagerAdapter()

        titleTextView.text = geoSearchModel?.title
        tvDetails.text = geoSearchModel?.title

        btnSetRout.setOnClickListener {
            drawRouteListener?.invoke()
        }

        if (routes?.routes.isNullOrEmpty()) dismiss()
        val route = routes?.routes?.first()

        if (route?.legs.isNullOrEmpty()) dismiss()
        val legInfo = route?.legs?.first()
        val destinationRoute = "${legInfo?.startAddress} -> \n ${legInfo?.endAddress}"
        tvDetailsDescription.setHtmlText(destinationRoute)
        tvDistance.text = legInfo?.distance?.text
        tvDuration.text = legInfo?.duration?.text
    }

    private fun setViewPagerAdapter() {
        val titles = listOf(getString(R.string.destination_info).toUpperCase(), getString(R.string.contacts).toUpperCase())
        val adapter = PlacesInfoAdapter(geoLocationDetailsModel, routes, geoSearchModel, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)
        reduceMarginsInTabs(tabLayout, 64)
    }

    private fun reduceMarginsInTabs(tabLayout: TabLayout, marginOffset: Int) {
        val tabStrip = tabLayout.getChildAt(0)
        if (tabStrip is ViewGroup) {
            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)
                if (tabView.layoutParams is ViewGroup.MarginLayoutParams) {
                    (tabView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = marginOffset
                    (tabView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = marginOffset
                }
            }
            tabLayout.requestLayout()
        }
    }
}