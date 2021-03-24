package com.example.reidofifa.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reidofifa.BaseApplication
import com.example.reidofifa.R
import com.example.reidofifa.composables.NavOptions
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import com.google.firebase.auth.FirebaseAuth
import com.example.reidofifa.composables.NavOption
import com.example.reidofifa.composables.loadImage
import com.example.reidofifa.models.Player
import com.example.reidofifa.util.DEFAULT_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OpponetListFragment : Fragment() {

    private val viewModel: OpponentListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val player = viewModel.player.value
                ReiDoFifaTheme {


                    val state = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()
                    Log.d("ZI", player.toString())
                    Scaffold(
                        scaffoldState = state,
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Choose Your Opponent") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        coroutineScope.launch {
                                            state.drawerState.open()
                                        }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = null)
                                    }
                                }
                            )
                        },
                        drawerContent = {
                            Column {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(0.32f)
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colors.primary)
                                ) {
                                    if (player?.image != null) {

                                        player.image.let { url ->
                                            val image =
                                                loadImage(url = url, defaultImage = DEFAULT_IMAGE).value
                                            image?.let { img ->
                                                Image(
                                                    bitmap = img.asImageBitmap(),
                                                    modifier = Modifier
                                                        .height(152.dp)
                                                        .width(152.dp)
                                                        .padding(top = 16.dp, start = 16.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }

                                        Text(
                                            modifier = Modifier
                                                .padding(start = 24.dp, top = 8.dp),
                                            text = if(player?.name == null){
                                                "name"
                                            } else {
                                                player.name
                                            }
                                            ,
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colors.onPrimary

                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(start = 24.dp, bottom = 16.dp, top = 8.dp),
                                            text = if(player?.email == null){
                                                "email"
                                            } else {
                                                player.email
                                            },
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colors.onPrimary
                                        )

                                    }
                                    Divider(
                                        modifier = Modifier.fillMaxWidth(),
                                        thickness = 1.dp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))

                                    NavOptions(
                                        NavOption(icon = Icons.Default.Home, label = "Home"),
                                        click = {})
                                    NavOptions(
                                        NavOption(
                                            icon = Icons.Default.AccountBox,
                                            label = "Profile"
                                        ), click = {
                                            findNavController().navigate(R.id.action_opponetListFragment_to_profileFragment)
                                        })
                                    NavOptions(
                                        NavOption(
                                            icon = Icons.Default.ExitToApp,
                                            label = "Logout"
                                        ), click = {
                                            FirebaseAuth.getInstance().signOut()
                                        })
                                }
                            }

                            ){


                            val opponentList = viewModel.data.value.data

                            LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(items = opponentList!!) { oppon ->
                                    Opponent(
                                        oppon,
                                        onClick = {
                                            val action =
                                                OpponetListFragmentDirections.actionOpponetListFragmentToGameFragment(
                                                    oppon.id,
                                                    oppon.name,
                                                    oppon.image
                                                )
                                            findNavController().navigate(action)
                                        }
                                    )
                                }
                            }
                        }
                        }
                }
            }
        }
    }

    @Composable
    fun Opponent(
        player: Player,
        onClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable(onClick = onClick)

        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp),
                painter = painterResource(R.drawable.ic_user_place_holder),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = player.name,
                    fontSize = 22.sp
                )
                Text(
                    text = "Games Played: ",
                    fontSize = 16.sp
                )
            }
        }
    }


//@Preview
//@Composable
//fun previewOpponent(){
//    Opponent()
//}

