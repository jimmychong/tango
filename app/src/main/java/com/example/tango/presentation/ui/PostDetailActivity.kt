package com.example.tango.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.tango.databinding.ActivityDetailBinding
import com.example.tango.domain.model.Post
import com.example.tango.presentation.post_detail.DetailListState
import com.example.tango.presentation.post_detail.PostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PostDetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PostDetailViewModel>()

    companion object {
        const val POST_KEY = "post"

        fun intentOf(activity: Activity, post: Post): Intent {
            return Intent(activity, PostDetailActivity::class.java).apply {
                putExtra(POST_KEY, post)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        collectDetailState()
        handleAction()
    }

    private fun handleAction() {
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.update.setOnClickListener {
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            val title = TextView(this)
            title.text = "Title"
            layout.addView(title)
            val etTitle = EditText(this)
            etTitle.setText(viewModel.postData?.title.orEmpty())
            etTitle.hint = "Enter Title"
            layout.addView(etTitle)
            val body = TextView(this)
            body.text = "Content"
            layout.addView(body)
            val etBody = EditText(this)
            etBody.isSingleLine = true
            etBody.setText(viewModel.postData?.body.orEmpty())
            etBody.hint = "Enter Body"
            layout.addView(etBody)

            val dialog = AlertDialog.Builder(this)
                .setTitle("Update Post")
                .setView(layout)
                .setPositiveButton("Update") { dialog, _ ->
                    viewModel.updatePost(
                        etTitle.text.toString(),
                        etBody.text.toString()
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }
    }

    private fun collectDetailState() {
        viewModel.state.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is DetailListState.Display -> {
                    binding.update.isEnabled = true
                    binding.title.text = it.displayData.first.title
                    binding.body.text = it.displayData.first.body
                    binding.userName.text = it.displayData.first.userName
                    binding.noOfComments.text = it.displayData.second.toString()
                }

                is DetailListState.Error -> {
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT)
                        .show()
                }

                is DetailListState.Loading -> {
                    binding.progressBar.isVisible = it.isShow
                }
            }
        }.launchIn(lifecycleScope)
    }

}