package uz.kozimjon.taskapp.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import uz.kozimjon.taskapp.R
import uz.kozimjon.taskapp.adapter.RecyclerViewAdapter
import uz.kozimjon.taskapp.viewmodel.CharacterSearchViewModel
import uz.kozimjon.taskapp.viewmodel.MainViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var searchViewModel: CharacterSearchViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var etSearch: EditText
    lateinit var ivBack: ImageView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.rvProducts)
        etSearch = findViewById(R.id.etSearch)
        ivBack = findViewById(R.id.ivBack)

        searchViewModel = ViewModelProvider(this)[CharacterSearchViewModel::class.java]

        ivBack.setOnClickListener {
            finish()
        }

        etSearch.addTextChangedListener {
            val currentText = it?.toString() ?: ""
            searchViewModel.submitQuery(currentText)
        }

        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter

        lifecycleScope.launchWhenCreated {
            searchViewModel.flow.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }

        searchViewModel.localExceptionEventLiveData.observe(this) { event ->
            event.getContent()?.let { localException ->
                Toast.makeText(this, localException.title, Toast.LENGTH_SHORT).show()
            }
        }
    }
}