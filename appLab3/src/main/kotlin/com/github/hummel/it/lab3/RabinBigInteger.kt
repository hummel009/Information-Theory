package com.github.hummel.it.lab3

import java.io.File
import java.math.BigInteger

class RabinBigInteger(
	private var p: BigInteger,
	private var q: BigInteger,
	private var b: BigInteger,
	private var input: String,
	private var output: String
) : Rabin {
	private val n: BigInteger = p * q

	override fun encode() {
		val listPlain = File(input).readBytes().map { it.toInt() and 0xFF }

		val listCipher = ArrayList<BigInteger>()
		listPlain.asSequence().map {
			it.toBigInteger()
		}.mapTo(listCipher) {
			(it * (it + b)) % n
		}
		File(output).writeText("$listCipher")
	}

	override fun decode() {
		val line = File(input).readText()
		val listCipher = line.substring(1, line.length - 1).split(", ").map { it.toBigInteger() }
		val listDecipher = ArrayList<Int>()

		for (c in listCipher) {
			val d = (b * b + 4.toBigInteger() * c) % n

			val mP = d.modPow(((p + BigInteger.ONE) / 4.toBigInteger()), p)
			val mQ = d.modPow(((q + BigInteger.ONE) / 4.toBigInteger()), q)

			val ypq = Utils.advancedEuclidAlgorithmBI(p, q)
			val yP = ypq.first
			val yQ = ypq.second

			val dS = Array<BigInteger>(4) { BigInteger.ZERO }
			dS[0] = (yP * p * mQ + yQ * q * mP).mod(n)
			dS[1] = n - dS[0]
			dS[2] = (yP * p * mQ - yQ * q * mP).mod(n)
			dS[3] = n - dS[2]

			val border = 256.toBigInteger()
			loop@ for (value in dS) {
				val temp = value - b
				val msg = if (temp and BigInteger.ONE == BigInteger.ZERO) {
					(temp / 2.toBigInteger()) % n
				} else {
					((temp + n) / 2.toBigInteger()) % n
				}
				if (msg < border) {
					listDecipher.add(msg.toInt())
					break@loop
				}
			}
		}
		File(output).writeBytes(listDecipher.map { it.toByte() }.toByteArray())
	}
}