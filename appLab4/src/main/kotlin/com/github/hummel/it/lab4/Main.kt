package com.github.hummel.it.lab4

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
			val frame = CipherMachine()
			frame.isVisible = true
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}

class CipherMachine : JFrame() {
	private var mode: SignMode = SignMode.RUS

	private val inputField: JTextField = JTextField(24)
	private val outputField: JTextField = JTextField(24)
	private val keyFieldQ: JTextField = JTextField(8).apply { text = "107" }
	private val keyFieldP: JTextField = JTextField(8).apply { text = "643" }
	private val keyFieldH: JTextField = JTextField(8).apply { text = "2" }
	private val keyFieldK: JTextField = JTextField(8).apply { text = "31" }
	private val keyFieldX: JTextField = JTextField(8).apply { text = "45" }
	private val keyFieldY: JTextField = JTextField(8).apply { text = "0" }
	private val keyFieldM: JTextField = JTextField(8).apply { text = "323" }

	init {
		title = "DSA Signer Machine"
		defaultCloseOperation = EXIT_ON_CLOSE
		setBounds(100, 100, 600, 324)

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

		val keyPanel1 = JPanel(GridLayout(1, 4, 5, 5)).apply {
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("Q:"), BorderLayout.WEST)
				add(keyFieldQ, BorderLayout.CENTER)
			})
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("P:"), BorderLayout.WEST)
				add(keyFieldP, BorderLayout.CENTER)
			})
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("H:"), BorderLayout.WEST)
				add(keyFieldH, BorderLayout.CENTER)
			})
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("K:"), BorderLayout.WEST)
				add(keyFieldK, BorderLayout.CENTER)
			})
		}

		val keyPanel2 = JPanel(GridLayout(1, 3, 5, 5)).apply {
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("X:"), BorderLayout.WEST)
				add(keyFieldX, BorderLayout.CENTER)
			})
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("Y:"), BorderLayout.WEST)
				add(keyFieldY, BorderLayout.CENTER)
			})
			add(JPanel(BorderLayout(5, 5)).apply {
				add(JLabel("M:"), BorderLayout.WEST)
				add(keyFieldM, BorderLayout.CENTER)
			})
		}

		val radioPanel = JPanel(GridLayout(1, 2, 5, 5)).apply {
			val group = ButtonGroup()
			val modes = SignMode.entries.toTypedArray()

			modes.forEach { signMode ->
				val button = JRadioButton(signMode.name, signMode == mode).apply {
					addActionListener {
						mode = signMode
					}
					group.add(this)
				}
				add(button)
			}
		}

		val processPanel = JPanel(GridLayout(1, 2, 5, 5)).apply {
			add(JButton("Ensign").apply {
				addActionListener {
					ensign()
				}
			})
			add(JButton("Design").apply {
				addActionListener {
					design()
				}
			})
		}

		contentPanel.add(inputPanel)
		contentPanel.add(outputPanel)
		contentPanel.add(keyPanel1)
		contentPanel.add(keyPanel2)
		contentPanel.add(radioPanel)
		contentPanel.add(processPanel)

		contentPane = contentPanel

		setLocationRelativeTo(null)
	}

	private fun ensign() {
		val error =
			error(inputField, outputField, keyFieldQ, keyFieldP, keyFieldH, keyFieldX, keyFieldY, keyFieldK, keyFieldM)

		if (keyFieldX.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Select X", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		if (!error) {
			val inputPath = inputField.text
			val outputPath = outputField.text
			val q = keyFieldQ.text.toBigInteger()
			val p = keyFieldP.text.toBigInteger()
			val h = keyFieldH.text.toBigInteger()
			val x = keyFieldX.text.toBigInteger()
			val k = keyFieldK.text.toBigInteger()
			val m = keyFieldM.text.toBigInteger()

			val signer = Signer(inputPath, outputPath, q, p, h, k, m)
			val cortege = try {
				signer.ensign(mode, x)
			} catch (_: Exception) {
				null
			}

			cortege?.let {
				val hash = it.value1
				val r = it.value2
				val s = it.value3
				val y = it.value4
				JOptionPane.showMessageDialog(
					this, "Hash = $hash, r = $r, s = $s, y = $y", "Message", JOptionPane.INFORMATION_MESSAGE
				)
				keyFieldX.text = "0"
				keyFieldY.text = "$y"
			} ?: run {
				JOptionPane.showMessageDialog(
					this, "Broken file", "Error", JOptionPane.ERROR_MESSAGE
				)
			}
		}
	}

	private fun design() {
		val error =
			error(inputField, outputField, keyFieldQ, keyFieldP, keyFieldH, keyFieldX, keyFieldY, keyFieldK, keyFieldM)

		if (keyFieldY.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Select Y", "Error", JOptionPane.ERROR_MESSAGE)
			return
		}

		if (!error) {
			val inputPath = inputField.text
			val outputPath = outputField.text
			val q = keyFieldQ.text.toBigInteger()
			val p = keyFieldP.text.toBigInteger()
			val h = keyFieldH.text.toBigInteger()
			val y = keyFieldY.text.toBigInteger()
			val k = keyFieldK.text.toBigInteger()
			val m = keyFieldM.text.toBigInteger()

			val signer = Signer(inputPath, outputPath, q, p, h, k, m)
			val cortege = try {
				signer.design(mode, y)
			} catch (_: Exception) {
				null
			}

			cortege?.let {
				val hash = it.value1
				val r = it.value2
				val s = it.value3
				val w = it.value4
				val u1 = it.value5
				val u2 = it.value6
				val v = it.value7
				JOptionPane.showMessageDialog(
					this,
					"Hash = $hash, R = $r, S = $s, W = $w, U1 = $u1, U2 = $u2, V = $v\r\n${r == v}",
					"Message",
					JOptionPane.INFORMATION_MESSAGE
				)
				keyFieldX.text = "45"
				keyFieldY.text = "0"
			} ?: run {
				JOptionPane.showMessageDialog(
					this, "Broken file", "Error", JOptionPane.ERROR_MESSAGE
				)
			}
		}
	}

	private fun error(
		inputField: JTextField,
		outputField: JTextField,
		keyFieldQ: JTextField,
		keyFieldP: JTextField,
		keyFieldH: JTextField,
		keyFieldX: JTextField,
		keyFieldY: JTextField,
		keyFieldK: JTextField,
		keyFieldM: JTextField
	): Boolean {
		if (inputField.text.isEmpty() || outputField.text.isEmpty() || keyFieldQ.text.isEmpty() || keyFieldP.text.isEmpty() || keyFieldH.text.isEmpty() || keyFieldK.text.isEmpty() || keyFieldM.text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Empty fields", "Error", JOptionPane.ERROR_MESSAGE)
			return true
		}
		try {
			val q = keyFieldQ.text.toBigInteger()
			val p = keyFieldP.text.toBigInteger()
			val h = keyFieldH.text.toBigInteger()
			val x = keyFieldX.text.toBigInteger()
			val k = keyFieldK.text.toBigInteger()
			keyFieldM.text.toBigInteger()
			keyFieldY.text.toBigInteger()
			ValuesChecker.checkQ(q)
			ValuesChecker.checkP(p, q)
			ValuesChecker.checkH(q, p, h)
			ValuesChecker.checkInterval(BigInteger.ZERO, q, x)
			ValuesChecker.checkInterval(BigInteger.ONE, q - BigInteger.ONE, k)
		} catch (_: Exception) {
			JOptionPane.showMessageDialog(this, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE)
			return true
		}
		return false
	}

	private fun selectPath(field: JTextField) {
		JFileChooser().run {
			if (showOpenDialog(this@CipherMachine) == JFileChooser.APPROVE_OPTION) {
				field.text = selectedFile.absolutePath
			}
		}
	}
}

enum class SignMode {
	RUS, ENG, ASC, BIN
}

data class CortegeFour(
	val value1: Any, val value2: Any, val value3: Any, val value4: Any
)

data class CortegeSeven(
	val value1: Any,
	val value2: Any,
	val value3: Any,
	val value4: Any,
	val value5: Any,
	val value6: Any,
	val value7: Any
)