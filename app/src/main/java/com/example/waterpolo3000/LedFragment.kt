package com.example.waterpolo3000

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.waterpolo3000.data.AppDatabase
import com.example.waterpolo3000.databinding.FragmentLedBoardBinding
import com.example.waterpolo3000.utilities.ProcessBT
import com.example.waterpolo3000.viewmodels.LedViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Observer

@AndroidEntryPoint
class LedFragment : Fragment() {

    private lateinit var binding: FragmentLedBoardBinding
    private val viewModel: LedViewModel by viewModels()

    lateinit var database: AppDatabase

    val brightnessText = "brightness%"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLedBoardBinding.inflate(inflater, container, false)
        binding.btnConnectMainBoard.visibility = View.GONE
        binding.btnConnectShotclock1.visibility = View.GONE
        binding.btnConnectShotclock2.visibility = View.GONE
        binding.btnConnectShotclock3.visibility = View.GONE
        binding.btnConnectShotclock4.visibility = View.GONE
        binding.sliderMainBoard.visibility = View.GONE
        binding.sliderShotclock1.visibility = View.GONE
        binding.sliderShotclock2.visibility = View.GONE
        binding.sliderShotclock3.visibility = View.GONE
        binding.sliderShotclock4.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ProcessBT.viewModel = viewModel
        val myBThandler = ProcessBT(activity)
        myBThandler.searchAllDevice()
        viewModel.btHandler = myBThandler
        if(myBThandler.remoteMainBoard != null){
            binding.btnConnectMainBoard.visibility = View.VISIBLE
            binding.textViewMainBoardStatus.visibility = View.GONE
        }
        if(myBThandler.remoteShotclock1 != null){
            binding.btnConnectShotclock1.visibility = View.VISIBLE
            binding.textViewShotclock1Status.visibility = View.GONE
        }

        // clickListener
        binding.btnConnectMainBoard.setOnClickListener() {
            viewModel.connectMainBoard()
            if(ProcessBT.mainBoardConnected){
                binding.btnConnectMainBoard.visibility = View.GONE
            }
        }
        binding.btnConnectShotclock1.setOnClickListener() {
            viewModel.connectShotclock1()
            if(ProcessBT.shotclock1Connected){
                binding.btnConnectShotclock1.visibility = View.GONE
            }
        }
        binding.btnConnectShotclock2.setOnClickListener() {
            viewModel.connectShotclock2()
            if(ProcessBT.shotclock2Connected){
                binding.btnConnectShotclock2.visibility = View.GONE
            }
        }

        binding.sliderMainBoard.addOnChangeListener() { _, value, _ ->
            val output = "$brightnessText${value.toInt()}"
            Log.d(TAG, "output: $output")
            ProcessBT.sendMessageToMainBoard(output)
        }

        binding.sliderShotclock1.addOnChangeListener() { _, value, _ ->
            val output = "$brightnessText${value.toInt()}"
            Log.d(TAG, "output: $output")
            ProcessBT.sendMessageToShotclock1(output)
        }

        binding.sliderShotclock2.addOnChangeListener() { _, value, _ ->
            val output = "$brightnessText${value.toInt()}"
            Log.d(TAG, "output: $output")
            ProcessBT.sendMessageToShotclock2(output)
        }

//        binding.test.setOnClickListener() {
//            val input = "brightness%20"
//            Log.d(TAG, "isconnected: ${bluetoothSocket[0].isConnected}")
//        }
        initObservers()
    }

    private fun initObservers() {
        // paired
        val mainBoardPairedObserver = Observer<Boolean> { newValue ->
            binding.btnConnectMainBoard.visibility = if (newValue) View.VISIBLE else View.GONE
            binding.btnConnectMainBoard.text = getString(R.string.connect)

            binding.textViewMainBoard.visibility = if (newValue) View.VISIBLE else View.GONE
        }
        val shotclock1PairedObserver = Observer<Boolean> { newValue ->
            binding.btnConnectShotclock1.visibility = if (newValue) View.VISIBLE else View.GONE
            binding.btnConnectShotclock1.text = getString(R.string.connect)

            binding.textViewShotclock1.visibility = if (newValue) View.VISIBLE else View.GONE
        }
        val shotclock2PairedObserver = Observer<Boolean> { newValue ->
            binding.btnConnectShotclock2.visibility = if (newValue) View.VISIBLE else View.GONE
            binding.btnConnectShotclock2.text = getString(R.string.connect)

            binding.textViewShotclock2.visibility = if (newValue) View.VISIBLE else View.GONE
        }
        viewModel.mainBoardPaired.observe(viewLifecycleOwner, mainBoardPairedObserver)
        viewModel.shotclock1Paired.observe(viewLifecycleOwner, shotclock1PairedObserver)
        viewModel.shotclock2Paired.observe(viewLifecycleOwner, shotclock2PairedObserver)

        // connected
        val mainBoardConnectedObserver = Observer<Boolean> { newValue ->
            binding.btnConnectMainBoard.text = if(newValue) getString(R.string.connected) else getString(R.string.connect)
            binding.sliderMainBoard.visibility = if (newValue) View.VISIBLE else View.GONE
        }
        val shotclock1ConnectedObserver = Observer<Boolean> { newValue ->
            binding.btnConnectShotclock1.text = if(newValue) getString(R.string.connected) else getString(R.string.connect)
            binding.sliderShotclock1.visibility = if (newValue) View.VISIBLE else View.GONE
        }
        val shotclock2ConnectedObserver = Observer<Boolean> { newValue ->
            binding.btnConnectShotclock2.text = if(newValue) getString(R.string.connected) else getString(R.string.connect)
            binding.sliderShotclock2.visibility = if (newValue) View.VISIBLE else View.GONE
        }
        viewModel.mainBoardConnected.observe(viewLifecycleOwner, mainBoardConnectedObserver)
        viewModel.shotclock1Connected.observe(viewLifecycleOwner, shotclock1ConnectedObserver)
        viewModel.shotclock2Connected.observe(viewLifecycleOwner, shotclock2ConnectedObserver)
    }
}