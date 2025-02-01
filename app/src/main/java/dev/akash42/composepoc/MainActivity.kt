package dev.akash42.composepoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.akash42.composepoc.ui.theme.ComposePOCTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePOCTheme {
                var txnUid by remember { mutableStateOf<String?>(null) }
                val onClick =  {
                    txnUid = Random.nextInt(5000, 100000).toString()
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        getComposable(txnUid)
                        Button(onClick = onClick) {
                            Text("Generate Random Transaction")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getComposable(txnUid: String?) {
    PassbookRecentEntriesComposable(txnUid)
}

@Composable
fun RandomComposable(modifier: Modifier = Modifier) {
    Text(
        text = "This is bad text: Random: ${Random.nextInt(5000, 100000)}",
        modifier = modifier
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePOCTheme {
        Greeting("Android")
    }
}