package cdi.interfacedesign.lolrankedtracker.firebase

import android.net.Uri
import com.google.firebase.Firebase
//import com.google.firebase.storage.storage
import java.lang.Exception
import java.nio.file.FileSystems
import java.nio.file.Path

class MyFirebaseStorage {

    /*private val storage = Firebase.storage
    private val storageRootReference = storage.reference

    private val imagesPath = "Images"
    private val imagesReference = storageRootReference.child(imagesPath)

    fun saveImage(uri: Uri, onSuccess: (Uri) -> Unit, onFailure: (Exception) -> Unit){
        val path: Path = FileSystems.getDefault().getPath(uri.path)
        val name = path.fileName.toString()

        val imageReference = imagesReference.child(name)
        imageReference.putFile(uri)
            .addOnSuccessListener { uploadSnapshot ->
                uploadSnapshot.storage.downloadUrl
                    .addOnSuccessListener(onSuccess)
                    .addOnFailureListener(onFailure)
            }
            .addOnFailureListener(onFailure)
    }*/
}