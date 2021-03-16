package com.example.reidofifa.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.reidofifa.R
import com.example.reidofifa.composables.LoginButton
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ReiDoFifaTheme(
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxHeight()
                    ) {
                        Image(
                            painterResource(id = R.drawable.bilolaldo),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 40.dp, bottom = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .width(400.dp)
                                .height(400.dp)
                        )

                        Text(
                            text = "Sign up or Login",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.padding(8.dp))

                        LoginButton(
                            onButtonClick = {
                                findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
                            },
                            text = "LOGIN",
                            color = MaterialTheme.colors.primary
                        )
                        LoginButton(
                            onButtonClick = {
                                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
                            },
                            text = "SIGN UP",
                            color = Color.White
                        )

                    }
                }

            }
        }
    }
}



@Preview
@Composable
fun PreviewLoginScreen() {
//    LoginScreen()
}