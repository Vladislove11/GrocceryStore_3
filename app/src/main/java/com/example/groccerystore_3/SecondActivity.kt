package com.example.groccerystore_3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class SecondActivity : AppCompatActivity() {
    private val GALLARY_REQUEST = 302
    private val LAUNCH_SECOND_ACTIVITY = 101
    private var photoUri: Uri? = null
    //var products: MutableList<Product> = mutableListOf()
    private lateinit var productViewModel: ProductViewModel
    var listAdapter: ListAdapter? = null

    private lateinit var listViewLV: ListView
    private lateinit var productNameET: EditText
    private lateinit var productPriceET: EditText
    private lateinit var productCommentET: EditText
    private lateinit var editImageIV: ImageView
    private lateinit var saveBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        init()

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        listAdapter = ListAdapter(this@SecondActivity, productViewModel.products)
        listViewLV.adapter = listAdapter

        productViewModel.productsData.observe(this){ products ->
            listAdapter?.clear()
            listAdapter?.addAll(products)
        }

        editImageIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLARY_REQUEST)
        }

        saveBTN.setOnClickListener {
            createProduct()
            listAdapter?.notifyDataSetChanged()
            clearEditFields()
        }
        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, CommentActivity::class.java)
                intent.putExtra("commentProd", productViewModel.products[position].comment)
                intent.putExtra("nameProd", productViewModel.products[position].name)
                intent.putExtra("priceProd", productViewModel.products[position].price)
                intent.putExtra("photoProd", productViewModel.products[position].image)
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY )

            }
    }

    private fun init() {
        listViewLV = findViewById(R.id.listViewLV)
        productNameET = findViewById(R.id.productNameET)
        productPriceET = findViewById(R.id.productPriceET)
        productCommentET = findViewById(R.id.productCommentET)
        editImageIV = findViewById(R.id.editImageIV)
        saveBTN = findViewById(R.id.saveBTN)
    }

    private fun createProduct() {
        val productName = productNameET.text.toString()
        val productPrice = productPriceET.text.toString()
        val productComment = productCommentET.text.toString()
        val productImage = photoUri.toString()
        val product = Product(productName, productPrice, productComment, productImage)
        productViewModel.products.add(product)
        photoUri = null
    }

    private fun clearEditFields() {
        productNameET.text.clear()
        productPriceET.text.clear()
        productCommentET.text.clear()
        editImageIV.setImageResource(R.drawable.ic_photo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageIV = findViewById(R.id.editImageIV)
        when(requestCode) {
            GALLARY_REQUEST -> if (resultCode == RESULT_OK){
                photoUri = data?.data
                editImageIV.setImageURI(photoUri)
                }
            LAUNCH_SECOND_ACTIVITY -> if (resultCode == RESULT_OK){
                listAdapter?.notifyDataSetChanged()
            }
        }
    }
}