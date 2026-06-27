package com.example.leslie

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.leslie.ui.theme.LeslieTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class Model(val name: String)
val model = listOf(
    Model("MiniCPM5-1B"),
    Model("qwen 0.5b"),
    Model("qwen2.5: 1.5b")
)
class talkToAI(val model: Model, val prompt: String){

}

class warning(
    context: Context,
    title: String,
    content: String,
    ok: () -> Unit
            ){
    var warn = android.app.AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(content)
        .setIcon(R.mipmap.ic_launcher)
        .setPositiveButton("OK") {dialog, _ ->
            ok()
        }
    fun warnNoDiss(){
        warn.show()
    }
    fun warnDiss(dismis: () -> Unit){
        warn.setOnDismissListener {
            dismis()
        }
        warn.show()
    }

}

public var dismissed: Boolean = false
public val user: String? = null
var welcomeJob: Job? = null


class MainActivity : ComponentActivity() {
    val mainl = R.layout.main
    val insert_name = R.layout.insert_name
    private lateinit var welcomeName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (user == null || user == ""){
            setContentView(insert_name)
            val q: ImageButton = findViewById(R.id.info)
            q.setOnClickListener {
                warning(
                    this,
                    "Info",
                    "This app wants safety and privacy for the user, once you set your username and password, you won't be able to change it and have to enter that everytime you want to use this app",
                    ok = {

                    }
                ).warnNoDiss()
            }
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = ContextCompat.getColor(this, R.color.blackgrey)
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.isAppearanceLightStatusBars = false
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            welcomeJob = lifecycleScope.launch(Dispatchers.IO){
                kotlinx.coroutines.withContext(Dispatchers.Main) {
                    while (isActive) {
                        var randomizer = getRandomString(4)
                        var welcome: TextView = findViewById(R.id.welcome)
                        welcome.setText("Welcome, ${randomizer.toString()}!")
                        delay(500)
                    }
                }
            }

        }else if (user != null || user != ""){
            setContentView(mainl)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = ContextCompat.getColor(this, R.color.blackgrey)
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.isAppearanceLightStatusBars = false
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            }
            welcomeJob?.cancel()
            warning(
                this,
                "Warning",
                "This app won't save any historic information, nor save any chat history. Delete cache all over you want, freedom is within your hand, $user.",
                ok = {
                    dismissed = false
                    Toast.makeText(this, "Goodluck, $user!", Toast.LENGTH_SHORT).show()
                }
                ).warnDiss (dismis = {
                dismissed = true
                })
        }
    }

    // Source - https://stackoverflow.com/a/54400933
// Posted by WhiteAngel, modified by community. See post 'Timeline' for change history
// Retrieved 2026-06-27, License - CC BY-SA 4.0

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }



}

