package com.github.hummel.it.lab3

import java.io.File

class RabinDefault(
	private var p: Int, private var q: Int, private var b: Int, private var input: String, private var output: String
) : Rabin {
	private val n: Int = p * q

	override fun encode() {
		val listPlain = File(input).readBytes().map { it.toInt() and 0xFF }

		val listCipher = ArrayList<Int>()
		listPlain.mapTo(listCipher) {
			(it * (it + b)) % n
		}
		File(output).writeText("$listCipher")
	}

	override fun decode() {
		val line = File(input).readText()
		val listCipher = line.substring(1, line.length - 1).split(", ").map { it.toInt() }
		val listDecipher = ArrayList<Short>()

		for (c in listCipher) {
			val d = (b * b + 4 * c) % n

			val mP = Utils.powMod(d.toLong(), (p + 1).toLong() / 4, p.toLong())
			val mQ = Utils.powMod(d.toLong(), (q + 1).toLong() / 4, q.toLong())

			val ypq = Utils.advancedEuclidAlgorithm(p.toLong(), q.toLong())
			val yP = ypq.first
			val yQ = ypq.second

			val dS = IntArray(4)
			val yPBig = yP.toBigInteger()
			val pBig = p.toBigInteger()
			val mQBig = mQ.toBigInteger()
			val yQBig = yQ.toBigInteger()
			val qBig = q.toBigInteger()
			val mPBig = mP.toBigInteger()
			val nBig = n.toBigInteger()
			dS[0] = (yPBig * pBig * mQBig + yQBig * qBig * mPBig).mod(nBig).toInt()
			dS[1] = n - dS[0]
			dS[2] = (yPBig * pBig * mQBig - yQBig * qBig * mPBig).mod(nBig).toInt()
			dS[3] = n - dS[2]

			loop@ for (value in dS) {
				val temp = value - b
				val msg = if (temp and 1 == 0) {
					(temp / 2) % n
				} else {
					((temp + n) / 2) % n
				}
				if (msg < 256) {
					listDecipher.add(msg.toShort())
					break@loop
				}
			}
		}
		File(output).writeBytes(listDecipher.map { it.toByte() }.toByteArray())
	}
}