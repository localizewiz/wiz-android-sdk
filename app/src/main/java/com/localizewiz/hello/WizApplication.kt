package com.localizewiz.hello

import android.app.Application
import com.localizewiz.localizewiz.Wiz

class WizApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Wiz.configure(this, apiKey = "wiz_c44cbfd8d665b9b6db7c049fae4d1c9d", projectId = "164567725610370375")
    }
}