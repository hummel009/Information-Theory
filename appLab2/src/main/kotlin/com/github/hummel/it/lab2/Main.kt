package com.github.hummel.it.lab2

import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubDarkIJTheme
import java.awt.*
import java.util.*
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
	private var srcFileBin: String = ""
	private var resFileBin: String = ""
	private var keyStream: String = ""

	private val inputField: JTextField = JTextField(24)
	private val outputField: JTextField = JTextField(24)
	private val keyField: JTextField = JTextField(24)

	init {
		title = "Polynomial Cipher Machine"
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

		val keyPanel = JPanel(BorderLayout(5, 5)).apply {
			add(JLabel("Start value:").apply {
				preferredSize = Dimension(100, preferredSize.height)
			}, BorderLayout.WEST)
			add(keyField, BorderLayout.CENTER)
		}

		val processPanel = JPanel(GridLayout(1, 1, 5, 5)).apply {
			add(JButton("Recode file").apply {
				addActionListener { process() }
			})
		}

		val dataPanel = JPanel(GridLayout(1, 3, 5, 5)).apply {
			add(JButton("Source").apply {
				addActionListener { ScrollWindow("Source", srcFileBin) }
			})
			add(JButton("Stream").apply {
				addActionListener { ScrollWindow("Stream", keyStream) }
			})
			add(JButton("Result").apply {
				addActionListener { ScrollWindow("Result", resFileBin) }
			})
		}

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(keyPanel)
		contentPanel.add(processPanel)
		contentPanel.add(dataPanel)

		contentPane = contentPanel
		setLocationRelativeTo(null)
	}

	private fun process() {
		val outputPath = outputField.text
		val inputPath = inputField.text
		val key = keyField.text.uppercase(Locale.getDefault()).filter { it in "01" }

		if (inputPath.isEmpty() || outputPath.isEmpty() || key.length != 34) {
			JOptionPane.showMessageDialog(
				this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE
			)
			return
		}

		val encoder = Encoder(intArrayOf(34, 15, 14, 1), key, inputPath, outputPath)
		val (src, stream, res) = encoder.encode()

		srcFileBin = src
		keyStream = stream
		resFileBin = res

		JOptionPane.showMessageDialog(
			this, "Complete", "Message", JOptionPane.INFORMATION_MESSAGE
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

class ScrollWindow(name: String, data: String) : JFrame() {
	init {
		title = name
		layout = BorderLayout()
		setSize(300, 300)

		val textArea = JTextArea(data)
		textArea.font = Font("Arial", Font.PLAIN, 16)
		textArea.lineWrap = true
		textArea.wrapStyleWord = true
		textArea.caretPosition = 0

		val scrollPane = JScrollPane(textArea)
		scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS

		add(scrollPane, BorderLayout.CENTER)
		setLocationRelativeTo(null)
		isVisible = true
	}
}