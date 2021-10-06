package com.example.waterpolo3000

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.waterpolo3000.adapters.GameEventAdapter
import com.example.waterpolo3000.data.AppDatabase
import com.example.waterpolo3000.data.ExclResult
import com.example.waterpolo3000.databinding.FragmentGameBinding
import com.example.waterpolo3000.game.GameControl
import com.example.waterpolo3000.utilities.*
import com.example.waterpolo3000.viewmodels.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()
    private val viewModely: GameViewModel by viewModels()

    private lateinit var v: View

    private var playerBtnPressed: Button? = null
    private var eventBtnPressed: Button? = null

    lateinit var database: AppDatabase

    private var tempCurrentCountdown: Long = 0

    val myArray = arrayOf("A", "O")
    private val exclusionResultWhite = mutableListOf<ExclResult>(
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", "")
    )

    private val exclusionResultBlue = mutableListOf<ExclResult>(
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", ""),
        ExclResult("", "", "")
    )

    lateinit var bindingButtonsBlue: List<Button>
    lateinit var bindingButtonsWhite: List<Button>
    lateinit var playerBtnStringsWhite: List<Int>
    lateinit var playerBtnStringsBlue: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.db = AppDatabase.getInstance(requireContext())
        viewModel.init()
        binding = FragmentGameBinding.inflate(inflater, container, false)
        v = binding.root
        bindingButtonsBlue = listOf(
            binding.btnB1,
            binding.btnB2,
            binding.btnB3,
            binding.btnB4,
            binding.btnB5,
            binding.btnB6,
            binding.btnB7,
            binding.btnB8,
            binding.btnB9,
            binding.btnB10,
            binding.btnB11,
            binding.btnB12,
            binding.btnB13
        )
        bindingButtonsWhite = listOf(
            binding.btnW1,
            binding.btnW2,
            binding.btnW3,
            binding.btnW4,
            binding.btnW5,
            binding.btnW6,
            binding.btnW7,
            binding.btnW8,
            binding.btnW9,
            binding.btnW10,
            binding.btnW11,
            binding.btnW12,
            binding.btnW13
        )
        playerBtnStringsWhite = listOf(
            R.string.W1,
            R.string.W2,
            R.string.W3,
            R.string.W4,
            R.string.W5,
            R.string.W6,
            R.string.W7,
            R.string.W8,
            R.string.W9,
            R.string.W10,
            R.string.W11,
            R.string.W12,
            R.string.W13,
        )
        playerBtnStringsBlue = listOf(
            R.string.B1,
            R.string.B2,
            R.string.B3,
            R.string.B4,
            R.string.B5,
            R.string.B6,
            R.string.B7,
            R.string.B8,
            R.string.B9,
            R.string.B10,
            R.string.B11,
            R.string.B12,
            R.string.B13,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GameEventAdapter()
        adapter.viewModelOut = viewModel
        // scroll always the top item(last inserted) in recyclerview
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.gameEventRecyclerview.smoothScrollToPosition(0)
            }
        })
        binding.gameEventRecyclerview.adapter = adapter
        binding.setClickListener {
            myVibrate()
            val btnId = resources.getResourceName(it.id).split("/")[1]
            // handle game event buttons
            when (btnId.split("_")[1]) {
                "event" -> {
                    if (playerBtnPressed == null && eventBtnPressed == null) {
                        eventBtnPressed = it as Button
                        eventBtnPressed!!.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.buttonActive
                            )
                        )
                    } else if (playerBtnPressed != null && eventBtnPressed == null) {
                        playerBtnPressed!!.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                if (resources.getResourceName(playerBtnPressed!!.id)
                                        .split("_")[1].contains("B")
                                ) R.color.blue else R.color.white
                            )
                        )
                        viewModel.processGameEvent(
                            btnId.split("_")[3],
                            resources.getResourceName(playerBtnPressed!!.id).split("/")[1]
                        )
                        eventBtnPressed = null
                        playerBtnPressed = null
                    } else if (playerBtnPressed == null && eventBtnPressed != null) {
                        eventBtnPressed!!.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                if (resources.getResourceName(eventBtnPressed!!.id)
                                        .split("_")[2].contains("goal")
                                ) R.color.green else R.color.red
                            )
                        )
                        eventBtnPressed = if (eventBtnPressed != it) {
                            it.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.buttonActive
                                )
                            )
                            it as Button
                        } else {
                            null
                        }
                    }
                }

                "time" -> {
                    tempCurrentCountdown = GameControl.currentCountdown
                    viewModel.processTime(btnId.split("_")[2])
                    if (btnId.split("_")[2] == "timeout") {
                        val dialog = Dialog(requireContext())
                        dialog.setContentView(R.layout.dialog_timeout)

                        val dialogButtonWhite = dialog.findViewById<Button>(R.id.btn_white)
                        val dialogButtonCancel = dialog.findViewById<Button>(R.id.btn_cancel)
                        val dialogButtonBlue = dialog.findViewById<Button>(R.id.btn_blue)

                        dialogButtonWhite.setOnClickListener {
                            Toast.makeText(requireContext(), "timeout white", Toast.LENGTH_SHORT)
                                .show()
                            viewModel.storeTimeout(WHITE, tempCurrentCountdown)
                            tempCurrentCountdown = 0
                            dialog.dismiss()
                        }
                        dialogButtonBlue.setOnClickListener {
                            Toast.makeText(requireContext(), "timeout blue", Toast.LENGTH_SHORT)
                                .show()
                            viewModel.storeTimeout(BLUE, tempCurrentCountdown)
                            tempCurrentCountdown = 0
                            dialog.dismiss()
                        }
                        dialogButtonCancel.setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.show()
                    }
                }

                else -> {
                    if (playerBtnPressed == null && eventBtnPressed == null) {
                        playerBtnPressed = it as Button
                        playerBtnPressed!!.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.buttonActive
                            )
                        )

                    } else if (playerBtnPressed == null && eventBtnPressed != null) {
                        eventBtnPressed!!.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                if (resources.getResourceName(eventBtnPressed!!.id)
                                        .split("_")[2].contains("goal")
                                ) R.color.green else R.color.red
                            )
                        )
                        viewModel.processGameEvent(
                            resources.getResourceName(eventBtnPressed!!.id)
                                .split("/")[1].split("_")[3], btnId
                        )
                        playerBtnPressed = it as Button
                        eventBtnPressed = null
                        playerBtnPressed = null

                    } else if (playerBtnPressed != null && eventBtnPressed == null) {
                        playerBtnPressed!!.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                if (resources.getResourceName(playerBtnPressed!!.id)
                                        .split("_")[1].contains("B")
                                ) R.color.blue else R.color.white
                            )
                        )
                        playerBtnPressed = if (playerBtnPressed != it) {
                            it.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.buttonActive
                                )
                            )
                            it as Button
                        } else {
                            null
                        }
                    }
                }
            }
        }

        initObservers()
        subscribeUi(adapter, binding)
    }

    private fun myVibrate() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when (item.itemId) {
            R.id.btn_new_game -> {
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_edit_settings)

                val dialogButtonOk = dialog.findViewById<Button>(R.id.dialogBtnOK)
                val dialogButtonCancel = dialog.findViewById<Button>(R.id.dialogBtnCancel)

                val numPickerSectionLength =
                    dialog.findViewById<NumberPicker>(R.id.numberpicker_game_section)
                val numPickerBreak = dialog.findViewById<NumberPicker>(R.id.numberpicker_break)
                val numPickerNumberOfSection =
                    dialog.findViewById<NumberPicker>(R.id.numberpicker_number_of_section)
                numPickerSectionLength.maxValue = 20
                numPickerSectionLength.minValue = 1
                numPickerSectionLength.value = 8
                numPickerBreak.maxValue = 20
                numPickerBreak.minValue = 1
                numPickerBreak.value = 2
                numPickerNumberOfSection.maxValue = 4
                numPickerNumberOfSection.minValue = 1
                numPickerNumberOfSection.value = 4

                dialogButtonOk.setOnClickListener {
                    Toast.makeText(requireContext(), "test me", Toast.LENGTH_SHORT).show()
                    GameControl.gameSectionLength = numPickerSectionLength.value * 60
                    GameControl.numberOfGameSection = numPickerNumberOfSection.value
                    viewModel.newGame()
                    dialog.dismiss()
                }

                dialogButtonCancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
                true
            }
            R.id.btn_edit_main_time -> {
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_edit_time)

                val dialogButtonOk = dialog.findViewById<Button>(R.id.dialogBtnOK)
                val dialogButtonCancel = dialog.findViewById<Button>(R.id.dialogBtnCancel)

                val numPickerMinutes = dialog.findViewById<NumberPicker>(R.id.numberpicker_minutes)
                val numPickerSeconds = dialog.findViewById<NumberPicker>(R.id.numberpicker_seconds)
                val numPickerSecondsSmall =
                    dialog.findViewById<NumberPicker>(R.id.numberpicker_seconds_small)
                numPickerMinutes.maxValue = 20
                numPickerMinutes.minValue = 0
                numPickerMinutes.value = 0
                numPickerSeconds.maxValue = 59
                numPickerSeconds.minValue = 0
                numPickerSeconds.value = 0
                numPickerSecondsSmall.maxValue = 9
                numPickerSecondsSmall.minValue = 0
                numPickerSecondsSmall.value = 0

                dialogButtonOk.setOnClickListener {
                    Toast.makeText(requireContext(), "test me", Toast.LENGTH_SHORT).show()
                    GameControl.currentCountdown =
                        ((numPickerMinutes.value * 60 * 1000) + (numPickerSeconds.value * 1000) + (numPickerSecondsSmall.value * 100)).toLong()
                    GameControl.setGameTimeEdit()
                    dialog.dismiss()
                }

                dialogButtonCancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
                true
            }
            R.id.btn_edit_shotclock -> {
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_edit_shotclock)

                val dialogButtonOk = dialog.findViewById<Button>(R.id.dialogBtnOK)
                val dialogButtonCancel = dialog.findViewById<Button>(R.id.dialogBtnCancel)

                val numPickerSeconds = dialog.findViewById<NumberPicker>(R.id.numberpicker_seconds)
                val numPickerSecondsSmall =
                    dialog.findViewById<NumberPicker>(R.id.numberpicker_seconds_small)
                numPickerSeconds.maxValue = 30
                numPickerSeconds.minValue = 0
                numPickerSeconds.value = 0
                numPickerSecondsSmall.maxValue = 9
                numPickerSecondsSmall.minValue = 0
                numPickerSecondsSmall.value = 0

                dialogButtonOk.setOnClickListener {
                    Toast.makeText(requireContext(), "test me", Toast.LENGTH_SHORT).show()
                    GameControl.currentCountdownShotclock =
                        ((numPickerSeconds.value * 1000) + (numPickerSecondsSmall.value * 100)).toLong()
                    GameControl.setShotclockEdit()
                    dialog.dismiss()
                }

                dialogButtonCancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }

    private fun initObservers() {
        // my livedata
        // Create the observer which updates the UI.
        val mainMinutesObserver = Observer<String> { newValue ->
            binding.mainMinutesView.text = newValue
        }
        val mainSecondsObserver = Observer<String> { newValue ->
            binding.mainSecondsView.text = newValue
        }
        val mainSecondsSmallObserver = Observer<String> { newValue ->
            binding.mainSecondsSmallView.text = newValue
        }
        val shotclockSecondsObserver = Observer<String> { newValue ->
            binding.shotclockSecondsView.text = newValue
        }
        val shotclockSecondsSmallObserver = Observer<String> { newValue ->
            binding.shotclockSecondsSmallView.text = newValue
        }
        val currentGameSectionObserver = Observer<String> { newValue ->
            binding.currentGameSection.text = newValue
            if (newValue.isDigitsOnly()) {
                ProcessBT.sendMessageToMainBoard("gameSection%$newValue")
            }
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.mainMinutes.observe(viewLifecycleOwner, mainMinutesObserver)
        viewModel.mainSeconds.observe(viewLifecycleOwner, mainSecondsObserver)
        viewModel.mainSecondsSmall.observe(viewLifecycleOwner, mainSecondsSmallObserver)
        viewModel.shotclockSeconds.observe(viewLifecycleOwner, shotclockSecondsObserver)
        viewModel.shotclockSecondsSmall.observe(viewLifecycleOwner, shotclockSecondsSmallObserver)
        viewModel.currentGameSection.observe(viewLifecycleOwner, currentGameSectionObserver)

        val timeClickableObserver = Observer<Boolean> { newValue ->
            binding.btnTimeStartStop.isClickable = newValue
            binding.btnTimeShotclockBig.isClickable = newValue
            binding.btnTimeShotclockSmall.isClickable = newValue
        }
        viewModel.timeClickable.observe(viewLifecycleOwner, timeClickableObserver)

        // set exclusion timer on player buttons
        val exclusionTimeObserver = Observer<String> { newValue ->
            val player = newValue.split(":")[0]
            val value1 = newValue.split(":")[1].split(".")[0].toInt()
            val value2 = newValue.split(":")[1].split(".")[1].toInt()
            val cap = player.split("_")[1]
            val index = player.split("_")[2]
            val btn = if(cap == "W") bindingButtonsWhite[index.toInt()-1] else bindingButtonsBlue[index.toInt()-1]
            val textColor = if (value1 < 1 && value2 < 1) (if(cap == "W") "#FF000000"/*black*/ else "#FFFFFFFF"/*white*/) else "#E91E63"/*red*/
            // btn_W_1:20
            btn.text = if(value1 < 1 && value2 < 1) (if(cap == "W") getString(playerBtnStringsWhite[index.toInt()-1]) else getString(playerBtnStringsBlue[index.toInt()-1])) else ((if(value1 < 1) "$value1.$value2" else value1).toString())
            btn.setTextColor(Color.parseColor(textColor))
        }
        viewModel.exclusionTime.observe(viewLifecycleOwner, exclusionTimeObserver)
    }

    private fun subscribeUi(adapter: GameEventAdapter, binding: FragmentGameBinding) {
        Log.d(TAG, "GameFragment.subscribeUi enter")
        viewModel.gameEvents.observe(viewLifecycleOwner) { result ->
//            binding.hasPlantings = !result.isNullOrEmpty() // make recyclerview visible
            adapter.submitList(result)
        }
        viewModel.goals.observe(viewLifecycleOwner) { result ->
            binding.goalsWhite = result.white
            binding.goalsBlue = result.blue
            val mainBoardString = "result%${result.white}:${result.blue}"
            ProcessBT.sendMessageToMainBoard(mainBoardString)
        }

        viewModel.timeoutForWhite.observe(viewLifecycleOwner) { result ->
            binding.timeoutWhiteValue = result.count
        }
        viewModel.timeoutForBlue.observe(viewLifecycleOwner) { result ->
            binding.timeoutBlueValue = result.count
        }

        // Exclusions
        val viewModelMembersBlue = listOf(
            viewModel.exB1,
            viewModel.exB2,
            viewModel.exB3,
            viewModel.exB4,
            viewModel.exB5,
            viewModel.exB6,
            viewModel.exB7,
            viewModel.exB8,
            viewModel.exB9,
            viewModel.exB10,
            viewModel.exB11,
            viewModel.exB12,
            viewModel.exB13
        )
        val bindingExclusionsBlue = listOf(
            binding.exclusionB1,
            binding.exclusionB2,
            binding.exclusionB3,
            binding.exclusionB4,
            binding.exclusionB5,
            binding.exclusionB6,
            binding.exclusionB7,
            binding.exclusionB8,
            binding.exclusionB9,
            binding.exclusionB10,
            binding.exclusionB11,
            binding.exclusionB12,
            binding.exclusionB13
        )
        viewModelMembersBlue.forEachIndexed { index, element ->
            element.observe(viewLifecycleOwner) { result ->
                // check for change in exclusion list and send info to mainBoard
                if (result != null) {
                    sendExclusionToMainBoard(exclusionResultBlue, result, index, BLUE)
                }
                bindingExclusionsBlue[index].text =
                    if (result != null) "${result.e1} ${result.e2} ${result.e3}" else ""
                if (!myArray.contains(result.e1) && !myArray.contains(result.e2) && result.e3.isEmpty()) {
                    bindingButtonsBlue[index].isClickable = true
                    bindingButtonsBlue[index].setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                } else {
                    bindingButtonsBlue[index].isClickable = false
                    bindingButtonsBlue[index].setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray_50_a600
                        )
                    )
                }
            }
        }

        val viewModelMembersWhite = listOf(
            viewModel.exW1,
            viewModel.exW2,
            viewModel.exW3,
            viewModel.exW4,
            viewModel.exW5,
            viewModel.exW6,
            viewModel.exW7,
            viewModel.exW8,
            viewModel.exW9,
            viewModel.exW10,
            viewModel.exW11,
            viewModel.exW12,
            viewModel.exW13
        )
        val bindingExclusionsWhite = listOf(
            binding.exclusionW1,
            binding.exclusionW2,
            binding.exclusionW3,
            binding.exclusionW4,
            binding.exclusionW5,
            binding.exclusionW6,
            binding.exclusionW7,
            binding.exclusionW8,
            binding.exclusionW9,
            binding.exclusionW10,
            binding.exclusionW11,
            binding.exclusionW12,
            binding.exclusionW13
        )
        viewModelMembersWhite.forEachIndexed { index, element ->
            element.observe(viewLifecycleOwner) { result ->
                // check for change in exclusion list and send info to mainBoard
                if (result != null) {
                    sendExclusionToMainBoard(exclusionResultWhite, result, index, WHITE)
                }
                bindingExclusionsWhite[index].text =
                    if (result != null) "${result.e1} ${result.e2} ${result.e3}" else ""
                if (!myArray.contains(result.e1) && !myArray.contains(result.e2) && result.e3.isEmpty()) {
                    bindingButtonsWhite[index].isClickable = true
                    bindingButtonsWhite[index].setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                } else {
                    bindingButtonsWhite[index].isClickable = false
                    bindingButtonsWhite[index].setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray_50_a600
                        )
                    )
                }
            }
        }
        Log.d(TAG, "GameFragment.subscribeUi leave")
    }

    private fun sendExclusionToMainBoard(
        exclusionResult: MutableList<ExclResult>,
        result: ExclResult,
        index: Int,
        cap: String
    ) {
        if (
            exclusionResult[index].e1 != result.e1 ||
            exclusionResult[index].e2 != result.e2 ||
            exclusionResult[index].e3 != result.e3
        ) {
            Log.d(TAG, "CHANGE")
            exclusionResult[index] = result
            var numberOfExclusions = 0
            numberOfExclusions = if (
                myArray.contains(result.e1) ||
                myArray.contains(result.e2) ||
                myArray.contains(result.e3)
            ) {
                3
            } else {
                result.e1.length + result.e2.length + result.e3.length
            }
            ProcessBT.sendMessageToMainBoard("player%$cap%${index + 1}%$numberOfExclusions")
        } else {
            Log.d(TAG, "NO CHANGE")
        }
    }

// TODO: convert to data binding if applicable
//    private fun processPlayer() {
//        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
//            PLANT_LIST_PAGE_INDEX

//    }
}
