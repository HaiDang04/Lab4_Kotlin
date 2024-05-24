package com.example.lab4_kotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4_kotlin.ui.theme.Lab4_KotlinTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                //  you will get result here in result.data
                val data = result.data?.getStringExtra(KEY_NHANVIEN_MODEL)
                Toast.makeText(
                    baseContext,

                    data,
                    Toast.LENGTH_LONG

                ).show()
            }
        }
        setContent {
            Lab4_KotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                    LoginScreen(startForResult)
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
            }
        }
    }
}


@Composable
fun LoginScreen(startForResult: ActivityResultLauncher<Intent>) {
    val context = LocalContext.current // getApplicationContext()
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.images1),
            contentDescription = "Logo",
            modifier = Modifier
                .size(300.dp) // Adjust the size as needed
                .padding(0.dp) // Add padding around the image
        )

        Text(
            text = "WELCOME BACK",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier .fillMaxWidth()
                .padding(start = 43.dp, bottom = 15.dp)
        )
        // Username TextField
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))

// Password TextField
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            if (userName.isNotBlank() && password.isNotBlank()) {
                Toast.makeText(context, "Login successful",
                    Toast.LENGTH_LONG).show()

                val intent = Intent(context, Bai2Activity::class.java)
                val nhanvienModel = NhanVienModel(userName, password)

                intent.putExtra(KEY_USERNAME, userName)
                intent.putExtra(KEY_NHANVIEN_MODEL, nhanvienModel)

                startForResult.launch(intent)
                //context.startActivity(intent)

            } else {
                Toast.makeText(
                    context,

                    "Please enter username and password",
                    Toast.LENGTH_LONG

                ).show()
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
// Forgot Password and Sign Up
        Row(
            modifier = Modifier.fillMaxWidth(0.6f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), // Make text bold
                color = Color.Gray,
                modifier = Modifier.clickable {
                    // Handle forgot password action
                }
            )
            Text(
                text = "Sign Up? ",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Gray,
                modifier = Modifier.clickable {
                    // Handle sign up action
                }
            )
        }
    }
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
    Lab4_KotlinTheme {
        Greeting("Android")
    }
}