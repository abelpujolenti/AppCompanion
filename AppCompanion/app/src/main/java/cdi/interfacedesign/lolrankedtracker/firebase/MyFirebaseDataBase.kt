package cdi.interfacedesign.lolrankedtracker.firebase

import cdi.interfacedesign.lolrankedtracker.firebase.models.DataBaseData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MyFirebaseDataBase {

    val store = Firebase.firestore

    inline fun <reified T:DataBaseData> Find(id: String, tableName: String, crossinline onSuccess: (T) -> Unit, crossinline onFailure: (Exception) -> Unit){

        val table = store.collection(tableName)
        table
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