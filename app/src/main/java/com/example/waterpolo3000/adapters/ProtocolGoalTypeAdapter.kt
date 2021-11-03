package com.example.waterpolo3000.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterpolo3000.data.ProtocolGameEventType
import com.example.waterpolo3000.data.ProtocolGoalType
import com.example.waterpolo3000.databinding.ListItemGoalsBinding
import com.example.waterpolo3000.databinding.ListItemGoalsBindingImpl
import com.example.waterpolo3000.databinding.ListItemPersonalFoulBinding

/**
 * Adapter for the [RecyclerView] in [ProtocolFragment].
 */
class ProtocolGoalTypeAdapter :
    ListAdapter<ProtocolGoalType, RecyclerView.ViewHolder>(ProtocolGoalTypeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProtocolGoalTypeViewHolder(
            ListItemGoalsBindingImpl.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val protocolGoalType = getItem(position)
        (holder as ProtocolGoalTypeViewHolder).bind(protocolGoalType)
    }


    class ProtocolGoalTypeViewHolder(
        private val binding: ListItemGoalsBinding // replace
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

        fun bind(item: ProtocolGoalType) {
            binding.apply {
                protocolGoalType = item
                executePendingBindings()
            }
        }
    }
}

private class ProtocolGoalTypeDiffCallback : DiffUtil.ItemCallback<ProtocolGoalType>() {

    override fun areItemsTheSame(oldItem: ProtocolGoalType, newItem: ProtocolGoalType): Boolean {
        return oldItem.guid == newItem.guid
    }

    override fun areContentsTheSame(oldItem: ProtocolGoalType, newItem: ProtocolGoalType): Boolean {
        return oldItem == newItem
    }
}