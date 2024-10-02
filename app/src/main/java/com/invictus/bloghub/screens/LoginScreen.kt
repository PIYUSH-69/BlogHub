package com.invictus.bloghub.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.invictus.bloghub.MainScreen
import com.invictus.bloghub.R
import com.invictus.bloghub.ui.theme.BLOGGERTheme
import com.invictus.bloghub.ui.theme.customgray
import com.invictus.bloghub.ui.theme.customwhite


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(navcontroller: NavHostController) {

    BLOGGERTheme{
        preview(navcontroller)
    }
}

@Composable
fun preview(navcontroller: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = customgray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.bloghub),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
            )


            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                colors = CardDefaults.cardColors(containerColor = customwhite ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        "WELCOME",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val name = remember { mutableStateOf("") }
                    val email = remember { mutableStateOf("") }
                    val password = remember { mutableStateOf("") }

                    customtextfield(name,"NAME")
                    Spacer(modifier = Modifier.padding(0.dp,5.dp))


                    customtextfield(email,"EMAIL")
                    Spacer(modifier = Modifier.padding(0.dp,5.dp))

                    var passwordVisible by remember { mutableStateOf(false) }

                    OutlinedTextField(value = password.value,
                        onValueChange ={password.value=it},
                        label = {Text(text = "PASSWORD")},
                        singleLine = true,
                        textStyle = TextStyle(color = Color.Black),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor =  Color.DarkGray ,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.DarkGray,
                            unfocusedLabelColor = Color.DarkGray,
                            cursorColor = Color.DarkGray,
                            focusedTrailingIconColor = Color.DarkGray,
                            unfocusedTrailingIconColor = Color.DarkGray
                        ),

                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Localized description for accessibility services
                            val description = if (passwordVisible) "Hide password" else "Show password"

                            // Toggle button to hide or display password
                            IconButton(onClick = {passwordVisible = !passwordVisible}){
                                Icon(imageVector  = image, description)
                            }
                        })

                    Spacer(modifier = Modifier.padding(0.dp,5.dp))

                    Button(onClick = { navcontroller.navigate(MainScreen(name =name.value )) }
                        , modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = customgray)) {
                        Text(text = "LOGIN",color = Color.White)
                    }


                }
            }







        }





    }


}

@Composable
fun customtextfield(arg: MutableState<String>, hint: String) {
    OutlinedTextField(value = arg.value, onValueChange ={arg.value=it}, label = {Text(text = hint)
    },singleLine = true,
        textStyle = TextStyle(color = Color.Black),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.DarkGray,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.DarkGray,
            unfocusedLabelColor = Color.DarkGray,
            cursorColor = Color.DarkGray
        )
    )


}
