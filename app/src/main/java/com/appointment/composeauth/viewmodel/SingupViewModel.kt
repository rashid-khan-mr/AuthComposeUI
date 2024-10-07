package ibtikar.tania.user.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appointment.composeauth.state.ErrorState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginErrorState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginUiEvent
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.emailOrMobileEmptyErrorState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.passwordEmptyErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
public class SingupViewModel @Inject constructor(
) : ViewModel() {



    var signupState = mutableStateOf(LoginState())
        private set

    fun onBackClick(){
        signupState.value= signupState.value.copy(
            isCorporateUser = false
        )

    }

    /**
     * Function called on any login event [LoginUiEvent]
     */
    fun onUiEvent(signupUiEvent: LoginUiEvent) {
        when (signupUiEvent) {

            // Email/Mobile changed
            is LoginUiEvent.EmailOrMobileChanged -> {
                signupState.value = signupState.value.copy(
                    emailOrMobile = signupUiEvent.inputValue,
                    errorState = signupState.value.errorState.copy(
                        emailOrMobileErrorState = if (signupUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailOrMobileEmptyErrorState
                    )
                )
            }

            // Password changed
            is LoginUiEvent.PasswordChanged -> {
                signupState.value = signupState.value.copy(
                    password = signupUiEvent.inputValue,
                    errorState = signupState.value.errorState.copy(
                        passwordErrorState = if (signupUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            // Submit Login
            is LoginUiEvent.Submit -> {

               /* val inputsValidated = validateInputs()
                if (inputsValidated) {
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)
                }*/
                val inputsValidated = validateCredentials()
                if (inputsValidated) {
                    signupState.value = signupState.value.copy(isLoginSuccessful = true)
                }
            }

        }
    }

    private fun validateCredentials(): Boolean {
        val mobileString = signupState.value.emailOrMobile.trim()
        val passwordString = signupState.value.password.trim()

        if (mobileString.isEmpty()){
            signupState.value = signupState.value.copy(
                errorState = LoginErrorState(
                    emailOrMobileErrorState = emailOrMobileEmptyErrorState
                )
            )
            return false
        }else if (passwordString.isEmpty()){
            signupState.value = signupState.value.copy(
                errorState = LoginErrorState(
                    passwordErrorState = passwordEmptyErrorState
                )
            )
            return false
        }else{
            return true
        }
    }

}
