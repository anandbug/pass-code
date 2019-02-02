package `in`.nishachar.mypasscode.home

import `in`.nishachar.mypasscode.R
import `in`.nishachar.mypasscode.shared.AppDatabase
import `in`.nishachar.mypasscode.shared.User
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.util.*

class HomeActivity : AppCompatActivity() {
    private var userAdapter : UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userAdapter = UserAdapter()
        recyclerView?.adapter = userAdapter

        fab?.setOnClickListener {
            callCamera()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val users = getUsersFromDB()
            Log.d("tag", "size is:" + users.size)
            userAdapter?.loadUsers(users)
        }
    }

    private fun callCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            GlobalScope.launch(Dispatchers.Main) {
                val imageArray = convertToByteArray(imageBitmap)
                val rnd = Random()
                val passCode = 100000 + rnd.nextInt(900000)
                val count: Int = userAdapter?.itemCount!! + 1
                val user = User(UUID.randomUUID().toString(), "user$count", passCode, imageArray)
                userAdapter?.addUser(user)
                insertUserEntry(user)
            }
        }
    }

    private fun insertUserEntry(user: User): Job {
        return GlobalScope.launch {
            AppDatabase.getDatabase(this@HomeActivity).userDao().insertUser(user)
        }
    }

    private suspend fun getUsersFromDB(): List<User> {
        return withContext(Dispatchers.Default) {
            AppDatabase.getDatabase(this@HomeActivity).userDao().fetchUsers()
        }
    }

    private suspend fun convertToByteArray(bitmap: Bitmap) : ByteArray {
        return GlobalScope.async{
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return@async stream.toByteArray()
        }.await()
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}
