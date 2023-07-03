package com.example.card

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.card.ui.theme.CardTheme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoteCard()
                }
            }
        }
    }
}

@Composable
fun NoteCard()
{
    val isIconClicked = remember{ mutableStateOf(false) }

    Card(modifier= Modifier
        .padding(8.dp)
        .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp))
    {
        Row(modifier= Modifier
            .padding(all=8.dp)
            )
        {
            Column(modifier=Modifier.weight(.9f))
            {
                Row()
                {
                    Text("NOTE: ", fontWeight = FontWeight.Bold,color = Color.Red,modifier=Modifier.weight(0.15f))
                    Text("These insoles are not",color = Color.White,modifier=Modifier.weight(0.75f))
                }
                HyperLinkText(
                    fullText = "prescription medical orthotics. If you have orthotics prescribed by a specialist, please continue using those and a 20% discount on your next subscription renewal!",
                    linkText = listOf("20% discount on your next subscription renewal!"),
                    hyperlinks = listOf("https://google.com")
                    )
            }
            androidx.compose.material3.Icon(imageVector = Icons.Default.Close, contentDescription = "",modifier= Modifier
                .weight(0.1f)
                .clickable {
                    isIconClicked.value = !isIconClicked.value
                },Color.White)
        }
    }
}

@Composable
fun HyperLinkText(
    modifier: Modifier=Modifier,
    fullText : String,
    linkText : List<String>,
    linkTextColor : Color = Color.White,
    linkTextDecoration : TextDecoration = TextDecoration.Underline,
    hyperlinks : List<String>,
    fontSize : TextUnit = 18.sp
)
{
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed{ index,link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color=linkTextColor,
                    fontSize=fontSize,
                    textDecoration = linkTextDecoration
                ),
                start=startIndex,
                end=endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
            fontSize=fontSize,
            color=linkTextColor
            ),
            start = 0,
            end = fullText.length
        )
    }
    val uriHandler = LocalUriHandler.current
    ClickableText(modifier=modifier,text = annotatedString, onClick ={
        annotatedString.getStringAnnotations("URL",it,it).firstOrNull()?.let { stringAnnotation ->
            uriHandler.openUri(stringAnnotation.item)
        }
    } )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CardTheme {
        NoteCard()
    }
}