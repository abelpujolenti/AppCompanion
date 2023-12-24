package cdi.interfacedesign.lolrankedtracker.firebase

import cdi.interfacedesign.lolrankedtracker.firebase.models.DataBaseData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MyFirebaseDataBase {

    val dataBase = Firebase.firestore

    fun <T: DataBaseData> Save(data: T, onSuccess: (T) -> Unit, onFailure: (Exception) -> Unit) {

        val table = dataBase.collection(data.GetTable())
        val id = data.id ?: table.document().id
        data.id = id

        table
            .document(id)
            .set(data)
            .addOnSuccessListener {
                onSuccess(data)
            }
            .addOnFailureListener { exception ->

                MyFirebase.crashlytics.logError(exception) {
                    key("Object", data.toString())
                    key("Error Type", "Insert Or Update Error")
                }

                onFailure(exception)
            }
    }

    inline fun <reified T:DataBaseData> Find(id: String, tableName: String, crossinline onSuccess: (T) -> Unit, crossinline onFailure: (Exception) -> Unit){

        dataBase.collection(tableName)
            .document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val data: T? = documentSnapshot.toObject(T::class.java)

                data?.let {data ->
                    onSuccess(data)
                } ?: kotlin.run {
                    val exception = Exception("Error on Parse Firestore DocumentSnapshot")
                    MyFirebase.crashlytics.logError(exception) {
                        key("id", id)
                        key("table", tableName)
                        key("Error Type", "Parse Error")
                        key("Snapshot", documentSnapshot.toString())
                    }

                    onFailure(exception)
                }
            }
            .addOnFailureListener { exception ->
                MyFirebase.crashlytics.logError(exception){
                    key("id", id)
                    key("table", tableName)
                    key("Error Type", "Object Not Found")
                }

                onFailure(exception)
            }

    }

}