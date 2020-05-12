package com.theuhooi.uhooipicbook.modules.monsterlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodel.MonsterListViewModel

class MonsterListFragment : Fragment() {

    // region Stored Instance Properties

    private var listener: OnListFragmentInteractionListener? = null

    // navGraphViewModelsを使うことで同じnavGraph内で同じViewModelのインスタンスを保有できる
    private val viewModel: MonsterListViewModel by navGraphViewModels(R.id.nav_graph)

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMonsterListBinding.inflate(inflater, container, false).let {
        it.monsterListRecyclerview.apply {
            adapter = MonsterListRecyclerViewAdapter(listener, context, viewModel, viewLifecycleOwner)
            layoutManager = LinearLayoutManager(context)
        }
        //viewModel.loadMonsters()
        it.viewModel = viewModel
        it.lifecycleOwner = viewLifecycleOwner
        it.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnListFragmentInteractionListener) {
            this.listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()

        this.listener = null
    }

    // endregion

    // region Interfaces

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: MonsterItem)
    }

    // endregion

}
