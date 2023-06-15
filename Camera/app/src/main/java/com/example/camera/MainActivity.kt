package com.example.camera





import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private val CAMERA_REQ_CODE = 100
    private lateinit var imgCamera: ImageView
    private lateinit var cameraBtn: Button
    private lateinit var selectImgBtn: Button
    private lateinit var storagePermissionLauncher: ActivityResultLauncher<String>
    private lateinit var selectImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgCamera = findViewById(R.id.camImg)
        cameraBtn = findViewById(R.id.cameraBtn)

        cameraBtn.setOnClickListener {
            val iCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQ_CODE)
            } else {
                // Launch the camera intent
                cameraLauncher.launch(iCamera)
            }

        }

        selectImgBtn = findViewById(R.id.selectImgBtn)

        selectImgBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                selectImageLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
            }
        }

        storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                selectImageLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri: Uri? = result.data?.data
                if (selectedImageUri != null) {
                    try {
                        val inputStream: InputStream? = contentResolver.openInputStream(selectedImageUri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        imgCamera.setImageBitmap(bitmap)
                    } catch (exception: Exception) {
                        Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val img = result.data?.extras?.get("data") as? Bitmap
            img?.let {
                imgCamera.setImageBitmap(it)
                // Save the image to SDCard
                val imageFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "captured_image.jpg")
                val outputStream = FileOutputStream(imageFile)
                it.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            }
        }
    }

}
