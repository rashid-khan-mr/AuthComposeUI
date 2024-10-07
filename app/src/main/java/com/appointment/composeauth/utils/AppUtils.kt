package ibtikar.tania.user.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.appointment.composeauth.R
import java.util.*
import java.util.regex.Pattern

class AppUtils {

    companion object {



        fun showInternetConnectionErrorDialog(activity: Activity, message: String) {
           /* AestheticDialog.Builder(activity, DialogStyle.CONNECTIFY, DialogType.ERROR)
                .setTitle(activity.getString(R.string.network_unavailable))
                .setMessage(message)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                    }
                })
                .show()*/
        }


        fun EditText.focus() {
            requestFocus()
            setSelection(length())
        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    fun ArabicToEnglish(str: String): String {
        var result = ""
        var en = '0'
        for (ch in str) {
            en = ch
            when (ch) {
                '۰' -> en = '0'
                '۱' -> en = '1'
                '۲' -> en = '2'
                '۳' -> en = '3'
                '۴' -> en = '4'
                '۵' -> en = '5'
                '۶' -> en = '6'
                '۷' -> en = '7'
                '۸' -> en = '8'
                '۹' -> en = '9'
            }
            result = "${result}$en"
        }
        return result
    }



    fun isValidEmail(email: String): Boolean {
        val emailPattern = (
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            )
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


    fun getCurrentMilliSeconds(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return "$hour:$minute:$second"
    }

    fun calculation(value: Int): String {
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR)
        var minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val temp = minute + value
        if (temp > 60) {
            hour++
        } else {
            minute += value
        }
        return "$hour:$minute:$second"
    }

    private fun shareApp(context: Context) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.type = "text/plain"
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "https://onelink.to/agzcem"
        )
        context.startActivity(sendIntent)
    }

    fun fullLog(tag: String, str: String) {
        if (str.length > 4000) {
            Log.d(tag, str.substring(0, 4000))
            fullLog(tag, str.substring(4000))
        } else Log.d(tag, str)
    }

    fun numbersFormat() {
        val locale = Locale.ENGLISH
        Locale.setDefault(locale)
    }

    fun openUrl(context: Context, ulr: String?) {
        val uri = Uri.parse(ulr)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    fun isInternetAvailable(context: Context): Boolean {
        var isConnected = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            isConnected = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                activeNetworkInfo?.run {
                    isConnected = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return isConnected
    }

    fun isInternetAccessible(context: Context): Boolean {
        if (isInternetAvailable(context)) {
            return true
        } else {
            showToast(
                context,
                "" + context.resources.getString(R.string.no_internet_connection)
            )
        }
        return false
    }
}
