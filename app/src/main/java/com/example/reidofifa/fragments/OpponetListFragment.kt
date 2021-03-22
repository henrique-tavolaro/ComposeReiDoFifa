package com.example.reidofifa.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import com.example.reidofifa.BaseApplication
import com.example.reidofifa.R
import com.example.reidofifa.composables.NavOptions
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import com.google.firebase.auth.FirebaseAuth
import com.example.reidofifa.composables.NavOption
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OpponetListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ReiDoFifaTheme {
                    val userId = getCurrentUserID()

                    val state = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()

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
                                    Image(
                                        painterResource(id = R.drawable.ic_user_place_holder),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(172.dp)
                                            .width(172.dp)
                                            .padding(top = 32.dp, start = 16.dp)
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 24.dp, top = 8.dp),
                                        text = "name",
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colors.onPrimary

                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 24.dp, bottom = 16.dp, top = 8.dp),
                                        text = "email",
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

                                NavOptions(NavOption(icon = Icons.Default.Home, label = "Home"), click = {})
                                NavOptions(NavOption(icon = Icons.Default.AccountBox, label = "Profile"), click = {
                                    findNavController().navigate(R.id.action_opponetListFragment_to_profileFragment)
                                })
                                NavOptions(NavOption(icon = Icons.Default.ExitToApp, label = "Logout"), click = {
                                    FirebaseAuth.getInstance().signOut()
                                })
                            }

                        }
                    ) {


                    }
                }
            }
        }
    }


    private fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.toString()
    }


}

@Composable
fun NavDrawerContent(state: ScaffoldState, coroutine: CoroutineScope) {

}


