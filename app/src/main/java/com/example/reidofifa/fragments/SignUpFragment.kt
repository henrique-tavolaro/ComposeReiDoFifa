package com.example.reidofifa.fragments

import android.media.MediaSession2
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ReiDoFifaTheme {

                    val name = viewModel.name.value
                    val email = viewModel.email.value
                    val password = viewModel.password.value


                        Column(
                            modifier = Modifier
                                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Enter e-mail, name and password to register",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
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
                                Column{

                                    OutlinedTextField(
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
                                        textStyle = MaterialTheme.typography.h5,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Email)

                                        )




                                    OutlinedTextField(
                                        value = name,
                                        onValueChange = { newValue ->
                                            viewModel.onNameChange(newValue)
                                        },
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colors.background),

                                        label = {
                                            Text(text = "Name")
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next
                                        ),
                                        textStyle = MaterialTheme.typography.h5
                                    )
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                                            .fillMaxWidth()
                                            .background(Color.White),
                                        value = password,
                                        onValueChange = { newValue ->
                                            viewModel.onPasswordChange(newValue)
                                        },

                                        label = {
                                            Text(text = "Password")
                                        },
                                        textStyle = MaterialTheme.typography.h5,
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Done,

                                        ),

                                        visualTransformation = PasswordVisualTransformation()
                                    )
                                    LoginButton(
                                        onButtonClick = { registerUser(email, name, password) },
                                        text = "REGISTER",
                                        color = MaterialTheme.colors.primary
                                    )
                                }
                            }
                        }
                }
            }


        }

    }

    fun userRegisteredSuccess(){
        //TODO inserir um Toast (não tava funcionando)
//        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        Log.e("TAG", "Success")
    }

    private fun registerUser(email: String, name: String, password: String ) {
        val emails: String = email.trim { it <= ' ' }
        val names: String = name.trim { it <= ' ' }
        val passwords: String = password.trim { it <= ' ' }

        if (validateForm(names, emails, passwords)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        val user = Player(firebaseUser.uid, name, registeredEmail)
                        FirestoreClass().registerUser(this, user)
                        signInUser(email, password)
                    } else {

                    Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

    private fun signInUser(email: String, password: String) {

           FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        FirestoreClass().loadUserData(this)
                        findNavController().navigate(R.id.action_signUpFragment_to_opponetListFragment)
                    } else {
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                    }

        }
    }

    private fun validateForm(email: String, name: String, password: String): Boolean {
        when {

            TextUtils.isEmpty(email) -> {
                Toast.makeText(context, "SSS", Toast.LENGTH_LONG).show()
                return false
            }
            TextUtils.isEmpty(name) -> {
                return false
            }
            TextUtils.isEmpty(password) -> {
                return false
            }
            else -> return true
        }
    }
}


