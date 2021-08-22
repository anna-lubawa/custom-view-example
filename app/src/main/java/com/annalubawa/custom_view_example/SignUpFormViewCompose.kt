package com.annalubawa.custom_view_example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

data class UserData(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)

@Composable
fun SignUpForm(
    modifier: Modifier,
    onSignUpClicked: () -> Unit
) {
    val focusRequesters = List(5) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var state by rememberSaveable {
        mutableStateOf(SignUpFormState())
    }

    var data by remember {
        mutableStateOf(UserData())
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SingleTextField(
            label = "E-mail",
            inputType = KeyboardType.Email,
            focusRequester = focusRequesters[0],
            nextFocusRequester = focusRequesters[1],
            onFocusChange = {
                if (it.isFocused && state.isButtonVisible) {
                    state = state.copy(
                        isButtonVisible = false
                    )
                }
            }
        ) {
            data = data.copy(
                email = it
            )
            state = state.copy(
                emailValid = validateEmail(it)
            )
        }
        SingleTextField(
            label = "Firstname",
            focusRequester = focusRequesters[1],
            nextFocusRequester = focusRequesters[2],
            onFocusChange = {
                if (it.isFocused && state.isButtonVisible) {
                    state = state.copy(
                        isButtonVisible = false
                    )
                }
            }
        ) {
            data = data.copy(
                firstName = it
            )
            state = state.copy(
                firstnameValid = validateName(it)
            )
        }
        SingleTextField(
            label = "Lastname",
            focusRequester = focusRequesters[2],
            nextFocusRequester = focusRequesters[3],
            onFocusChange = {
                if (it.isFocused && state.isButtonVisible) {
                    state = state.copy(
                        isButtonVisible = false
                    )
                }
            }
        ) {
            data = data.copy(
                lastName = it
            )
            state = state.copy(
                lastnameValid = validateName(it)
            )
        }
        SingleTextField(
            label = "Password",
            inputType = KeyboardType.Password,
            focusRequester = focusRequesters[3],
            nextFocusRequester = focusRequesters[4],
            onFocusChange = {
                if (it.isFocused && state.isButtonVisible) {
                    state = state.copy(
                        isButtonVisible = false
                    )
                }
            }
        ) {
            data = data.copy(
                password = it
            )
            state = state.copy(
                passwordsValid = validatePasswords(it, data)
            )
        }
        SingleTextField(
            label = "Repeat password",
            inputType = KeyboardType.Password,
            focusRequester = focusRequesters[4],
            nextFocusRequester = focusRequesters[0],
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                    if (calculateProgress(state) == 1f) {
                        state = state.copy(
                            isButtonVisible = true
                        )
                    }
                }
            ),
            onFocusChange = {
                if (it.isFocused && state.isButtonVisible) {
                    state = state.copy(
                        isButtonVisible = false
                    )
                }
            }
        ) {
            data = data.copy(
                repeatPassword = it
            )
            state = state.copy(
                passwordsValid = validatePasswords(it, data)
            )
        }
        if(!state.isButtonVisible) {
            LinearProgressIndicator(
                progress = calculateProgress(state),
                backgroundColor = Grey,
                color = Green,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp
                    )
                    .height(16.dp)
            )
        }
        if(state.isButtonVisible) {
            Button(
                onClick = onSignUpClicked,
                enabled = true,
                content = {
                    Text("SIGN UP")
                },
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Orange,
                    contentColor = Color.White
                )
            )
        }
    }
}

@Composable
private fun SingleTextField(
    label: String,
    inputType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocusChange: (FocusState) -> Unit = {},
    onStateChange: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        label = { Text(label, color = DarkGrey) },
        onValueChange = {
            text = it
            onStateChange(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 16.dp
            )
            .focusOrder(focusRequester) {
                nextFocusRequester.requestFocus()
            }
            .onFocusChanged{
               onFocusChange(it)
            },
        shape = CircleShape,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Grey,
            disabledLabelColor = Grey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = DarkBlue
        ),
        visualTransformation = if (inputType == KeyboardType.Password)
            PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        keyboardActions = keyboardActions
    )
}

private fun calculateProgress(state: SignUpFormState): Float {
    return 0.0f
        .plus(if (state.emailValid) 0.2f else 0f)
        .plus(if (state.firstnameValid) 0.2f else 0f)
        .plus(if (state.lastnameValid) 0.2f else 0f)
        .plus(if (state.passwordsValid) 0.4f else 0f)
}

private fun validateEmail(text: String): Boolean {
    return text.contains("@")
            && text.contains(".")
            && text.length >= 5
}

private fun validateName(text: String): Boolean {
    return text.length >= 2
}

private fun validatePasswords(text: String, data: UserData): Boolean {
    return text.length >= 8 && data.password == data.repeatPassword
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SignUpForm(
        Modifier
            .padding(
                horizontal = 32.dp,
                vertical = 24.dp
            ),
        { }
    )
}