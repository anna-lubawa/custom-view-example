package com.annalubawa.custom_view_example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SignUpForm(
                    Modifier
                        .padding(
                            horizontal = 32.dp,
                            vertical = 24.dp
                        )
                ) { Toast.makeText(context, "Sign up clicked", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondFragment()
    }
}