package com.github.hummel.it.lab3

import java.math.BigInteger

object Utils {
	fun advancedEuclidAlgorithmBI(a: BigInteger, b: BigInteger): Pair<BigInteger, BigInteger> {
		var d0 = a
		var d1 = b
		var d2: BigInteger
		var x0 = BigInteger.ONE
		var x2: BigInteger
		var y0 = BigInteger.ZERO
		var y2: BigInteger
		var q: BigInteger
		var y1 = BigInteger.ONE
		var x1 = BigInteger.ZERO
		while (d1 > BigInteger.ONE) {
			q = d0 / d1
			d2 = d0.mod(d1)
			x2 = x0 - q * x1
			y2 = y0 - q * y1
			d0 = d1
			d1 = d2
			x0 = x1
			x1 = x2
			y0 = y1
			y1 = y2
		}
		return x1 to y1
	}

	fun advancedEuclidAlgorithm(a: Long, b: Long): Pair<Long, Long> {
		var d0 = a
		var d1 = b
		var d2: Long
		var x0 = 1L
		var x2: Long
		var y0 = 0L
		var y2: Long
		var q: Long
		var yQ = 1L
		var yP = 0L
		while (d1 > 1) {
			q = d0 / d1
			d2 = d0 % d1
			x2 = x0 - q * yP
			y2 = y0 - q * yQ
			d0 = d1
			d1 = d2
			x0 = yP
			yP = x2
			y0 = yQ
			yQ = y2
		}
		return yP to yQ
	}

	fun powMod(a: Long, b: Long, m: Long): Long {
		var a1 = a
		var exp = b
		var res = 1L
		while (exp != 0L) {
			while (exp % 2 == 0L) {
				exp /= 2
				a1 = (a1 * a1) % m
			}
			exp--
			res = (res * a1) % m
		}
		return res
	}

	fun isPrime(n: Long): Boolean {
		if (n <= 1 || n % 2 == 0L) {
			return false
		}
		var d = n - 1
		var s = 0
		while (d % 2 == 0L) {
			d /= 2
			s++
		}
		repeat(5) {
			val a = (2..(n - 2)).random()
			var x = powMod(a, d, n)
			if (!(x == 1L || x == n - 1)) {
				var j = 1
				while (j < s) {
					x = powMod(x, 2, n)
					if (x == n - 1) {
						break
					}
					j++
				}
				if (x != n - 1) {
					return@isPrime false
				}
			}
		}
		return true
	}
}