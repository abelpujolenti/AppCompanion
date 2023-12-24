package cdi.interfacedesign.lolrankedtracker.firebase

import android.app.Application
import cdi.interfacedesign.lolrankedtracker.firebase.models.DataBaseUser
import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuthentication() {

    private val firebaseAuthentication = FirebaseAuth.getInstance()
    private var currentUser: DataBaseUser? = null

    fun IsLoginActive() = GetUser() != null

    fun SetCurrentUser(newUser: DataBaseUser){
        currentUser = newUser
    }

    fun GetUser() = currentUser

    fun GetAuthenticationDataBaseUser(): DataBaseUser?{
        firebaseAuthentication.currentUser?.let { user ->
            val dataBaseUser = DataBaseUser(
                user.uid,
                user.email,
                user.displayName
            )
            return dataBaseUser
        }
        return null
    }

}