package cdi.interfacedesign.lolrankedtracker.firebase.models

data class DataBaseUser(
    override var id: String? = null,
    var email: String? = null,
    var userName: String? = null,
    val admin: Boolean = false

) : DataBaseData{
    override fun GetTable(): String {
        TODO("Not yet implemented")
    }

}