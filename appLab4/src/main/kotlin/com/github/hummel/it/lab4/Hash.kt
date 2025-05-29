package com.github.hummel.it.lab4

import java.math.BigInteger

object Hash {
	private const val ALPHABET_ENG: String = " ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	private const val ALPHABET_RUS: String = " АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

	fun getHashCharsEng(input: String, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		input.asSequence().map {
			ALPHABET_ENG.indexOf(it).toBigInteger()
		}.forEach {
			h = ((h + it).pow(2)) % mod
		}

		return h
	}

	fun getHashCharsRus(input: String, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		input.asSequence().map {
			ALPHABET_RUS.indexOf(it).toBigInteger()
		}.forEach {
			h = ((h + it).pow(2)) % mod
		}

		return h
	}

	fun getHashCharsAsc(input: String, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		input.asSequence().map {
			it.code.toBigInteger()
		}.forEach {
			h = ((h + it).pow(2)) % mod
		}

		return h
	}

	fun getHashBinary(input: ByteArray, mod: BigInteger): BigInteger {
		var h = 100.toBigInteger()

		input.asSequence().map {
			it.toInt().toBigInteger()
		}.forEach {
			h = ((h + it).pow(2)) % mod
		}

		return h
	}
}