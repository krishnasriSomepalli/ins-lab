class RSA {
	// returns d, n
	public static int[] generateKeys(int p, int q, int e) {
		int n = p*q;
		int phi = (p-1)*(q-1);
		int[] result = new int[2];

		// check if p and q are primes
		if(!Arithmetic.isPrime(p) || !Arithmetic.isPrime(q))
			return null;
		// check if e and phi are coprimes
		if(Arithmetic.GCD(e, phi) != 1)
			return null;

		// determine the value of d
		int k = 1;
		while((1+(k*phi))%e != 0)
			k++;
		int d = (1+(k*phi))/e;

		result[0] = d;
		result[1] = n;
		return result;
	}


	public static int[] encrypt(String plaintext, int exp, int n) {
		int[] cipher = new int[plaintext.length()];
		for(int i=0; i<plaintext.length(); i++) 
			cipher[i] = Arithmetic.modExp(plaintext.charAt(i), exp, n);
		return cipher;
	}

	public static String decrypt(int[] cipher, int exp, int n) {
		StringBuilder plaintext = new StringBuilder();
		for(int i=0; i<cipher.length; i++)
			plaintext.append((char)Arithmetic.modExp(cipher[i], exp, n));
		return plaintext.toString();
	}
}