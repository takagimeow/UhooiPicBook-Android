package com.theuhooi.uhooipicbook.ui.monsterlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.theuhooi.uhooipicbook.BuildConfig
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.MonsterListFragmentBinding
import com.theuhooi.uhooipicbook.ui.motion.Stagger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MonsterListFragment : Fragment(R.layout.monster_list_fragment) {

    // region Stored Instance Properties

    private val viewModel: MonsterViewModel by hiltNavGraphViewModels(R.id.monster_nav_graph)

    // endregion

    // region View Life-Cycle Methods

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val binding = MonsterListFragmentBinding.bind(view)
        val list = binding.monsterListRecyclerview
        val adapter = MonsterListAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    TransitionManager.beginDelayedTransition(list, Stagger())
                    adapter.submitList(it.monsterItems)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.monster_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.contact_us_menu_item -> {
                val action =
                    MonsterListFragmentDirections.actionListToWebView(getString(R.string.contact_us_url))
                findNavController().navigate(action)
                true
            }
            R.id.privacy_policy_menu_item -> {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_url)))
                startActivity(intent)
                true
            }
            R.id.licenses_menu_item -> {
                findNavController().navigate(MonsterListFragmentDirections.actionListToLicenses())
                true
            }
            R.id.about_this_app_menu_item -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.app_name)
                    .setMessage(
                        """
                        ${getString(R.string.this_app_is_open_source_software)}
                        ${getString(R.string.uhooipicbook_github_url)}
                        
                        ${getString(R.string.version)} ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})
                        ${getString(R.string.copyright)}
                        """.trimIndent()
                    )
                    .setPositiveButton(R.string.ok, null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // endregion

}
