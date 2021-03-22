package com.example.reidofifa.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.reidofifa.BaseApplication
import com.example.reidofifa.R
import com.example.reidofifa.firebase.FirestoreClass
import com.example.reidofifa.models.Player
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModels()

    private var selectedImageUri: Uri? = null
    private var profileImageURL: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


                return ComposeView(requireContext()).apply {
            setContent {
                val dataOrException = viewModel.data.value
                ReiDoFifaTheme {
                    CircularProgressBar(isDisplayed = viewModel.loading.value)
                    Card(
                        modifier = Modifier
                            .padding(16.dp),
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(top = 24.dp, bottom = 24.dp)
                                    .align(Alignment.CenterHorizontally),
                                painter = painterResource(R.drawable.ic_user_place_holder),
                                contentDescription = null
                            )
                            TextField(value = dataOrException.toString(), onValueChange = { /*TODO*/ })
                        }
                    }
                }
            }
        }
    }

//    fun getUser(player: Player) {
//        FirestoreClass().loadUserDataOnProfile()
//        Log.d("TESTE", player.toString())
//    }
//
//    fun loadUserInUI(){
//        FirestoreClass().loadUserDataOnProfile()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}


@Composable
fun CircularProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
}