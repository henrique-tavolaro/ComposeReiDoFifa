package com.example.reidofifa.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reidofifa.BaseApplication
import com.example.reidofifa.R
import com.example.reidofifa.composables.LoginButton
import com.example.reidofifa.firebase.FirestoreClass
import com.example.reidofifa.models.Player
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                ReiDoFifaTheme() {

                    val email = viewModel.email.value
                    val password = viewModel.password.value

                    Column(
                        modifier = Modifier
                            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Enter e-mail and password to login",
                            modifier = Modifier
                                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.h5
                        )
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally),
                            elevation = 8.dp
                        ) {
                            Column {

                                TextField(
                                    modifier = Modifier
                                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colors.surface),
                                    value = email,
                                    onValueChange = { newValue ->
                                        viewModel.onEmailChange(newValue)
                                    },
                                    label = {
                                        Text(text = "E-mail")
                                    },
                                    textStyle = MaterialTheme.typography.h5
                                )
                                TextField(
                                    value = password,
                                    onValueChange = { newValue ->
                                        viewModel.onPasswordChange(newValue)
                                    },
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colors.background),

                                    label = {
                                        Text(text = "Password")
                                    },
                                    textStyle = MaterialTheme.typography.h5
                                )

                                LoginButton(
                                    onButtonClick = {
                                        signInUser(email, password)
                                    },
                                    text = "SIGN IN",
                                    color = MaterialTheme.colors.primary
                                )

                            }
                        }
                    }
                }
            }
        }
    }


//    fun signInSuccess(player: Player) {
//        try {
//
//        } catch (e: Exception){
//            Log.e("SSSS", "${e.message.toString()}, ${e.cause.toString()}")
//        }
//    }

    private fun signInUser(email: String, password: String) {
        val emails: String = email.trim { it <= ' ' }
        val passwords: String = password.trim { it <= ' ' }

        if (validateForm(emails, passwords)) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        FirestoreClass().loadUserData(this)
                        findNavController().navigate(R.id.action_signInFragment_to_opponetListFragment)
//                    } else {
//                        TODO Toast não está funcionando
//                        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        when {

            TextUtils.isEmpty(email) -> {
//                Toast.makeText(this, "Please insert E-mail", Toast.LENGTH_SHORT).show()
//                TODO arrumar Toast
                return false
            }
            TextUtils.isEmpty(password) -> {
//                Toast.makeText(this, "Please insert Password", Toast.LENGTH_SHORT).show()
//                TODO arrumar Toast
                return false
            }
            else -> return true
        }
    }


}