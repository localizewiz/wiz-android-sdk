package com.localizewiz.localizewiz.models

import java.util.*

class Workspace (
    var id: String,
    var name: String,
    var slug: String,
    var ownerId: String,
    var apiKey: String,
    var created: Date,
    var updated: Date,
    var activeUntil: Date,
    var numberOfProjects: Int?,
    var numberOfMembers: Int?,
    var projectLimit: Int,
    var stringLimit: Int,
    var languageLimit: Int,
    var planId: String,
    var projects: Array<Project>?
) { }
