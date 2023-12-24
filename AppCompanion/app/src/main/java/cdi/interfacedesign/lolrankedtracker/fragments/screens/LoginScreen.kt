package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.firebase.models.DataBaseUser
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppMainMenu
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.common.SignInButton
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class LoginScreen: Fragment() {

    lateinit var fragmentView: View

    val googleAuthButton by lazy { fragmentView.findViewById<SignInButton>(R.id.login_google_button) }

    val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.screen_main_activity, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleAuthButton.setOnClickListener { GoogleAuthentication() }
    }

    private fun GoogleAuthentication() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signIntIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signIntIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        if(result.resultCode != Activity.RESULT_OK) {
            MyFirebase.crashlytics.logSimpleError("Login Error") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        val authUser = MyFirebase.authentication.GetAuthenticationDataBaseUser()?: kotlin.run {
            MyFirebase.crashlytics.logSimpleError("Login Error No User") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        val id = authUser.id ?: kotlin.run {
            MyFirebase.crashlytics.logSimpleError("Login Error No ID") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        MyFirebase.dataBase.Find<DataBaseUser>(id, authUser.GetTable(),
            onSuccess = { dataBaseUser ->
                dataBaseUser.lastLogin = Date()

                finalSaveUser(dataBaseUser)
            },
            onFailure = {
                finalSaveUser(authUser)
            })
    }

    private fun finalSaveUser(dataBaseUser: DataBaseUser) {
        MyFirebase.dataBase.Save(dataBaseUser,
            onSuccess = { dbUser ->
                MyFirebase.authentication.SetCurrentUser(dbUser)
                sendToastSuccessAndClose()
            },
            onFailure = {
                sendToastError()
            }
        )
    }

    private fun sendToastError() {
        Snackbar.make(
            AppMainMenu.get().fragmentView,
            getString(R.string.login_error),
            Snackbar.LENGTH_LONG)
            .show()
    }

    private fun sendToastSuccessAndClose() {
        Snackbar.make(
            AppMainMenu.get().fragmentView,
            getString(R.string.user_login_message, MyFirebase.authentication.GetUser()?.userName),
            Snackbar.LENGTH_LONG)
            .show()
        parentFragmentManager.popBackStack()
    }

}