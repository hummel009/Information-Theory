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
			val frame = GUI()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class GUI : JFrame() {
	var srcFileBin: String = ""
	var resFileBin: String = ""
	var keyStream: String = ""

	private fun selectPath(pathField: JTextField) {
		val fileChooser = JFileChooser()
		val result = fileChooser.showOpenDialog(this)
		if (result == JFileChooser.APPROVE_OPTION) {
			pathField.text = fileChooser.selectedFile.absolutePath
		}
	}

	private fun process(inputField: JTextField, outputField: JTextField, keyField: JTextField) {
		val outputPath = outputField.text
		val inputPath = inputField.text
		val key = keyField.text.uppercase(Locale.getDefault()).filter { it in "01" }

		if (inputPath.isEmpty() || outputPath.isEmpty() || key.length != 34) {
			JOptionPane.showMessageDialog(this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		val encoder = Encoder(intArrayOf(34, 15, 14, 1), key, inputPath, outputPath, this)
		encoder.encode()
		JOptionPane.showMessageDialog(this, "Complete", "Message", JOptionPane.INFORMATION_MESSAGE)
	}

	init {
		title = "Polynomial Cipher Machine"
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
		val keyLabel = JLabel("Start value:")
		val keyField = JTextField(24)
		keyPanel.add(keyLabel)
		keyPanel.add(keyField)

		val processPanel = JPanel()
		val processButton = JButton("Recode file")
		processButton.addActionListener { process(inputField, outputField, keyField) }
		processPanel.add(processButton)

		val dataPanel = JPanel()
		val dataSource = JButton("Source")
		val dataStream = JButton("Stream")
		val dataResult = JButton("Result")
		dataSource.addActionListener { ScrollWindow("Source", srcFileBin) }
		dataStream.addActionListener { ScrollWindow("Stream", keyStream) }
		dataResult.addActionListener { ScrollWindow("Result", resFileBin) }
		dataPanel.add(dataSource)
		dataPanel.add(dataStream)
		dataPanel.add(dataResult)

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(keyPanel)
		contentPanel.add(processPanel)
		contentPanel.add(dataPanel)

		setLocationRelativeTo(null)
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