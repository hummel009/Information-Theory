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

const val alphabet: String = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

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
	private var vigenereSelected: Boolean = true

	private val inputField: JTextField = JTextField(20)
	private val outputField: JTextField = JTextField(20)
	private val keyField: JTextField = JTextField(20)

	init {
		title = "Vigenere & Column Cipher Machine"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 600, 270)

		val contentPanel = JPanel().apply {
			border = EmptyBorder(10, 10, 10, 10)
			layout = GridLayout(0, 1, 5, 10)
		}

		val radioPanel = JPanel().apply {
			val vigenereButton = JRadioButton("Vigenere", true).apply {
				addActionListener {
					vigenereSelected = true
				}
			}
			val columnButton = JRadioButton("Column Method").apply {
				addActionListener {
					vigenereSelected = false
				}
			}
			ButtonGroup().apply {
				add(vigenereButton)
				add(columnButton)
			}
			add(vigenereButton)
			add(columnButton)
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

		val keyPanel = JPanel(BorderLayout(5, 5)).apply {
			add(JLabel("Key:").apply {
				preferredSize = Dimension(100, preferredSize.height)
			}, BorderLayout.WEST)
			add(keyField, BorderLayout.CENTER)
		}

		val processPanel = JPanel(GridLayout(1, 2, 5, 5)).apply {
			add(JButton("Encode").apply {
				addActionListener {
					process(false)
				}
			})
			add(JButton("Decode").apply {
				addActionListener {
					process(true)
				}
			})
		}

		contentPanel.add(radioPanel)
		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(keyPanel)
		contentPanel.add(processPanel)

		contentPane = contentPanel

		setLocationRelativeTo(null)
	}

	private fun process(reverse: Boolean) {
		if (inputField.text.isEmpty() || outputField.text.isEmpty()) {
			JOptionPane.showMessageDialog(
				this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE
			)
			return
		}

		val key = keyField.text.uppercase(Locale.getDefault()).filter { it in alphabet }
		val message = File(inputField.text).readText().uppercase(Locale.getDefault()).filter { it in alphabet }

		if (key.isEmpty() || message.isEmpty()) {
			JOptionPane.showMessageDialog(
				this, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE
			)
			return
		}

		val result = if (vigenereSelected) {
			Vigenere(message, key).run {
				if (reverse) decode() else encode()
			}
		} else {
			ColumnMethod(message, key).run {
				if (reverse) decode() else encode()
			}
		}

		File(outputField.text).writeText(result)

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