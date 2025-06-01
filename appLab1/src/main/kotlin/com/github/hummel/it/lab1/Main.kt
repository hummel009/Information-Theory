package com.github.hummel.it.lab1

import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubDarkIJTheme
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import java.io.File
import java.util.*
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main() {
	FlatLightLaf.setup()
	EventQueue.invokeLater {
		try {
			UIManager.setLookAndFeel(FlatMTGitHubDarkIJTheme())
			val frame = GUI()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class GUI : JFrame() {
	val alphabet: String = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
	private var vigenere: Boolean = true

	init {
		title = "Vigenere & Column Cipher Machine"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 450, 210)

		val contentPanel = JPanel()
		contentPanel.border = EmptyBorder(5, 5, 5, 5)
		contentPanel.layout = BorderLayout(0, 0)
		contentPanel.layout = GridLayout(0, 1, 0, 0)
		contentPane = contentPanel

		val inputPanel = JPanel()
		val inputLabel = JLabel("Input path:")
		inputLabel.preferredSize = Dimension(80, inputLabel.preferredSize.height)
		val inputField = JTextField(20)
		val inputButton = JButton("Select path")
		inputButton.addActionListener { selectPath(inputField) }
		inputPanel.add(inputLabel)
		inputPanel.add(inputField)
		inputPanel.add(inputButton)

		val outputPanel = JPanel()
		val outputLabel = JLabel("Output path:")
		outputLabel.preferredSize = Dimension(80, outputLabel.preferredSize.height)
		val outputField = JTextField(20)
		val outputButton = JButton("Select path")
		outputButton.addActionListener { selectPath(outputField) }
		outputPanel.add(outputLabel)
		outputPanel.add(outputField)
		outputPanel.add(outputButton)

		val keyPanel = JPanel()
		val keyLabel = JLabel("Key:")
		val keyField = JTextField(20)
		keyPanel.add(keyLabel)
		keyPanel.add(keyField)

		val radioPanel = JPanel()
		val radioVigenere = JRadioButton("Vigenere")
		val radioColumn = JRadioButton("Column Method")
		radioVigenere.isSelected = true
		radioVigenere.addActionListener {
			vigenere = true
			radioColumn.isSelected = false
		}
		radioColumn.addActionListener {
			vigenere = false
			radioVigenere.isSelected = false
		}
		radioPanel.add(radioVigenere)
		radioPanel.add(radioColumn)

		val processPanel = JPanel()
		val processEncode = JButton("Encode")
		val processDecode = JButton("Decode")
		processEncode.addActionListener { encode(inputField, outputField, keyField) }
		processDecode.addActionListener { decode(inputField, outputField, keyField) }
		processPanel.add(processEncode)
		processPanel.add(processDecode)

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(radioPanel)
		contentPanel.add(keyPanel)
		contentPanel.add(processPanel)

		setLocationRelativeTo(null)
	}

	private fun encode(inputField: JTextField, outputField: JTextField, keyField: JTextField) {
		val data = error(inputField, outputField, keyField)
		val error = data.first

		if (!error) {
			val key = data.second.first
			val msg = data.second.second

			val res = if (vigenere) {
				val machine = Vigenere(msg, key, this)
				machine.encode()
			} else {
				val machine = ColumnMethod(msg, key, this)
				machine.encode(false)
			}

			File(outputField.text).writeText(res)

			JOptionPane.showMessageDialog(this, "Complete", "Message", JOptionPane.INFORMATION_MESSAGE)
		}
	}

	private fun decode(inputField: JTextField, outputField: JTextField, keyField: JTextField) {
		val data = error(inputField, outputField, keyField)
		val error = data.first

		if (!error) {
			val key = data.second.first
			val msg = data.second.second

			val res = if (vigenere) {
				val machine = Vigenere(msg, key, this)
				machine.decode()
			} else {
				val machine = ColumnMethod(msg, key, this)
				machine.decode(false)
			}

			File(outputField.text).writeText(res)

			JOptionPane.showMessageDialog(this, "Complete", "Message", JOptionPane.INFORMATION_MESSAGE)
		}
	}

	private fun error(
		inputField: JTextField, outputField: JTextField, keyField: JTextField
	): Pair<Boolean, Pair<String, String>> {
		val inputPath = inputField.text
		val outputPath = outputField.text

		if (inputPath.isEmpty() || outputPath.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE)
			return true to ("" to "")
		}

		val key = keyField.text.uppercase(Locale.getDefault()).filter { it in alphabet }
		val msg = File(inputPath).readText().uppercase(Locale.getDefault()).filter { it in alphabet }

		if (key.isEmpty() || msg.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE)
			return true to ("" to "")
		}

		return false to (key to msg)
	}

	private fun selectPath(pathField: JTextField) {
		val fileChooser = JFileChooser()
		val result = fileChooser.showOpenDialog(this)
		if (result == JFileChooser.APPROVE_OPTION) {
			pathField.text = fileChooser.selectedFile.absolutePath
		}
	}
}