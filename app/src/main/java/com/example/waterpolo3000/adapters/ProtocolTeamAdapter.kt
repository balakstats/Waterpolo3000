package com.example.waterpolo3000.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterpolo3000.GameFragment
import com.example.waterpolo3000.data.GameEventView
import com.example.waterpolo3000.data.ProtocolTeam
import com.example.waterpolo3000.databinding.ListItemProtocolTeamBinding

/**
 * Adapter for the [RecyclerView] in [GameFragment].
 */
class ProtocolTeamAdapter : ListAdapter<ProtocolTeam, RecyclerView.ViewHolder>(ProtocolTeamDiffCallback()) { // replace

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProtocolTeamViewHolder(
            ListItemProtocolTeamBinding.inflate(  // replace
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val protocolTeam = getItem(position)
            (holder as ProtocolTeamViewHolder).bind(protocolTeam)
    }


class ProtocolTeamViewHolder(
        private val binding: ListItemProtocolTeamBinding // replace
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
//            binding.setClickListener {
//                binding.gameEvent?.let { gameEvent ->
//                    navigateToGameEvent(gameEvent, it)
//                }
//            }
        }

//        private fun navigateToGameEvent(gameEvent: GameEvent,view: View) {
//            val direction =
//                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment( // replace
//                    gameEvent.participant
//                )
//            view.findNavController().navigate(direction)
//        }

        fun bind(item: ProtocolTeam) {
            binding.apply {
                protocolTeam = item
                executePendingBindings()
            }
        }
    }
}

private class ProtocolTeamDiffCallback: DiffUtil.ItemCallback<ProtocolTeam>() {

    override fun areItemsTheSame(oldItem: ProtocolTeam, newItem: ProtocolTeam): Boolean {
        return oldItem.number == newItem.number // change to guid
    }

    override fun areContentsTheSame(oldItem: ProtocolTeam, newItem: ProtocolTeam): Boolean {
        return oldItem == newItem
    }
}