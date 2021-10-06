package com.example.waterpolo3000.adapters

import android.content.ContentValues.TAG
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterpolo3000.GameFragment
import com.example.waterpolo3000.data.EditTeam
import com.example.waterpolo3000.databinding.ListItemProtocolEditTeamBinding
import com.example.waterpolo3000.viewmodels.GameViewModel
import com.example.waterpolo3000.viewmodels.ProtocolViewModel

/**
 * Adapter for the [RecyclerView] in [GameFragment].
 */
class ProtocolTeamEditAdapter : ListAdapter<EditTeam, RecyclerView.ViewHolder>(ProtocolTeamEditDiffCallback()) {
    lateinit var viewModelOut: ProtocolViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProtocolEditTeamViewHolder(
            viewModelOut,
            ListItemProtocolEditTeamBinding.inflate(  // replace
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val editTeam = getItem(position)
        (holder as ProtocolEditTeamViewHolder).bind(editTeam)
    }

    public override fun getItem(position: Int): EditTeam {
        return super.getItem(position)
    }

    class ProtocolEditTeamViewHolder(
        private val viewModelIn: ProtocolViewModel,
        private val binding: ListItemProtocolEditTeamBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {

            val firstName = binding.protocolItemFirstname
            firstName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    Log.d(TAG, "first: $s")
                    viewModelIn.updatePlayerName(binding.dummyGuid.text.toString(),"firstName",s.toString())
                }
            })

            val lastName = binding.protocolItemLastname
            lastName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    Log.d(TAG, "last: $s")
                    viewModelIn.updatePlayerName(binding.dummyGuid.text.toString(),"lastName",s.toString())
                }
            })

        }

//        private fun navigateToGameEvent(gameEvent: GameEvent,view: View) {
//            val direction =
//                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment( // replace
//                    gameEvent.participant
//                )
//            view.findNavController().navigate(direction)
//        }

        fun bind(item: EditTeam) {
            binding.apply {
                editTeam = item
                executePendingBindings()
            }
        }
    }
}

private class ProtocolTeamEditDiffCallback : DiffUtil.ItemCallback<EditTeam>() {

    override fun areItemsTheSame(oldItem: EditTeam, newItem: EditTeam): Boolean {
        Log.d(TAG, "same?")
        return oldItem.number == newItem.number // change to guid
    }

    override fun areContentsTheSame(oldItem: EditTeam, newItem: EditTeam): Boolean {
        return oldItem == newItem
    }
}