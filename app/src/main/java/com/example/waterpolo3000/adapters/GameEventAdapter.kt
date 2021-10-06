package com.example.waterpolo3000.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterpolo3000.GameFragment
import com.example.waterpolo3000.R
import com.example.waterpolo3000.data.GameEventView
import com.example.waterpolo3000.databinding.ListItemGameEventBinding
import com.example.waterpolo3000.game.GameControl
import com.example.waterpolo3000.viewmodels.GameViewModel
import com.example.waterpolo3000.utilities.*

/**
 * Adapter for the [RecyclerView] in [GameFragment].
 */
class GameEventAdapter : ListAdapter<GameEventView, RecyclerView.ViewHolder>(GameEventDiffCallback()) {
    lateinit var viewModelOut: GameViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GameEventViewHolder(
            viewModelOut,
            ListItemGameEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val gameEvent = getItem(position)
        (holder as GameEventViewHolder).bind(gameEvent)
    }


    class GameEventViewHolder(
        private val viewModelIn: GameViewModel,
        private val binding: ListItemGameEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener1 {
                binding.edit = !binding.edit
            }

            binding.setClickListener2 {
                binding.edit = true
                binding.gameEvent?.let { gameEvent ->
                    viewModelIn.deleteGameEvent(gameEvent.guid)
                }
            }

            binding.setClickListener3 { go ->
                binding.edit = true

                val dialog = Dialog(go.context)
                dialog.setContentView(R.layout.dialog_select_game_event_time)

                val dialogButtonOk = dialog.findViewById<Button>(R.id.dialogBtnOK)
                val dialogButtonCancel = dialog.findViewById<Button>(R.id.dialogBtnCancel)
                val numPickerMinutes = dialog.findViewById<NumberPicker>(R.id.numberpicker_minutes)
                val numPickerSeconds = dialog.findViewById<NumberPicker>(R.id.numberpicker_seconds)
                val numPickerSecondsSmall = dialog.findViewById<NumberPicker>(R.id.numberpicker_secondsSmall)
                numPickerMinutes.maxValue      = (DEFAULT_GAME_SECTION_LENGTH/60)
                numPickerMinutes.minValue      = MyTimeConverter.getMinutesFromLong(GameControl.currentCountdown)
                numPickerMinutes.value         = MyTimeConverter.getMinutesFromLong(GameControl.currentCountdown)
                numPickerSeconds.maxValue      = 59
                numPickerSeconds.value         = MyTimeConverter.getSecondsFromLong(GameControl.currentCountdown)
                numPickerSecondsSmall.maxValue = 9
                numPickerSecondsSmall.value    = MyTimeConverter.getSecondsSmallFromLong(GameControl.currentCountdown)

                dialogButtonOk.setOnClickListener {
                    val newTime: Long = ((numPickerMinutes.value)*60*1000).toLong()+((numPickerSeconds.value)*1000)+((numPickerSecondsSmall.value)*100)
                    binding.gameEvent?.let { gameEvent ->
                        viewModelIn.updateGameEventTime(gameEvent,newTime)
                    }
                    dialog.dismiss()
//                    Toast.makeText(go.context, "Dismissed..!!", Toast.LENGTH_SHORT).show()
                }
                dialogButtonCancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }

            binding.edit = true

//                    navigateToGameEvent(gameEvent, it)
        }

//        private fun navigateToGameEvent(gameEvent: GameEvent,view: View) {
//            val direction =
//                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment( // replace
//                    gameEvent.participant
//                )
//            view.findNavController().navigate(direction)
//        }

        fun bind(item: GameEventView) {
            binding.apply {
                gameEvent = item
                executePendingBindings()
            }
        }
    }
}

private class GameEventDiffCallback : DiffUtil.ItemCallback<GameEventView>() {

    override fun areItemsTheSame(oldItem: GameEventView, newItem: GameEventView): Boolean {
        return oldItem.guid == newItem.guid
    }

    override fun areContentsTheSame(oldItem: GameEventView, newItem: GameEventView): Boolean {
        return oldItem == newItem
    }
}