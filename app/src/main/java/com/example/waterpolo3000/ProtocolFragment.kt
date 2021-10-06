package com.example.waterpolo3000

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.waterpolo3000.adapters.ProtocolGoalTypeAdapter
import com.example.waterpolo3000.adapters.ProtocolPersonalFoulAdapter
import com.example.waterpolo3000.adapters.ProtocolTeamAdapter
import com.example.waterpolo3000.adapters.ProtocolTeamEditAdapter
import com.example.waterpolo3000.data.AppDatabase
import com.example.waterpolo3000.databinding.FragmentProtocolBinding
import com.example.waterpolo3000.viewmodels.ProtocolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProtocolFragment : Fragment() {

    private lateinit var binding: FragmentProtocolBinding
    private val viewModel: ProtocolViewModel by viewModels()

    lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProtocolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.db = AppDatabase.getInstance(requireContext())

        val adapterBlue = ProtocolTeamAdapter()
        val adapterWhite = ProtocolTeamAdapter()
        val adapterPersonalFoul = ProtocolPersonalFoulAdapter()
        val adapterGoalType = ProtocolGoalTypeAdapter()
        val adapterEditTeamWhite = ProtocolTeamEditAdapter()
        adapterEditTeamWhite.viewModelOut = viewModel
        val adapterEditTeamBlue = ProtocolTeamEditAdapter()
        adapterEditTeamBlue.viewModelOut = viewModel
        binding.teamBlueRecyclerview.adapter = adapterBlue
        binding.teamWhiteRecyclerview.adapter = adapterWhite
        binding.personalFoulRecyclerview.adapter = adapterPersonalFoul
        binding.goalsRecyclerview.adapter = adapterGoalType
        binding.editWhiteRecyclerview.adapter = adapterEditTeamWhite
        binding.editBlueRecyclerview.adapter = adapterEditTeamBlue

        subscribeUiTeams(adapterBlue, adapterWhite)
        subscribeUiPersonalFouls(adapterPersonalFoul)
        subscribeUiGoals(adapterGoalType, binding)
        subscribeUiEditTeams(adapterEditTeamWhite, adapterEditTeamBlue, binding)

        binding.setClickListener {
            val btnId = resources.getResourceName(it.id).split("/")[1]
            binding.edit = listOf("btn_save", "btn_cancel").contains(btnId)
            when(btnId){
                "btn_save" -> viewModel.storePlayerUpdated()
                "btn_cancel" -> {
                    viewModel.clearPlayerUpdate()
                    // needed to not to the have the changed names after a cancel
                    adapterEditTeamWhite.notifyDataSetChanged()
                    adapterEditTeamBlue.notifyDataSetChanged()
                }
            }
        }
    }

    private fun subscribeUiTeams(adapterBlue: ProtocolTeamAdapter, adapterWhite: ProtocolTeamAdapter) {
        viewModel.protocolForTeamBlue.observe(viewLifecycleOwner) { result ->
            adapterBlue.submitList(result)
        }
        viewModel.protocolForTeamWhite.observe(viewLifecycleOwner) { result ->
            adapterWhite.submitList(result)
        }
    }

    private fun subscribeUiPersonalFouls(adapter: ProtocolPersonalFoulAdapter) {
        viewModel.protocolForPersonalFoul.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

    private fun subscribeUiGoals(adapter: ProtocolGoalTypeAdapter, binding: FragmentProtocolBinding) {
        viewModel.protocolGoalType.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
        viewModel.goals.observe(viewLifecycleOwner) { result ->
            binding.goalsWhite = result.white
            binding.goalsBlue = result.blue
        }
        viewModel.goalsFirstQuarter.observe(viewLifecycleOwner) { result ->
            binding.goalsWhiteFirst = result.white
            binding.goalsBlueFirst = result.blue
        }
        viewModel.goalsSecondQuarter.observe(viewLifecycleOwner) { result ->
            binding.goalsWhiteSecond = result.white
            binding.goalsBlueSecond = result.blue
        }
        viewModel.goalsThirdQuarter.observe(viewLifecycleOwner) { result ->
            binding.goalsWhiteThird = result.white
            binding.goalsBlueThird = result.blue
        }
        viewModel.goalsFourthQuarter.observe(viewLifecycleOwner) { result ->
            binding.goalsWhiteFourth = result.white
            binding.goalsBlueFourth = result.blue
        }
    }

    private fun subscribeUiEditTeams(adapterWhite: ProtocolTeamEditAdapter, adapterBlue: ProtocolTeamEditAdapter, binding: FragmentProtocolBinding) {
        binding.edit = true
        viewModel.editTeamWhite.observe(viewLifecycleOwner) { result ->
            adapterWhite.submitList(result)
        }
        viewModel.editTeamBlue.observe(viewLifecycleOwner) { result ->
            adapterBlue.submitList(result)
        }
    }

    // TODO: convert to data binding if applicable
//    private fun processPlayer() {
//        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
//            PLANT_LIST_PAGE_INDEX

//    }
}
