package com.invictus.bloghub.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.invictus.bloghub.FullBlogScreen
import com.invictus.bloghub.ui.theme.customgray
import com.invictus.bloghub.ui.theme.orange
import com.invictus.bloghub.viewmodels.FullBlogScreenViewModel

@Composable
fun FullBlogScreen( args: FullBlogScreen) {
    val scrollState = rememberScrollState()
    val viewModel: FullBlogScreenViewModel = viewModel()
    val summaryText by viewModel.summary
    var isSummaryVisible by remember { mutableStateOf(false) }
    var isbuttonVisible by remember { mutableStateOf(true) }

    val rainbowColors = listOf(
        Color.Red, orange, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta
    )

    val infiniteTransition = rememberInfiniteTransition(label = "background")
    // Animate the gradient positions over time

    val targetoffset= with(LocalDensity.current){
        1000.dp.toPx()
    }

    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = targetoffset,
        animationSpec = infiniteRepeatable(tween(durationMillis = 3000, easing = LinearEasing), // Adjust duration
        repeatMode = RepeatMode.Restart
        ), label = "offset"
    )

    LaunchedEffect(animatedOffset) {
        println("Animated Offset: $animatedOffset")
    }

    val brushsize=600f
    val brush = Brush.linearGradient(
        colors = rainbowColors,
        start = Offset(animatedOffset, animatedOffset),
        end = Offset(animatedOffset + brushsize, animatedOffset + brushsize) ,
        tileMode = TileMode.Mirror// Move both start and end to create motion
    )

    Surface(
        modifier = Modifier.fillMaxSize())
    {
        Column(modifier = Modifier
            .background(customgray)
            .fillMaxSize()
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(30.dp, 10.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Top
            )
        {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = args.blog!!
                , style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp, 20.dp, 16.dp, 5.dp) )
            
            Text(text = "Category: "+args.blogcategory!! ,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.Underline // Adds underline to the text
                ),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp))

                Text(text = args.blogdesc!!,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify,
                    color = Color.White,)

    if (isbuttonVisible){
    Button( onClick = {
        viewModel.summarize(args.blogdesc)
        isSummaryVisible=true
        isbuttonVisible=false
    },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = ButtonDefaults.buttonElevation(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color. Black// Background color of the button
        )) {

        Text(
            text = "Summarize",
            style = TextStyle(
                brush = brush, // Apply gradient to text
                fontSize = 24.sp
            )
        )


    }

}


            if (isSummaryVisible) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Summarized text :"

                    , style = MaterialTheme.typography.titleLarge.copy(
                        textDecoration = TextDecoration.Underline // Adds underline to the text
                    ),
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp) .fillMaxWidth(), // Ensures the text takes the full width of its parent
                    textAlign = TextAlign.Left  )

                // Show the summarized text if available
                summaryText?.let {
                    Text(text = it
                        , style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(16.dp) )
                }
            }

        }
    }



}