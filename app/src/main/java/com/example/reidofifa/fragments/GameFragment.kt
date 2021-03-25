package com.example.reidofifa.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reidofifa.R
import com.example.reidofifa.composables.loadImage
import com.example.reidofifa.models.Game
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import com.example.reidofifa.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class GameFragment : Fragment() {

    private val viewModel: GamesViewModel by viewModels()
    val args: GameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                val player = viewModel.player.value
                ReiDoFifaTheme {


                    val resultP1 = viewModel.resultP1.value
                    val resultP2 = viewModel.resultP2.value

                    val state = rememberScaffoldState()
                    var win = 0
                    var draw = 0
                    var lost = 0
                    var games = listOf<Game>()
                    if (player != null) {

                        viewModel.getAllGames(player.id, args.id)
                        games = viewModel.data.value.data!!
                        Log.d("TESTE", games.toString())
                        for (game in games) {
                            if (game.resultP1.toInt() == game.resultP2.toInt()) {
                                draw++
                            }
                            if ((game.player1Id == player.id && game.resultP1.toInt() > game.resultP2.toInt() ||
                                        game.player2Id == player.id && game.resultP2.toInt() > game.resultP1.toInt()
                                        )
                            ) {
                                win++
                            }
                            if ((game.player1Id == player.id && game.resultP1.toInt() < game.resultP2.toInt() ||
                                        game.player2Id == player.id && game.resultP2.toInt() < game.resultP1.toInt()
                                        )
                            ){
                                lost++
                            }
                        }
                    }
//                    val game = viewModel.provideGetAllGames(player!!.id, args.id)
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
                        }) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row {
                                if (player != null) {
                                    Player(
                                        name = player.name,
                                        url = player.image
                                    )
                                }


                                Column {
                                    val sdf = SimpleDateFormat("dd.MM.yyyy")
                                    val currDate: String = sdf.format(Date())
                                    Stats(win, draw, lost)
                                    if (player != null) {
                                        val game = hashMapOf(
                                            ID to "",
                                            PLAYER1ID to player!!.id,
                                            PLAYER2ID to args.id,
                                            RESULT1 to resultP1,
                                            RESULT2 to resultP2,
                                            DATE to currDate,
                                            PLAYERS to player.id + "_" + args.id
                                        )
                                        InsertResult(
                                            resultP1 = resultP1,
                                            onResultP1Changed = viewModel::onResultP1Changed,
                                            resultP2 = resultP2,
                                            onResultP2Changed = viewModel::onResultP2Changed,
                                            onClick = {
                                                viewModel.registerGame(game)
                                                viewModel.getAllGames(player.id, args.id)
                                            }
                                        )
                                    }
                                }
                                if (player != null) {
                                    Player(
                                        name = args.name,
                                        url = args.image
                                    )
                                }
                            }
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                                items(games!!) { game ->
                                    GameResults(game)
                                }
                            }


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
    url: String
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


            val image =
                loadImage(url = url, defaultImage = DEFAULT_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    modifier = Modifier
                        .height(106.dp)
                        .width(106.dp)
                        .padding(8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }

    }
}


@Composable
fun Stats(
    win: Int,
    draw: Int,
    lost: Int
) {
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
                    text = win.toString(),
                    fontSize = 13.sp
                )
                Text(
                    text = draw.toString(),
                    fontSize = 13.sp
                )
                Text(
                    text = lost.toString(),
                    fontSize = 13.sp
                )
            }

        }
    }
}

@Composable
fun InsertResult(
    resultP1: String,
    onResultP1Changed: (String) -> Unit,
    resultP2: String,
    onResultP2Changed: (String) -> Unit,
    onClick: () -> Unit
) {
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
                value = resultP1,
                onValueChange = onResultP1Changed,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                // TODO arrumar o input do usuário.
            )
            Text(
                text = "X",
                fontSize = 14.sp
            )
            BasicTextField(
                modifier = Modifier.width(8.dp),
                value = resultP2,
                onValueChange = onResultP2Changed,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                // TODO arrumar o input do usuário.
            )
        }
        Button(
            onClick = onClick,
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

val game = Game("id", "1", "2", "3", "4", "12.12.2012", "1_2")


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
            text = game.date,
        )
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = game.resultP1)
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "x"
            )
            Text(text = game.resultP2)
        }

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

//@Preview
//@Composable
//fun PreviewResult(){
//    InsertResult()
//}


//@Preview
//@Composable
//fun PreviewStats(){
//    Stats()
//}


//        @Preview
//@Composable
//fun PreviewPlayer(){
//    Player()
//}

