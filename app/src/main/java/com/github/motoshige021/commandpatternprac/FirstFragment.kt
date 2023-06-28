package com.github.motoshige021.commandpatternprac

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.motoshige021.commandpatternprac.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var appViewModel : AppViewModel
    private lateinit var appMediator : AppMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        appMediator = AppMediator(appViewModel, this)
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        // Mediatorにobserve処理を移動
        //appViewModel.editor.updated.observe(this) {
        appMediator.setUpdateProc{
            binding.textviewFirst.setTextColor(appViewModel.editor.textColor.toArgb())
            binding.textviewFirst.setText(appViewModel.editor.getBackup())
        }

        var msg1ButtonClickListener =
            SimpleButtonClickListener(Mes1ButtonCommandImpl(binding.textviewSimple))
        binding.buttonMes1.setOnClickListener(msg1ButtonClickListener)

        var msg2ButtonClickListener =
            SimpleButtonClickListener(Msg2ButtonCommandImpl(binding.textviewSimple))
        binding.buttonMes2.setOnClickListener(msg2ButtonClickListener)

        /*
        var msg3ButtonClickListener =
            SimpleButtonClickListener(Msg3ButtonCommandImpl(binding.textviewSimple))
        binding.buttonMes3.setOnClickListener(msg3ButtonClickListener)
        */

        var inputButtonClickListener = ButtonClickListener(
            InputButtonCommand(appViewModel, appViewModel.editor), appViewModel)
        binding.buttonInput.setOnClickListener(inputButtonClickListener)

        var cutButtonClickListener = ButtonClickListener(
            CutButtonCOmmand(appViewModel, appViewModel.editor), appViewModel)
        binding.buttonCut.setOnClickListener(cutButtonClickListener)

        // Mediatorにobserve処理を移動
        //appViewModel.editor.cutText.observe(this) {
        appMediator.setCutButtonProc {
            binding.buttonzPaste.isEnabled = true
        }
        var pastoButtonClickListener = ButtonClickListener(
            PastoButtonCommand(appViewModel, appViewModel.editor), appViewModel)
        binding.buttonzPaste.setOnClickListener(pastoButtonClickListener)
        binding.buttonzPaste.isEnabled =false
        appMediator.setPasteButtonProc {
            binding.buttonzPaste.isEnabled =false
        }

        var undoButtonClickListener = ButtonClickListener(
            UndoButtonCommand(appViewModel, appViewModel.editor), appViewModel)
        binding.buttonUndo.setOnClickListener(undoButtonClickListener)

        appMediator.setUndoButtonProc { historySize: Int ->
            binding.buttonUndo.isEnabled = historySize != 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class SimpleButtonClickListener(in_command: SimpleButtonCommand) : View.OnClickListener {
        private lateinit var command : SimpleButtonCommand
        init {
            command = in_command
        }
        override fun onClick(v: View?) {
            command.execute()
        }
    }

    inner class ButtonClickListener(in_command: ButtonCommand, in_appModel: AppViewModel)
        : View.OnClickListener{
        private lateinit var buttonCommand: ButtonCommand
        private lateinit var  appModel: AppViewModel
        init {
            buttonCommand = in_command
            appModel = in_appModel
        }

        override fun onClick(v: View?) {
            if (buttonCommand is UndoButtonCommand) {
                if (!appModel.undo(buttonCommand)) {
                    Toast.makeText(this@FirstFragment.requireContext(),
                        "No History of Undo", Toast.LENGTH_SHORT).show()
                }
                return
            }
            appModel.executeCommand(buttonCommand)
        }
    }
}