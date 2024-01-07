package cdi.interfacedesign.lolrankedtracker.firebase.models

data class DataBaseImage (
    override var id: String?,
    var imageUrl: String? = null

) : DataBaseData {
    override fun GetTable(): String {
        TODO("Not yet implemented")
    }
}