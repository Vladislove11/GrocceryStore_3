package com.example.groccerystore_3

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CommentActivity : AppCompatActivity() {
    private val GALLARY_REQUEST = 302
    private var photoUri: Uri? = null

    private lateinit var productCommentTV: TextView
    private lateinit var productNameTV: TextView
    private lateinit var productPriceTV: TextView
    private lateinit var productImageIV: ImageView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comment)

        productCommentTV = findViewById(R.id.commentProductTV)
        productNameTV = findViewById(R.id.productNameTV)
        productPriceTV = findViewById(R.id.productPriceTV)
        productImageIV = findViewById(R.id.productImageIV)

        val comment = intent.getStringExtra("commentProd")
        val name = intent.getStringExtra("nameProd")
        val price = intent.getStringExtra("priceProd")
        val image = intent.getStringExtra("photoProd")
        productCommentTV.text = "$comment"
        productNameTV.text = "$name"
        productPriceTV.text = "$price"
        val photo: Uri? = Uri.parse(image)
        productImageIV.setImageURI(photo)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = ""

        productImageIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLARY_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu_comment, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exitMenuMain ->{
                finishAffinity()
                Toast.makeText(this,"Приложение завершено", Toast.LENGTH_LONG).show()
            }
            R.id.backMenuMain ->{
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        productImageIV = findViewById(R.id.productImageIV)
        when(requestCode) {
            GALLARY_REQUEST -> if (resultCode == RESULT_OK){
                photoUri = data?.data
                productImageIV.setImageURI(photoUri)
            }
        }
    }

}