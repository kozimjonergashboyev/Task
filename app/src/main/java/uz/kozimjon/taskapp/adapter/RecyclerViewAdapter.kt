package uz.kozimjon.taskapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.taskapp.R
import uz.kozimjon.taskapp.model.CharacterData
import uz.kozimjon.taskapp.screen.DetailActivity
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RecyclerViewAdapter : PagingDataAdapter<CharacterData, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)

        return MyViewHolder(inflater)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.ivImage)
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvCreated: TextView = view.findViewById(R.id.tvCreated)
        private val linearLayout: LinearLayout = view.findViewById(R.id.linearLayout)

        @SuppressLint("SimpleDateFormat")
        fun bind(data: CharacterData) {
            tvName.text = data.name

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

            linearLayout.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("data", data as Serializable)
                it.context.startActivity(intent)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name && oldItem.species == newItem.species
        }

    }
}