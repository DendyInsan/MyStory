package com.dicoding.mystory.adapter

import android.content.Intent
import android.widget.RemoteViewsService
import com.dicoding.mystory.factory.StackRemoteViewsFactory

class StackWidgetServices: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}