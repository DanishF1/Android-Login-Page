package com.example.leslie

import android.content.Context
import android.os.Bundle
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
import com.example.leslie.ui.theme.LeslieTheme

data class Model(val name: String)
val model = listOf(
    Model("MiniCPM5-1B"),
    Model("qwen 0.5b"),
    Model("qwen2.5: 1.5b")
)
class talkToAI(val model: Model, val prompt: String){

}
public var dismissed: Boolean = false
public val user: String? = null


class MainActivity : ComponentActivity() {
    val mainl = R.layout.main
    val insert_name = R.layout.insert_name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (user == null || user == ""){
            setContentView(insert_name)
        }else if (user != null || user != ""){
            setContentView(mainl)
            warning(
                this,
                "Warning",
                "This app won't save any historic information, nor save any chat history. Delete cache all over you want, freedom is within your hand, $user.",
                ok = {
                    dismissed = false
                    Toast.makeText(this, "Goodluck, $user!", Toast.LENGTH_SHORT).show()
                },
                dismiss = {
                    dismissed = true
                })
        }
    }

    private fun warning(
        context: Context,
        title: String,
        content: String,
        ok: () -> Unit,
        dismiss: () -> Unit
        ){
        val warn = android.app.AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(content)
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton("OK") {dialog, _ ->
                ok()
            }
            .setNegativeButton("Don't Show This Again") {dialog, _ ->
                dismiss()
            }
        if (!dismissed) warn.show()



    }
}

