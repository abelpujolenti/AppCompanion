package cdi.interfacedesign.lolrankedtracker.firebase.models

interface DataBaseData {
    var id: String?

    fun GetTable(): String
}