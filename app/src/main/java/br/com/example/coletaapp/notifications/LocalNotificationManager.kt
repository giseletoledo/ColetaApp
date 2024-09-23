package br.com.example.coletaapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

import android.app.AlarmManager
import java.util.Calendar

object LocalNotificationManager {

    private const val CHANNEL_ID = "coletaapp_channel"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ColetaApp Notifications"
            val descriptionText = "Canal de notificações para ColetaApp"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(
        city: String,
        shiftType: String,
        time: String,
        daysOfWeek: List<String>,
        context: Context
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Parse o horário (ex: "8h00") para horas e minutos
        val (hour, minute) = time.split("h").map { it.toInt() }

        // Crie um intent para o BroadcastReceiver que irá gerar a notificação
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("city", city)
            putExtra("shiftType", shiftType)
            putExtra("time", time)
        }

        // Gerar um ID único baseado na cidade, tipo de turno e horário
        val requestCode = (city + shiftType + time).hashCode()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            val dayOfWeekMap = mapOf(
                "SUNDAY" to Calendar.SUNDAY,
                "MONDAY" to Calendar.MONDAY,
                "TUESDAY" to Calendar.TUESDAY,
                "WEDNESDAY" to Calendar.WEDNESDAY,
                "THURSDAY" to Calendar.THURSDAY,
                "FRIDAY" to Calendar.FRIDAY,
                "SATURDAY" to Calendar.SATURDAY
            )

            val currentDayOfWeek = get(Calendar.DAY_OF_WEEK)

            // Localiza o próximo dia da semana que corresponde ao turno
            val nextDayOfWeek = daysOfWeek
                .mapNotNull { dayOfWeekMap[it.uppercase()] }
                .firstOrNull { it >= currentDayOfWeek }
                ?: dayOfWeekMap[daysOfWeek.first().uppercase()]  // Se todos os dias já passaram, retorna o primeiro da lista

            if (nextDayOfWeek != null) {
                set(Calendar.DAY_OF_WEEK, nextDayOfWeek)
            }
        }

        val currentTime = System.currentTimeMillis()

        // Verifica se a data/hora já passou para hoje, se sim, move para o próximo dia/turno
        if (calendar.timeInMillis < currentTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Verifica a versão do Android e usa `setExactAndAllowWhileIdle` para garantir precisão
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }


    fun cancelNotification(city: String, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        // Use um ID exclusivo para PendingIntent, com base na cidade
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            city.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Cancelar o alarme
        alarmManager.cancel(pendingIntent)
    }
}
