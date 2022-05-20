package uz.kozimjon.taskapp.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.collectLatest
import uz.kozimjon.taskapp.R
import uz.kozimjon.taskapp.adapter.RecyclerViewAdapter
import uz.kozimjon.taskapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var maiViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var llSearch: LinearLayout
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvProducts)
        llSearch = findViewById(R.id.llSearch)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        maiViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
        loadData()

        llSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        lifecycleScope.launchWhenCreated {
            maiViewModel.getListData().collectLatest {
                recyclerViewAdapter.submitData(it)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}