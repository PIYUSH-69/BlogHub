package com.invictus.bloghub.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.invictus.bloghub.ui.theme.customwhite


@Composable
fun texfield2(arg: MutableState<String>, hint: String,lines :Int=1) {
    OutlinedTextField(value = arg.value, onValueChange ={arg.value=it}, label = {
        Text(text = hint)
    },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White),
        minLines = lines,

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = customwhite,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = customwhite,
            cursorColor = Color.White
        )
    )


}