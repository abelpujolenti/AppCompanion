package cdi.interfacedesign.lolrankedtracker.firebase.models

import java.util.Date

data class DataBaseUser(
    override var id: String? = null,
    var email: String? = null,
    var userName: String? = null,
    var lastLogin: Date? = Date(),
    val admin: Boolean = false

) : DataBaseData{
    override fun GetTable(): String = "users"

}