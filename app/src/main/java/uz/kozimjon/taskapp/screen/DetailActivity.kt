package uz.kozimjon.taskapp.screen

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import uz.kozimjon.taskapp.R
import uz.kozimjon.taskapp.model.CharacterData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageView: ImageView = findViewById(R.id.ivImage)
        val tvName: TextView = findViewById(R.id.tvName)
        val tvCreated: TextView = findViewById(R.id.tvCreated)
        val tvStatus: TextView = findViewById(R.id.tvStatus)
        val tvSpecies: TextView = findViewById(R.id.tvSpecies)
        val tvType: TextView = findViewById(R.id.tvType)
        val tvGender: TextView = findViewById(R.id.tvGender)

        val data = intent.extras?.get("data") as CharacterData

        tvName.text = data.name
        tvStatus.text = data.status
        tvSpecies.text = data.species

        if (data.type.equals("")) {
            tvType.text = "No type"
        } else {
            tvType.text = data.type
        }

        tvGender.text = data.gender

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val parsedDate = LocalDateTime.parse(data.created, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            tvCreated.text = formattedDate.toString()

        } else {
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formattedDate = formatter.format(parser.parse(data.created))
            tvCreated.text = formattedDate
        }

        Glide.with(imageView).load(data.image).placeholder(R.drawable.background_image).into(imageView)
    }
}