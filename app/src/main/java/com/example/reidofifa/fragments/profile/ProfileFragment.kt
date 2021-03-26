package com.example.reidofifa.fragments.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.reidofifa.R
import com.example.reidofifa.composables.*
import com.example.reidofifa.models.Player
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import com.example.reidofifa.util.DEFAULT_IMAGE
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val IMAGE = "image"
const val NAME = "name"

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private var selectedImageUri = mutableStateOf<Uri?>(null)
    private var profileImageURL: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                val player = viewModel.data.value
                selectedImageUri.value = player?.image?.toUri()
                ReiDoFifaTheme {


                    val state = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()

                    Scaffold(
                        scaffoldState = state,
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "My Profile") },
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
                                    click = {
                                        findNavController().navigate(R.id.action_profileFragment_to_opponetListFragment)
                                    },
                                    background = Color.White
                                )
                                NavOptions(

                                    NavOption(
                                        icon = Icons.Default.AccountBox,
                                        label = "Profile",
                                    ),
                                    click = {

                                    },

                                    background = Color.LightGray.copy(alpha = 0.4f),
                                )
                                NavOptions(
                                    NavOption(
                                        icon = Icons.Default.ExitToApp,
                                        label = "Logout"
                                    ), click = {
                                        FirebaseAuth.getInstance().signOut()
                                        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                                    }, background = Color.White
                                )
                            }

                        }
                    ) {
                        Column {
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally),
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),

                                ) {
                                circularProgressBar(isDisplayed = viewModel.loading.value)

                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    val image = loadImageUri(

                                        url = selectedImageUri.value,



                                        defaultImage = DEFAULT_IMAGE
                                    ).value
                                    image?.let { img ->
                                        Image(
                                            bitmap = img.asImageBitmap(),
                                            modifier = Modifier
                                                .padding(top = 32.dp, bottom = 32.dp)
                                                .align(Alignment.CenterHorizontally)
                                                .width(160.dp)
                                                .height(160.dp)
                                                .clickable(onClick = { showImageChooser() })
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop,
                                            contentDescription = null
                                        )
                                    }
                                    val editText = viewModel.editText.value


                                    if (player != null) {
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .padding(bottom = 16.dp),
                                            value = player.name,
                                            onValueChange = {
                                            /*TODO não consigo carregar o noem e editar no edit text*/
                                            }

                                        )
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .padding(16.dp),
                                            value = player.email,
                                            readOnly = true,
                                            onValueChange = {  })
                                    }
                                    LoginButton(
                                        onButtonClick = {
                                            uploadUserImage(player!!)
                                            findNavController().navigate(R.id.action_profileFragment_to_opponetListFragment)
                                        },
                                        text = "UPDATE",
                                        color = MaterialTheme.colors.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            }
        } else {
            Toast.makeText(context, "you just denied permission for storage", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showImageChooser() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            PICK_IMAGE_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
            && requestCode == PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            selectedImageUri.value = data.data
        }
    }

    private fun getFileExtension(uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context?.contentResolver!!.getType(uri!!))
    }


    fun updateUserProfileData(player: Player) {
        val userHashMap = HashMap<String, Any>()

        if (profileImageURL.isNotEmpty() && profileImageURL != player.image) {
            userHashMap[IMAGE] = profileImageURL
        }

        userHashMap[NAME] = player.name

        viewModel.updateUserDetails(userHashMap)

    }

    fun uploadUserImage(player: Player) {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            "USER_IMAGE" + System.currentTimeMillis() + "." + getFileExtension(selectedImageUri.value)
        )

        selectedImageUri.value?.let {
            storageRef.putFile(it).addOnSuccessListener { task ->
                Log.i("SSS", task.metadata!!.reference!!.downloadUrl.toString())
                task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    Log.i("SSS2", uri.toString())
                    profileImageURL = uri.toString()

                        updateUserProfileData(player)
                }
            }.addOnFailureListener { e ->
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }


    companion object {

        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2

    }

}
