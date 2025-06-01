package com.github.hummel.it.lab3

object ValuesCheckerDefault {
	fun checkPrime(n: Int) {
		if (!Utils.isPrime(n.toLong())) {
			throw Exception()
		}
	}

	fun checkB(b: Int, p: Int, q: Int) {
		if (!(b > 0 && b < p * q && b < 10533)) {
			throw Exception()
		}
	}

	fun checkPAndQ(p: Int, q: Int) {
		if (!(p > 3 && q > 3511 && p * q > 256)) {
			throw Exception()
		}
		if (!(p % 4 == 3 && q % 4 == 3)) {
			throw Exception()
		}
	}
}