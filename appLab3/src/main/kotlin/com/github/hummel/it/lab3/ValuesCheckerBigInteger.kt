package com.github.hummel.it.lab3

import java.math.BigInteger

object ValuesCheckerBigInteger {
	fun checkPrime(n: BigInteger) {
		if (!n.isProbablePrime(95)) {
			throw Exception()
		}
	}

	fun checkB(b: BigInteger, p: BigInteger, q: BigInteger) {
		val limit = 10533.toBigInteger()
		if (!(b > BigInteger.ZERO && b < p * q && b < limit)) {
			throw Exception()
		}
	}

	fun checkPAndQ(p: BigInteger, q: BigInteger) {
		val three = 3.toBigInteger()
		val four = 4.toBigInteger()
		val qLimit = 3511.toBigInteger()
		val prodLimit = 256.toBigInteger()

		if (!(p > three && q > qLimit && p * q > prodLimit)) {
			throw Exception()
		}
		if (!(p.mod(four) == three && q.mod(four) == three)) {
			throw Exception()
		}
	}
}