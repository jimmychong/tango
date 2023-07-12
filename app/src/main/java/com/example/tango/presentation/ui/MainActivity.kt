package com.example.tango.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.tango.R
import com.example.tango.databinding.ActivityMainBinding
import com.example.tango.presentation.post_list.PostAdapter
import com.example.tango.presentation.post_list.PostListState
import com.example.tango.presentation.post_list.PostListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostListViewModel>()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //can add a event observer in here so when the network call success dismiss the splash screen
        installSplashScreen().apply {
        }
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.posts.adapter = adapter
        adapter.selectedPostListener.onItemClick = {
            startActivity(PostDetailActivity.intentOf(this, it))
        }

        collectPostState()
        observeAction()
    }

    private fun observeAction() {
        binding.add.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialog_add_post, null)
            dialog.setContentView(view)
            view.findViewById<Button>(R.id.submit).setOnClickListener {
                val title = view.findViewById<EditText>(R.id.etTitle).text.toString()
                val body = view.findViewById<EditText>(R.id.etContent).text.toString()

                if (title.isEmpty() || body.isEmpty()) {
                    Toast.makeText(this, "Title or Content is empty", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addPost(title, body)
                    dialog.dismiss()
                }
            }
            view.findViewById<Button>(R.id.cancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun collectPostState() {
        viewModel.state.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is PostListState.Error -> Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                is PostListState.Loading -> {
                    if (it.isShow) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }

                is PostListState.Posts -> {
                    //future can add data in local db(room) and display data from local db
                    //after the network call success, the data will be diff it with local db
                    adapter.submitList(it.posts)
                }

                is PostListState.AddedNewPost -> {
                    //because add record to dp have delay, so we add it to the top of list
                    adapter.currentList.toMutableList().apply {
                        add(0, it.post)
                    }.also {
                        adapter.submitList(it)
                    }
                    //because add the new post to the top of list, so we scroll to top
                    binding.posts.smoothScrollToPosition(0)
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.posts.adapter = null
        _binding = null
    }

    /**
     * If want to refresh the data when user back to this screen
     * add viewModel.getPosts() in onResume()
     */
    override fun onResume() {
        super.onResume()
    }
}