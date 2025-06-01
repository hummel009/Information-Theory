package com.github.hummel.it.lab2

import java.io.File
import kotlin.experimental.xor
import kotlin.math.pow


class Encoder(
	private var polynomialPowers: IntArray,
	private var initialKey: String,
	private var pathToSrcFile: String,
	private var pathToResFile: String
) {

	fun encode(): Triple<String, String, String> {
		val reg = Register(polynomialPowers, initialKey)
		val srcBytes = File(pathToSrcFile).readBytes()
		val resBytes = srcBytes.copyOf()

		val bites = 8
		val bufSrcFile = StringBuilder()
		val bufGenkey = StringBuilder()
		val bufResFile = StringBuilder()
		val currKey = StringBuilder()
		srcBytes.forEachIndexed { i, byte ->
			bufSrcFile.append(Integer.toBinaryString(byte.toInt() and 0xFF).format("%8s", "0") + " ")

			repeat(bites) { currKey.append(reg.generateKeyBit()) }

			var keyByte = 0

			"$currKey".indices.asSequence().map { j ->
				2.0.pow((bites - 1 - j)).toInt().toByte() * ("$currKey"[j].digitToInt()).toByte()
			}.forEach {
				keyByte = (keyByte + it)
			}

			bufGenkey.append("$currKey")
			resBytes[i] = byte xor keyByte.toByte()
			bufResFile.append(Integer.toBinaryString(resBytes[i].toInt() and 0xFF).format("%8s", "0") + " ")

			currKey.clear()
		}
		File(pathToResFile).writeBytes(resBytes)

		return Triple(
			"$bufSrcFile".replace(" ", ""),
			"$bufSrcFile".replace(" ", ""),
			"$bufResFile".replace(" ", "")
		)
	}
}