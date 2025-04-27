package com.github.hummel.it.lab3

import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubDarkIJTheme
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import java.math.BigInteger
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
	private var fast = true

	private fun selectPath(pathField: JTextField) {
		val fileChooser = JFileChooser()
		val result = fileChooser.showOpenDialog(this)
		if (result == JFileChooser.APPROVE_OPTION) {
			pathField.text = fileChooser.selectedFile.absolutePath
		}
	}

	private fun error(
		inputField: JTextField,
		outputField: JTextField,
		keyFieldP: JTextField,
		keyFieldQ: JTextField,
		keyFieldB: JTextField
	): Boolean {
		if (inputField.text.isEmpty() || outputField.text.isEmpty() || keyFieldP.text.isEmpty() || keyFieldQ.text.isEmpty() || keyFieldB.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE)
			return true
		}

		try {
			if (fast) {
				val keyP = keyFieldP.text.toInt()
				val keyQ = keyFieldQ.text.toInt()
				val keyB = keyFieldB.text.toInt()

				if (!Utils.isPrime(keyP.toLong()) || !Utils.isPrime(keyQ.toLong())) {
					throw Exception()
				}
				if (!(keyB < keyP * keyQ && keyB > 0 && keyB < 10533)) {
					throw Exception()
				}
				if (!(keyP > 3 && keyQ > 3511 && keyP * keyQ > 256)) {
					throw Exception()
				}
				if (!(keyP % 4 == 3 && keyQ % 4 == 3)) {
					throw Exception()
				}
			} else {
				val keyP = keyFieldP.text.toBigInteger()
				val keyQ = keyFieldQ.text.toBigInteger()
				val keyB = keyFieldB.text.toBigInteger()

				if (!(keyP.isProbablePrime(95) && keyQ.isProbablePrime(95))) {
					throw Exception()
				}
				if (!(keyB < keyP * keyQ && keyB > BigInteger.ZERO && keyB < 10533.toBigInteger())) {
					throw Exception()
				}
				if (!(keyP > 3.toBigInteger() && keyQ > 3511.toBigInteger() && keyP * keyQ > 256.toBigInteger())) {
					throw Exception()
				}
				if (!(keyP % 4.toBigInteger() == 3.toBigInteger() && keyQ % 4.toBigInteger() == 3.toBigInteger())) {
					throw Exception()
				}
			}
		} catch (_: Exception) {
			JOptionPane.showMessageDialog(this, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE)
			return true
		}
		return false
	}

	private fun decode(
		inputField: JTextField,
		outputField: JTextField,
		keyFieldP: JTextField,
		keyFieldQ: JTextField,
		keyFieldB: JTextField
	) {
		val error = error(inputField, outputField, keyFieldP, keyFieldQ, keyFieldB)

		if (!error) {
			val inputPath = inputField.text
			val outputPath = outputField.text

			val machine = if (fast) {
				val keyP = keyFieldP.text.toInt()
				val keyQ = keyFieldQ.text.toInt()
				val keyB = keyFieldB.text.toInt()

				DefaultRabin(keyP, keyQ, keyB, inputPath, outputPath)
			} else {
				val keyP = keyFieldP.text.toBigInteger()
				val keyQ = keyFieldQ.text.toBigInteger()
				val keyB = keyFieldB.text.toBigInteger()

				BigIntegerRabin(keyP, keyQ, keyB, inputPath, outputPath)
			}

			machine.decode()

			JOptionPane.showMessageDialog(this, "Complete", "Message", JOptionPane.INFORMATION_MESSAGE)
		}
	}

	private fun encode(
		inputField: JTextField,
		outputField: JTextField,
		keyFieldP: JTextField,
		keyFieldQ: JTextField,
		keyFieldB: JTextField
	) {
		val error = error(inputField, outputField, keyFieldP, keyFieldQ, keyFieldB)

		if (!error) {
			val inputPath = inputField.text
			val outputPath = outputField.text

			val machine = if (fast) {
				val keyP = keyFieldP.text.toInt()
				val keyQ = keyFieldQ.text.toInt()
				val keyB = keyFieldB.text.toInt()

				DefaultRabin(keyP, keyQ, keyB, inputPath, outputPath)
			} else {
				val keyP = keyFieldP.text.toBigInteger()
				val keyQ = keyFieldQ.text.toBigInteger()
				val keyB = keyFieldB.text.toBigInteger()

				BigIntegerRabin(keyP, keyQ, keyB, inputPath, outputPath)
			}

			machine.encode()

			JOptionPane.showMessageDialog(this, "Complete", "Message", JOptionPane.INFORMATION_MESSAGE)
		}
	}

	init {
		title = "Rabin Cipher Machine"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 500, 210)

		val contentPanel = JPanel()
		contentPanel.border = EmptyBorder(5, 5, 5, 5)
		contentPanel.layout = BorderLayout(0, 0)
		contentPanel.layout = GridLayout(0, 1, 0, 0)
		contentPane = contentPanel

		val inputPanel = JPanel()
		val inputLabel = JLabel("Input path:")
		inputLabel.preferredSize = Dimension(80, inputLabel.preferredSize.height)
		val inputField = JTextField(24)
		val inputButton = JButton("Select path")
		inputButton.addActionListener { selectPath(inputField) }
		inputPanel.add(inputLabel)
		inputPanel.add(inputField)
		inputPanel.add(inputButton)

		val outputPanel = JPanel()
		val outputLabel = JLabel("Output path:")
		outputLabel.preferredSize = Dimension(80, outputLabel.preferredSize.height)
		val outputField = JTextField(24)
		val outputButton = JButton("Select path")
		outputButton.addActionListener { selectPath(outputField) }
		outputPanel.add(outputLabel)
		outputPanel.add(outputField)
		outputPanel.add(outputButton)

		val keyPanel = JPanel()
		val keyLabelP = JLabel("P:")
		val keyFieldP = JTextField(8)
		keyFieldP.text = "5003"
		val keyLabelQ = JLabel("Q:")
		val keyFieldQ = JTextField(8)
		keyFieldQ.text = "5227"
		val keyLabelB = JLabel("B:")
		val keyFieldB = JTextField(8)
		keyFieldB.text = "1234"
		keyPanel.add(keyLabelP)
		keyPanel.add(keyFieldP)
		keyPanel.add(keyLabelQ)
		keyPanel.add(keyFieldQ)
		keyPanel.add(keyLabelB)
		keyPanel.add(keyFieldB)

		val radioPanel = JPanel()
		val radioButtonUW = JRadioButton("Usual (faster)")
		val radioButtonBI = JRadioButton("Big Int (slow)")
		radioButtonUW.isSelected = true
		radioButtonUW.addActionListener {
			fast = true
			radioButtonBI.isSelected = false
		}
		radioButtonBI.addActionListener {
			fast = false
			radioButtonUW.isSelected = false
		}
		radioPanel.add(radioButtonUW)
		radioPanel.add(radioButtonBI)

		val processPanel = JPanel()
		val processEncode = JButton("Encode")
		val processDecode = JButton("Decode")
		processEncode.addActionListener { encode(inputField, outputField, keyFieldP, keyFieldQ, keyFieldB) }
		processDecode.addActionListener { decode(inputField, outputField, keyFieldP, keyFieldQ, keyFieldB) }
		processPanel.add(processEncode)
		processPanel.add(processDecode)

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(keyPanel)
		contentPanel.add(radioPanel)
		contentPanel.add(processPanel)

		setLocationRelativeTo(null)
	}
}