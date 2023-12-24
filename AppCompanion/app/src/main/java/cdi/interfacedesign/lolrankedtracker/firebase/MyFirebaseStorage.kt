package cdi.interfacedesign.lolrankedtracker.firebase

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

class MyFirebaseStorage {

    private val storage = Firebase.storage
    private val storageRootReference = storage.reference
    private val imagesReference = storageRootReference.child("dragontail-13.24.1/12.24.1/img")

    fun LoadImage(imagePath: String): StorageReference{
        return imagesReference.child(imagePath)
    }
}