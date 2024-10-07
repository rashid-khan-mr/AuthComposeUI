package com.appointment.composeauth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.appointment.composeauth.repository.MainActivityRepositry
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginUiEvent
import ibtikar.tania.user.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navigateToDashboard: () -> Unit,
        navigateToSignup: () -> Unit

    ) {

    val focusRequester = remember { FocusRequester() }

    val loginState by loginViewModel.loginState

    if (loginState.isLoginSuccessful) {
        /**
         * Navigate to Authenticated navigation route
         * once login is successful
         */
        LaunchedEffect(key1 = true) {
            navigateToDashboard.invoke()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.light_gray))
        ) {
            // ... other composables


            Spacer(modifier = Modifier.padding(20.dp))
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.logoimg),
                contentDescription = "Description of the image",
                modifier = Modifier
                    .weight(0.08f),
                contentScale = ContentScale.Crop
            )

            /*Image(
                painter = painterResource(id = R.drawable.tanialogo),
                contentDescription = "Description of the image",
                modifier = Modifier.weight(0.04f), // Example size, you can adjust it
                contentScale = ContentScale.Crop // Adjust the content scale if needed
            )*/

            if (!loginState.isCorporateUser) {
                LoginHeadings(Modifier)
                EnterNumberTxtField(
                    phoneNumber = loginState.emailOrMobile,
                    onPhoneNumberChange = { inputString ->
                        loginViewModel.onUiEvent(
                            loginUiEvent = LoginUiEvent.EmailOrMobileChanged(
                                inputString
                            )
                        )
                        //phoneNumber = it
                    },
                    showError = loginState.errorState.emailOrMobileErrorState.hasError,
                    focusRequester = focusRequester,
                    onLoginClick = {
                        loginViewModel.onUiEvent(loginUiEvent = LoginUiEvent.Submit)

                        /*if (validatePhoneNumber(phoneNumber)) {
                        proceedWithLogin("966 - " + phoneNumber)
                    } else {
                        showErrorOnPhoneNumber = true // Update showError ifnot valid
                    }*/

                    }
                )

                Spacer(modifier = Modifier.weight(0.01f))

                LoginAsCorporateUser(text = AnnotatedString(stringResource(id = R.string._string_login_as_corporate_user))) {
                    navigateToSignup.invoke()
                }
            }
            Spacer(modifier = Modifier.weight(0.01f))
        }
    }
}
@Composable
fun LoginHeadings(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.login),
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth(),
        style = TextStyle(
            color = colorResource(id = R.color.dark_blue), // Set color from colors.xml
            fontSize = 43.sp,                        // Set text size
            fontWeight = FontWeight.Bold,             // Set text to bold
        )

    )


    Text(
        text = stringResource(id = R.string.login_by_phone_des),
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 10.dp),
        style = TextStyle(
            color = colorResource(id = R.color.dark_blue), // Set color from colors.xml
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )


    )
}
@Composable
private fun EnterNumberTxtField(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    showError: Boolean, // Pass showError as a parameter
    focusRequester: FocusRequester, // Pass focusRequester as a parameter
    onLoginClick: (String) -> Unit
) {



    TextField(
        value = phoneNumber,
        onValueChange = { onPhoneNumberChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(16.dp)) // Add this line
        .background(Color.White) // Add this line to set the background color
        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)),
    //   label = { Text("Enter phone number") },
        placeholder = { Text("Enter phone number") },
        leadingIcon = { Text("966 - ") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        colors = TextFieldDefaults.colors(
            unfocusedLabelColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White, // Ensures the disabled state is also white
            errorContainerColor = Color.White,    // Ensures the error state is also white
        ),
        trailingIcon = {
            if (showError) {
                Icon(
                    Icons.Filled.ErrorOutline,
                    contentDescription = "Error",
                    tint = Color.Red
                )
            }
        },
        isError = showError,
        /*supportingText = {
            if (showError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.invalid_mobile),
                    color = Color.Red,
                    textAlign = TextAlign.End
                )
            } },*/

    )

    LoginBtn{
        onLoginClick(phoneNumber)
    }
}

@Composable
private fun LoginBtn(onLoginClick: () -> Unit) {
    Button(
        onClick = { onLoginClick() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonColors(
            containerColor = colorResource(id = R.color.orange_color),
            contentColor = colorResource(
                id = R.color.white
            ),
            disabledContainerColor = colorResource(id = R.color.gray_color),
            disabledContentColor = colorResource(
                id = R.color.black
            )
        ),
    ) {
        Text(
            modifier = Modifier.padding(start = 35.dp, end = 35.dp),
            text = "Login",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        )
    }
}


@Composable
private fun LoginAsCorporateUser(text: AnnotatedString, loginAsCorporateUser: () -> Unit) {
    ClickableText(
        onClick = { loginAsCorporateUser() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center), // Center horizontally
        text = text,
        style = TextStyle(
            color = colorResource(id = R.color.dark_blue_new), // Set color from colors.xml
            fontWeight = FontWeight.Bold
        )
    )
}


@Preview
@Composable
fun PreviewLoginScreen(){
    LoginScreen(navigateToDashboard = {}) {

    }
}


