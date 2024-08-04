package com.corylab.hinthunt.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UninstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_PACKAGE_REMOVED) {
            val packageName = intent.data?.schemeSpecificPart
            if (packageName == context.packageName) {
                performCleanupTasks()
            }
        }
    }

    private fun performCleanupTasks() {
    }
}