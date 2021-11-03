package com.example.waterpolo3000.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterpolo3000.data.ProtocolGameEventType
import com.example.waterpolo3000.databinding.ListItemPersonalFoulBinding

/**
 * Adapter for the [RecyclerView] in [ProtocolFragment].
 */
class ProtocolPersonalFoulAdapter :
    ListAdapter<ProtocolGameEventType, RecyclerView.ViewHolder>(ProtocolPersonalFoulDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProtocolPersonalFoulViewHolder(
            ListItemPersonalFoulBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val protocolPersonalFoul = getItem(position)
        (holder as ProtocolPersonalFoulViewHolder).bind(protocolPersonalFoul)
    }


    class ProtocolPersonalFoulViewHolder(
        private val binding: ListItemPersonalFoulBinding
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

        fun bind(item: ProtocolGameEventType) {
            binding.apply {
                protocolPersonalFoul = item
                executePendingBindings()
            }
        }
    }
}

private class ProtocolPersonalFoulDiffCallback : DiffUtil.ItemCallback<ProtocolGameEventType>() {

    override fun areItemsTheSame(
        oldItem: ProtocolGameEventType,
        newItem: ProtocolGameEventType
    ): Boolean {
        return oldItem.guid == newItem.guid
    }

    override fun areContentsTheSame(
        oldItem: ProtocolGameEventType,
        newItem: ProtocolGameEventType
    ): Boolean {
        return oldItem == newItem
    }
}