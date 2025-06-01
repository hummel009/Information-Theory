package com.github.hummel.it.lab3

import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubDarkIJTheme
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main() {
	FlatLightLaf.setup()
	EventQueue.invokeLater {
		try {
			UIManager.setLookAndFeel(FlatMTGitHubDarkIJTheme())
			val frame = CipherMachine()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class CipherMachine : JFrame() {
	private var fast: Boolean = true

	private val inputField: JTextField = JTextField(24)
	private val outputField: JTextField = JTextField(24)

	private val keyFieldP: JTextField = JTextField(8).apply { text = "5003" }
	private val keyFieldQ: JTextField = JTextField(8).apply { text = "5227" }
	private val keyFieldB: JTextField = JTextField(8).apply { text = "1234" }

	init {
		title = "Rabin Cipher Machine"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 600, 270)

		val contentPanel = JPanel().apply {
			border = EmptyBorder(10, 10, 10, 10)
			layout = GridLayout(0, 1, 5, 10)
		}

		val inputPanel = JPanel(BorderLayout(5, 5)).apply {
			add(JLabel("Input path:").apply {
				preferredSize = Dimension(100, preferredSize.height)
			}, BorderLayout.WEST)
			add(inputField, BorderLayout.CENTER)
			add(JButton("Browse").apply {
				preferredSize = Dimension(100, preferredSize.height)
				addActionListener {
					selectPath(inputField)
				}
			}, BorderLayout.EAST)
		}

		val outputPanel = JPanel(BorderLayout(5, 5)).apply {
			add(JLabel("Output path:").apply {
				preferredSize = Dimension(100, preferredSize.height)
			}, BorderLayout.WEST)
			add(outputField, BorderLayout.CENTER)
			add(JButton("Browse").apply {
				preferredSize = Dimension(100, preferredSize.height)
				addActionListener {
					selectPath(outputField)
				}
			}, BorderLayout.EAST)
		}

		val keyPanel = JPanel(GridLayout(1, 3, 5, 5)).apply {
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("P:"), BorderLayout.WEST)
				add(keyFieldP, BorderLayout.CENTER)
			})
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("Q:"), BorderLayout.WEST)
				add(keyFieldQ, BorderLayout.CENTER)
			})
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("B:"), BorderLayout.WEST)
				add(keyFieldB, BorderLayout.CENTER)
			})
		}

		val radioPanel = JPanel(GridLayout(1, 2, 5, 5)).apply {
			val usualButton = JRadioButton("Usual (faster)", true).apply {
				addActionListener {
					fast = true
				}
			}
			val bigIntButton = JRadioButton("Big Int (slow)").apply {
				addActionListener {
					fast = false
				}
			}
			ButtonGroup().apply {
				add(usualButton)
				add(bigIntButton)
			}
			add(usualButton)
			add(bigIntButton)
		}

		val processPanel = JPanel(GridLayout(1, 2, 5, 5)).apply {
			add(JButton("Encode").apply {
				addActionListener {
					process(true)
				}
			})
			add(JButton("Decode").apply {
				addActionListener {
					process(false)
				}
			})
		}

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(keyPanel)
		contentPanel.add(radioPanel)
		contentPanel.add(processPanel)

		contentPane = contentPanel

		setLocationRelativeTo(null)
	}

	private fun process(isEncode: Boolean) {
		if (inputField.text.isEmpty() || outputField.text.isEmpty() || keyFieldP.text.isEmpty() || keyFieldQ.text.isEmpty() || keyFieldB.text.isEmpty()) {
			JOptionPane.showMessageDialog(
				this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE
			)
			return
		}

		try {
			if (fast) {
				val keyP = keyFieldP.text.toInt()
				val keyQ = keyFieldQ.text.toInt()
				val keyB = keyFieldB.text.toInt()

				ValuesCheckerDefault.checkPrime(keyP)
				ValuesCheckerDefault.checkPrime(keyQ)
				ValuesCheckerDefault.checkB(keyB, keyP, keyQ)
				ValuesCheckerDefault.checkPAndQ(keyP, keyQ)
			} else {
				val keyP = keyFieldP.text.toBigInteger()
				val keyQ = keyFieldQ.text.toBigInteger()
				val keyB = keyFieldB.text.toBigInteger()

				ValuesCheckerBigInteger.checkPrime(keyP)
				ValuesCheckerBigInteger.checkPrime(keyQ)
				ValuesCheckerBigInteger.checkB(keyB, keyP, keyQ)
				ValuesCheckerBigInteger.checkPAndQ(keyP, keyQ)
			}
		} catch (_: Exception) {
			JOptionPane.showMessageDialog(
				this, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE
			)
			return
		}

		val inputPath = inputField.text
		val outputPath = outputField.text

		val machine = if (fast) {
			RabinDefault(
				keyFieldP.text.toInt(), keyFieldQ.text.toInt(), keyFieldB.text.toInt(), inputPath, outputPath
			)
		} else {
			RabinBigInteger(
				keyFieldP.text.toBigInteger(),
				keyFieldQ.text.toBigInteger(),
				keyFieldB.text.toBigInteger(),
				inputPath,
				outputPath
			)
		}

		if (isEncode) {
			machine.encode()
		} else {
			machine.decode()
		}

		JOptionPane.showMessageDialog(
			this, "Complete", "Success", JOptionPane.INFORMATION_MESSAGE
		)
	}

	private fun selectPath(field: JTextField) {
		JFileChooser().run {
			if (showOpenDialog(this@CipherMachine) == JFileChooser.APPROVE_OPTION) {
				field.text = selectedFile.absolutePath
			}
		}
	}
}