package com.localizewiz.hello

import android.app.Application
import com.localizewiz.wiz.Wiz

val wiz = Wiz.instance

class WizApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Wiz.setup(context = this, apiKey = "wiz_fa63b250438705bbeccf016361ffb602", projectId = "24037786491290786")
    }
}
