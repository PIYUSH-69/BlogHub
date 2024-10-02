package com.invictus.bloghub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.invictus.bloghub.AddBlogScreen
import com.invictus.bloghub.FullBlogScreen
import com.invictus.bloghub.MainScreen
import com.invictus.bloghub.models.blogitem
import com.invictus.bloghub.ui.theme.customgray
import com.invictus.bloghub.ui.theme.customwhite
import com.invictus.bloghub.viewmodels.MainScreenViewModel


@Composable
fun MainScreen(navcontroller: NavHostController) {
    val viewModel: MainScreenViewModel = viewModel()
    val blogs = viewModel.blogs.collectAsState(initial = emptyList())
    screen(navcontroller,blogs,null)
}

@Composable
fun screen(
    navcontroller: NavHostController,
    blogs: State<List<blogitem>>,
    args: MainScreen?

) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navcontroller.navigate(AddBlogScreen) },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            )
        }

    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            MainScreenContent(args,blogs,navcontroller)
        }
    }}

@Composable
fun MainScreenContent(
    args: MainScreen?,
    blogs: State<List<blogitem>>,
    navcontroller: NavHostController
){
    val categories = blogs.value.mapNotNull { it.category }.distinct().toMutableList()
    categories.add(0, "All")
    var selectedTabIndex by remember { mutableIntStateOf(0) } // Use the 'by' keyword with MutableState



    Column(modifier = Modifier
        .background(customgray)
        .fillMaxSize()) {

        Row (
            Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp)
        ){
            Text(modifier = Modifier.fillMaxWidth(),
                text = "Welcome to Blog Hub ",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = Color.White,
                textAlign = TextAlign.Center
                )
        }


            ScrollableTabRow(selectedTabIndex = selectedTabIndex,
                containerColor= Color.Black,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(0.dp) // Custom height for the indicator
                    // Custom indicator color and shape
                    )
                }) {
                categories.forEachIndexed { index, category ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {  selectedTabIndex = index },
                        text = { Text(category, color = if (selectedTabIndex == index) Color.Black else Color.White)
                        }
                        ,
                        modifier = Modifier
                            .padding(4.dp) // Add some padding around the tab
                            .clip(CircleShape) // Make the tab circular
                            .background(if (selectedTabIndex == index) customwhite else Color.Gray) // Set the background color
                    )

                }
            }





        // Display blogs based on the selected tab (category)
        val filteredBlogs = if (categories[selectedTabIndex] == "All") {
            blogs.value
        } else {
            blogs.value.filter { it.category == categories[selectedTabIndex] }
        }


//        LazyColumn(modifier = Modifier.fillMaxWidth()) {
//            items(filteredBlogs) { blog ->
//                BlogItemView(blog) // Custom composable to display each blog
//            }
//        }


        LazyColumn (modifier = Modifier
            .background(customgray)
            .padding(16.dp),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
            ,content = {
                items( filteredBlogs){
                    BlogCard(it,onClick = {
                        navcontroller.navigate(
                            FullBlogScreen(
                            blog = it.title,
                            blogdesc=it.description,
                            blogcategory=it.category
                        )
                        )
                    })
                }
            })
    }
}

@Composable
fun BlogCard(it: blogitem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = customwhite),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it.title ?: "Untitled",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold // Making the title bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ("Category: " + it.category),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = it.description ?: "No description available",
                style = MaterialTheme.typography.bodyLarge,
            )



        }
    }
}