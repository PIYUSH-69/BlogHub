package com.invictus.bloghub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.invictus.bloghub.MainScreen
import com.invictus.bloghub.components.texfield2
import com.invictus.bloghub.models.blogitem
import com.invictus.bloghub.ui.theme.customgray
import com.invictus.bloghub.viewmodels.AddBlogScreenViewModel


@Composable
fun AddBlog(navHostController: NavHostController)  {

    val viewModel: AddBlogScreenViewModel = viewModel()
    val category by viewModel.category
    val isLoading by viewModel.isLoading
    val content = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    var isbuttonvisvisible by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = customgray
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .padding(30.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            Text(
                text = "Add Blog", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            texfield2(arg = title, hint = "Enter Title", lines = 1)
            Spacer(modifier = Modifier.padding(5.dp))

            texfield2(arg = content, hint = "Enter Content", lines = 15)
            Spacer(modifier = Modifier.padding(16.dp))


            if (isbuttonvisvisible){
                Button(
                    onClick = {
                        viewModel.categorize(content.value)
                        isbuttonvisvisible=false

                        //navHostController.navigate(LoginScreen)
                    }, modifier = Modifier.fillMaxWidth(),
                    elevation = ButtonDefaults.buttonElevation(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                )

                {
                    Text(text = "Analyze Blog")
                }



            }



            if (isLoading == null) {

                //DO NOTHING

            } else {
                if (isLoading == true) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text(
                        text = "CATEGORY : ${category?.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(16.dp)
                    )

                    Text(
                        text = "Percentage : ${category?.percentage!!.toFloat()} %",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Justify,
                    )

                    if(category!!.percentage==0.0){

                        Spacer(modifier = Modifier.padding(16.dp))
                        Text(text ="PLEASE ENTER BOTH FIELDS" )

                    }else{

                        Button(
                            onClick = {
                                navHostController.navigate(MainScreen(name = "HELLO")){
                                    popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                                    launchSingleTop = true
                                }
                                viewModel.addBlog(blogitem(title = title.value,description = content.value,category = category!!.name))
                            }, modifier = Modifier.fillMaxWidth(),
                            elevation = ButtonDefaults.buttonElevation(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        )

                        {
                            Text(text = "ADD BLOG")
                        }
                    }




                }


            }


        }
    }}

















