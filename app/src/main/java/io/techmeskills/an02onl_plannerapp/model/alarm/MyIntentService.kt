package io.techmeskills.an02onl_plannerapp.model.alarm

import android.content.Intent
import androidx.core.app.JobIntentService

class MyIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        intent.apply {
            when (intent.action) {
                ACTION_SEND_TEST_MESSAGE -> {
                    val message = getStringExtra(EXTRA_MESSAGE)
                    println(message)
                }
            }
        }
    }


    companion object {
        const val ACTION_SEND_TEST_MESSAGE = "ACTION_SEND_TEST_MESSAGE"
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }
}