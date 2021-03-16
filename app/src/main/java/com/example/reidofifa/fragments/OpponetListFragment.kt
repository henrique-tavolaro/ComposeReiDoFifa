package com.example.reidofifa.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import com.example.reidofifa.R
import com.example.reidofifa.ui.theme.ReiDoFifaTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

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

                    Column() {
                        Text(text = userId)
                    }
                }

            }

        }
    }

    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser.toString()
    }
}