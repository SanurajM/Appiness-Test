package com.appiness.appinesstest.ui

import android.content.Intent
import android.os.Handler
import com.appiness.appinesstest.R


class SplashActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_splash

    override fun initViews() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
