package ibtikar.tania.user.ui.viewmodels

import android.health.connect.datatypes.units.Length
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appointment.composeauth.R
import com.appointment.composeauth.data.remote.LoginRequest
import com.appointment.composeauth.repository.MainActivityRepositry
import com.appointment.composeauth.state.ErrorState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginErrorState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.LoginUiEvent
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.emailOrMobileEmptyErrorState
import com.jodhpurtechies.composelogin.ui.screens.unauthenticated.login.state.passwordEmptyErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import ibtikar.tania.user.utils.AppUtils
import javax.inject.Inject

@HiltViewModel
public class LoginViewModel @Inject constructor(
) : ViewModel() {



    var loginState = mutableStateOf(LoginState())
        private set

    fun onBackClick(){
        loginState.value= loginState.value.copy(
            isCorporateUser = false
        )

    }

    /**
     * Function called on any login event [LoginUiEvent]
     */
    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {

            // Email/Mobile changed
            is LoginUiEvent.EmailOrMobileChanged -> {
                loginState.value = loginState.value.copy(
                    emailOrMobile = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        emailOrMobileErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailOrMobileEmptyErrorState
                    )
                )
            }

            // Password changed
            is LoginUiEvent.PasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
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
                val inputsValidated = validatePhoneNumber()
                if (!inputsValidated) {
                    // TODO Trigger login in authentication flow
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)

                }
            }

        }
    }

    private fun validatePhoneNumber(): Boolean {
        val mobileString = loginState.value.emailOrMobile.trim()

        val phoneNumber = mobileString.replace("966 - ", "").trim()
        if (phoneNumber.isEmpty() || phoneNumber.length < 9 || (phoneNumber.first()
                .equals('0', ignoreCase = true)
                    && phoneNumber.length > 10)
            || (phoneNumber.first().equals('5', ignoreCase = true) && phoneNumber.length > 9)
            || (phoneNumber.first().equals('0', ignoreCase = true) && phoneNumber.length < 10)
            || !(phoneNumber.first().equals('0', ignoreCase = true) ||
                    phoneNumber.first().equals('5', ignoreCase = true)) ||
            (phoneNumber.first().equals('0', ignoreCase = true) && phoneNumber[1] != '5')
        ) {
            loginState.value = loginState.value.copy(
                errorState = LoginErrorState(
                    emailOrMobileErrorState = emailOrMobileEmptyErrorState
                )
            )

            return false
        }else{
            loginState.value = loginState.value.copy(errorState = LoginErrorState())

            return true
        }
    }

}
