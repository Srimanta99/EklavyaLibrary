package com.sm.mylibrary.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.sm.mylibrary.databinding.ActivityLibraryOwnerBinding
import com.sm.mylibrary.utils.Constants


class LibraryOwnerActivity : AppCompatActivity() {
    lateinit var viewbinding: ActivityLibraryOwnerBinding
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityLibraryOwnerBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)


       // viewbinding.wbOwner.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        val webSettings: WebSettings =  viewbinding.wbOwner.getSettings()
        webSettings.javaScriptEnabled = true
        // Load URL inside the app (not in browser)
        viewbinding.wbOwner.setWebViewClient(WebViewClient())


        // Optional: enable zoom controls and DOM storage
        webSettings.domStorageEnabled = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false


        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading, please wait...")
            setCancelable(false)
        }

        viewbinding.imgBack.setOnClickListener {
           finish()
        }


        viewbinding.wbOwner.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress < 100) {
                    viewbinding.progressBar.setVisibility(View.VISIBLE)
                } else {
                    viewbinding.progressBar.setVisibility(View.GONE)
                }
            }
        })


        /*viewbinding.wbOwner.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                progressDialog.show()
            }

            override fun onPageFinished(view: WebView, url: String) {
                progressDialog.dismiss()
            }
        })*/


        // Load URL
        viewbinding.wbOwner.loadUrl(Constants.LIBRARY_OWNER)
    }

    override fun onBackPressed() {
        if (viewbinding.wbOwner.canGoBack()) {
            viewbinding.wbOwner.goBack()
        } else {
            super.onBackPressed()
        }
    }
}