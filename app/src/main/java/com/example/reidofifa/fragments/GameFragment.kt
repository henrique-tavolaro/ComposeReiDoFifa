package com.example.reidofifa.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ContentView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reidofifa.R
import com.example.reidofifa.models.Game
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameFragment : Fragment() {

    val args: GameFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                ReiDoFifaTheme {
                    val state = rememberScaffoldState()

                    Scaffold(
                        scaffoldState = state,
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Matches") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        findNavController().navigate(R.id.action_gameFragment_to_opponetListFragment)
                                    }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                }
                            )
                        }){
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row {
                                Player(args.name)
                                Column {
                                    Stats()
                                    InsertResult()
                                }
                                Player(args.name)
                            }
//                        LazyColumn(modifier = Modifier.fillMaxWidth()){
//                           items(games){ game ->
//                                GameResults(game)
//                           }
//                        }


                        }
                    }

                }
            }
        }
    }


}

@Composable
fun Player(
    name: String,
//    image: String
) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = name,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .width(106.dp)
                    .height(106.dp),
                painter = painterResource(
                    id = R.drawable.ic_user_place_holder
                ),
                contentDescription = null
            )

        }
    }
}

@Composable
fun Stats() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(90.dp)
            .height(90.dp),
        elevation = 6.dp,
        shape = CircleShape
    ) {
        Column {

            Text(
                text = "Stats",
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "W",
                    fontSize = 14.sp
                )
                Text(
                    text = "D",
                    fontSize = 14.sp
                )
                Text(
                    text = "L",
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "--",
                    fontSize = 13.sp
                )
                Text(
                    text = "--",
                    fontSize = 13.sp
                )
                Text(
                    text = "--",
                    fontSize = 13.sp
                )
            }
            InsertResult()
        }
    }
}

@Composable
fun InsertResult() {
    Column(
        modifier = Modifier
            .width(100.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            BasicTextField(
                modifier = Modifier.width(8.dp),
                value = "0",
                onValueChange = { /*TODO*/ },
            )
            Text(
                text = "X",
                fontSize = 14.sp
            )
            BasicTextField(
                modifier = Modifier.width(8.dp),
                value = "0",
                onValueChange = { /*TODO*/ },
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(100.dp)
        ) {
            Text(
                text = "SAVE GAME",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun GameResults(game: Game) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "12.12.2112",
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = "0 x 0"
        )
    }
}

//@Preview
//@Composable
//fun PreviewGameResult() {
//    Column {
//        Row {
//            Player()
//            Stats()
//
//            Player()
//        }
//                        LazyColumn(modifier = Modifier.fillMaxWidth()){
//                           items(games){ game ->
//                                GameResults(game)
//                           }
//                        }

//
//    }
//}

@Preview
@Composable
fun PreviewResult(){
    InsertResult()
}



@Preview
@Composable
fun PreviewStats(){
    Stats()
}



//        @Preview
//@Composable
//fun PreviewPlayer(){
//    Player()
//}

