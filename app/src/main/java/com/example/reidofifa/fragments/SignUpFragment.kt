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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.reidofifa.BaseApplication
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

    @Inject
    lateinit var application: BaseApplication

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
                                        textStyle = MaterialTheme.typography.h5
                                    )
                                    TextField(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                            .background(Color.White),
                                        value = password,
                                        onValueChange = { newValue ->
                                            viewModel.onPasswordChange(newValue)
                                        },
                                        label = {
                                            Text(text = "Password")
                                        },
                                        textStyle = MaterialTheme.typography.h5
                                    )
                                    LoginButton(
                                        onButtonClick = { registerUser(email, name, password) },
                                        text = "REGISTER",
                                        color = MaterialTheme.colors.primary)
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
        FirebaseAuth.getInstance().signOut()
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
                    } else {

                        //TODO inserir um Toast (não tava funcionando)
                    //Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

    private fun validateForm(email: String, name: String, password: String): Boolean {
        when {

            TextUtils.isEmpty(email) -> {
                //TODO inserir um Toast (não tava funcionando)
                return false
            }
            TextUtils.isEmpty(name) -> {
                //TODO inserir um Toast (não tava funcionando)
                return false
            }
            TextUtils.isEmpty(password) -> {
                //TODO inserir um Toast (não tava funcionando)
                return false
            }
            else -> return true
        }
    }
}


