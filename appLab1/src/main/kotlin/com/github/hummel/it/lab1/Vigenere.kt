package com.github.hummel.it.lab1

class Vigenere(private var msg: String, private var key: String) {
	fun encode(): String {
		var newKey = key + msg
		newKey = newKey.dropLast(key.length)
		var encryptMsg = ""

		for (x in msg.indices) {
			val q = alphabet.length
			val mn = alphabet.indexOf(msg[x])
			val kn = alphabet.indexOf(newKey[x])
			encryptMsg += alphabet[(q + mn + kn) % q]
		}
		return encryptMsg
	}

	fun decode(): String {
		val currentKey = StringBuilder(key)

		for (x in msg.indices) {
			val q = alphabet.length
			val mn = alphabet.indexOf(msg[x])
			val kn = alphabet.indexOf(currentKey[x])
			currentKey.append(alphabet[(q + mn - kn) % q])
		}
		return currentKey.substring(key.length).toString()
	}
}