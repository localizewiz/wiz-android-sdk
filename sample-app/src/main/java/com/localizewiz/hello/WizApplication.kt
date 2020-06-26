package com.localizewiz.hello

import android.app.Application
import com.localizewiz.wiz.Wiz

class WizApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Wiz.configure(context = this, apiKey = "wiz_fa63b250438705bbeccf016361ffb602", projectId = "24037786491290786")
    }
}
