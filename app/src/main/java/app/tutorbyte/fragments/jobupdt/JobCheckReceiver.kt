package app.tutorbyte.fragments.jobupdt

import android.content.Context

class JobNotificationManager(private val context: Context) {

//    companion object {
//        private const val NOTIFICATION_CHANNEL_ID = "JobNotifications"
//        private const val NOTIFICATION_ID = 1
//    }
//
//    init {
//        createNotificationChannel()
//        setupJobCheckAlarm()
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Job Notifications"
//            val descriptionText = "Receive notifications for new job openings"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//
//            val notificationManager: NotificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    private fun setupJobCheckAlarm() {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val alarmIntent = Intent(context, JobCheckReceiver::class.java).let { intent ->
//            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//        }
//
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis(),
//            60000L,
//            alarmIntent
//        )
//    }
//
//
//
//    class JobCheckReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            context?.let {
//                val database = FirebaseDatabase.getInstance()
//                val jobsRef = database.getReference("Jobs")
//
//                jobsRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        for (dataSnapshot in snapshot.children) {
//                            val job = dataSnapshot.getValue(Job::class.java)
//                            val jobId = dataSnapshot.key
//
//                            if (isJobNew(jobId)) {
//                                showNewJobNotification()
//                            }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//
//                    }
//                })
//            }
//        }
//
////        fun showNewJobNotification() {
////            val context:Context
////            val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
////                .setSmallIcon(R.drawable.ic_add)
////                .setContentTitle("New Job Opening")
////                .setContentText("A new job opening is available.")
////                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
////
////            val notificationManager: NotificationManager =
////                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
////
////            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
////        }
////        private fun isJobNew(jobId: String?): Boolean {
////            return true
////        }
////    }
}
