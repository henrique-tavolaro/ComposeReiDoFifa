package com.example.reidofifa.fragments

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
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.reidofifa.composables.DEFAULT_IMAGE
import com.example.reidofifa.composables.LoginButton
import com.example.reidofifa.composables.circularProgressBar
import com.example.reidofifa.composables.loadImage
import com.example.reidofifa.models.Player
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

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
                ReiDoFifaTheme {


                    Column {
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                                ,
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),

                            ) {
                            circularProgressBar(isDisplayed = viewModel.loading.value)

                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                val image = loadImage(
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
                                        contentDescription = null
                                    )
                                }

                                if (player != null) {
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(bottom = 16.dp),
                                        value = player.name,
                                            onValueChange = { /*TODO*/ })
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(16.dp),
                                        value = player.email,
                                        readOnly = true,
                                        onValueChange = { /*TODO*/ })
                                }
                                LoginButton(
                                    onButtonClick = {
                                        uploadUserImage()
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

    private fun uploadUserImage() {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            "USER_IMAGE" + System.currentTimeMillis() + "." + getFileExtension(selectedImageUri.value)
        )

        selectedImageUri.value?.let {
            storageRef.putFile(it).addOnSuccessListener { task ->
                Log.i("SSS", task.metadata!!.reference!!.downloadUrl.toString())
                task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    Log.i("SSS2", uri.toString())
                    profileImageURL = uri.toString()

//                        updateUserProfileData()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }

//    fun updateUserProfileData(){
//        val userHashMap = HashMap<String, Any>()
//
//        if(profileImageURL.isNotEmpty() && profileImageURL != player!!.image){
//            userHashMap["image"] = profileImageURL
//        }
////
//        if(etProfileName.text.toString() != userDetails.name) {
//            userHashMap[Constants.NAME] = etProfileName.text.toString()
//
//        }

//        viewModel.updateUserDetails(userHashMap)
//    }


    companion object {

        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2

    }

}
