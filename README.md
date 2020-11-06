# LocalizeWiz Android SDK

## Installation
The LocalizeWiz Android SDK can be added to your project either by using Gradle or Maven

### Gradle

In your root `build.gradle` file, add this at the end of repositories:
```
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```

In your the `build.gradle` file for your app's module, add this:
```gradle
	dependencies {
	        implementation 'com.github.localizewiz:wiz-android-sdk:$version'
	}
```

### Maven
In your Maven build file, add this:

```xml
<dependency>
   <groupId>com.localizewiz.wiz</groupId>
   <artifactId>localizewiz</artifactId>
   <version>$(version number)</version>
</dependency>
```

## Initialization
The `Wiz` SDK must be initialized before using it anywhere in your app. 

The best place to do the initialization is in your Application file. To do this, you have to create a custom Application class if you are not already using one.

Create an Application file in your project root package:
```kotlin
package com.mycompany.myapp

import android.app.Application
import com.localizewiz.wiz.Wiz

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Wiz.setup(context = this, apiKey = "my-wiz-api-key", projectId = "my-wiz-project-id")
    }
}
```

Then, in your `AndroidManifest.xml` file, set this custom application class as your app's application class.
Add this to your app's manifest file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.mycompany.myapp">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".MyApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
    </application>
</manifest>
```

The key lines to pay attention to are:

- android: name: Set this to the name of the class you created in the previous step.
- uses-permission: This is to add the internet permissionto your application if you did not already have this permission added. Internet permission is needed to fetch localizations from the cloud.

## Usage

Replace all occurences of `Context.getString(stringId)` with `wiz.getString(stringId)`.
So search and find lines like:

```kotlin
this.editText.text = getString(R.string.myStringId)
```

and replace them with:
```kotlin
this.editText.text = wiz.getString(R.string.myStringId)
```

If you were not using `Context.getString(stringId)` before, replace literal string references in your code with wiz.`getString(stringId)`

Voila! You're all set. Your app will now present localized content to users.
For details on more advanced usage and examples, please check the [android integration guide](android-integration.md)

