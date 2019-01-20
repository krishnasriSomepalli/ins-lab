class BinaryRepresentationException extends Exception {
   char c;
   BinaryRepresentationException(char c) {
      this.c = c;
   } 
   @Override
   public String toString() { 
      return ("Unexpected char found in binary string: "+c);
   }
}

public class DES {

	private static String ENCRYPT = "ENCRYPT";
	private static String DECRYPT = "DECRYPT";

	private static int BLOCK_SIZE = 64;

	private static int[] LEFT_SHIFT = {
		1, 1, 2, 2, 2, 2, 2, 2,
		1, 2, 2, 2, 2, 2, 2, 1
	};

	// 8x7
	private static int[] PC_1 = {
		57, 49, 41, 33, 25, 17, 9,
		1, 58, 50, 42, 34, 26, 18,
		10, 2, 59, 51, 43, 35, 27,
		19, 11, 3, 60, 52, 44, 36,
		63, 55, 47, 39, 31, 23, 15,
		7, 62, 54, 46, 38, 30, 22,
		14, 6, 61, 53, 45, 37, 29,
		21, 13, 5, 28, 20, 12, 4
	}; 

	// 8x6
	private static int[] PC_2 = {
		14, 17, 11, 24, 1, 5, 
		3, 28, 15, 6, 21, 10, 
		23, 19, 12, 4, 26, 8, 
		16, 7, 27, 20, 13, 2, 
		41, 52, 31, 37, 47, 55, 
		30, 40, 51, 45, 33, 48, 
		44, 49, 39, 56, 34, 53, 
		46, 42, 50, 36, 29, 32
	};

	private static int[] IP = {
		58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7,
	};

	private static int[] E_BIT = {
		32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
	};

	// 8x4x16
	private static int[][][] S = {
		{
			{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
	      	{0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
	      	{4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
	     	{15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
		},
		{
			{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
	      	{3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
	      	{0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
	     	{13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
		},
		{
			{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8}, 
			{13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1}, 
			{13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
			{1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
		},
		{
			{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
			{13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9}, 
			{10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4}, 
			{3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
		},
		{
			{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9}, 
			{14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6}, 
			{4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14}, 
			{11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
		},
		{
			{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11}, 
			{10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8}, 
			{9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6}, 
			{4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
		},
		{
			{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1}, 
			{13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6}, 
			{1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2}, 
			{6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
		},
		{
			{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7}, 
			{1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2}, 
			{7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8}, 
			{2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
		}
	};

	private static int[] P = {
		16, 7, 20, 21,
        29, 12, 28, 17,
        1, 15, 23, 26,
        5, 18, 31, 10,
        2, 8, 24, 14,
        32, 27, 3, 9,
        19, 13, 30, 6,
        22, 11, 4, 25
	};

	private static int[] IPFinal = {
		40, 8, 48, 16, 56, 24, 64, 32,
		39, 7, 47, 15, 55, 23, 63, 31, 
		38, 6, 46, 14, 54, 22, 62, 30, 
		37, 5, 45, 13, 53, 21, 61, 29, 
		36, 4, 44, 12, 52, 20, 60, 28, 
		35, 3, 43, 11, 51, 19, 59, 27, 
		34, 2, 42, 10, 50, 18, 58, 26, 
		33, 1, 41, 9, 49, 17, 57, 25
	}; 

	private static char[][] generateSubkeys(char[] key) {

		char[] temp = new char[56];
		char[][] subkeys = new char[16][48];

		// permute using PC_1 (64 bit -> 56 bit)
		for(int i=0; i<56; i++) 
			temp[i] = key[PC_1[i]-1];

		// applying left shifts and permuting again (56 bit -> 48 bit)
		for(int i=0; i<16; i++) {
			if(LEFT_SHIFT[i] == 1) {
				char swapVar = temp[0];
				for(int j=1; j<28; j++)
					temp[j-1] = temp[j];
				temp[27] = swapVar;

				swapVar = temp[28];
				for(int j=29; j<56; j++)
					temp[j-1] = temp[j];
				temp[55] = swapVar; 
			}
			else {
				char swapVar1 = temp[0];
				char swapVar2 = temp[1];
				for(int j=2; j<28; j++)
					temp[j-2] = temp[j];
				temp[26] = swapVar1;
				temp[27] = swapVar2;

				swapVar1 = temp[28];
				swapVar2 = temp[29];
				for(int j=30; j<56; j++)
					temp[j-2] = temp[j];
				temp[54] = swapVar1;
				temp[55] = swapVar2;
			}

			// permute using PC_2
			for(int j=0; j<48; j++) 
				subkeys[i][j] = temp[PC_2[j]-1];
		}
		return subkeys;
	}

	public static char XOR(char a, char b) throws BinaryRepresentationException {
		if(a=='1'||a=='0' && b=='1'||b=='0') {
			if(a==b)
				return '0';
			else
				return '1';
		}
		if(a=='1' || a=='0')
			throw new BinaryRepresentationException(b);
		else
			throw new BinaryRepresentationException(a);
	}

	private static char[] func(char[] R, char[] subkey) {
		char[] temp = new char[48];
		char[] charOutput = new char[32];
		StringBuilder output = new StringBuilder();
		for(int i=0; i<48; i++) 
			temp[i] = R[E_BIT[i]-1];

		try{
			for(int i=0; i<48; i++)
				temp[i] = XOR(temp[i], subkey[i]);
		}
		catch(BinaryRepresentationException e) {
			System.out.println(e);
		}

		for(int i=0; i<8; i++) {
			int col = Integer.parseInt(new String(temp, i*6+1, 4), 2);
			int row = Integer.parseInt(""+temp[i*6]+temp[i*6+5], 2);
			String binaryTemp = Integer.toBinaryString(S[i][row][col]);
			int length = binaryTemp.length();
			// padding with 0 to ensure length of 4 bits
			while(length<4) {
				output.append("0");
				length++;
			}
			output.append(binaryTemp);
		}

		// P permutation
		temp = output.toString().toCharArray();
		for(int i=0; i<32; i++)
			charOutput[i] = temp[P[i]-1];

		return charOutput;
	}

	private static String toBinary(String text) {
	    byte[] bytes = text.getBytes();
	    StringBuilder binary = new StringBuilder();
	    for (byte b : bytes) {
	        int val = b;
	        for (int i = 0; i < 8; i++) {
	            binary.append((val & 128) == 0 ? 0 : 1);
	            val <<= 1;
	        }
	    }
	    return binary.toString();
	}

	public static String encrypt(String plainText, String key) {
		return crypt(plainText, key, ENCRYPT);
	}

	public static String decrypt(String cipherText, String key) {
		return crypt(cipherText, key, DECRYPT);
	}

	// 'type' can be either 'ENCRYPT' or 'DECRYPT'
	private static String crypt(String inputText, String key, String type) {

		// create 64 bit blocks
		// convert every character in String inputText to bits and each bit is to be stored as a char in 'blocks[][]'
		String binary = toBinary(inputText);
		int blocksNo = (binary.length()%BLOCK_SIZE)==0 ? (binary.length()/BLOCK_SIZE):(binary.length()/BLOCK_SIZE+1);
		char[][] blocks = new char[blocksNo][64]; 
		int q = 0, r = 0;
		for(int p=0; p<binary.length(); p++) {
			if(r == 64) {
				q++;
				r = 0;
			}
			blocks[q][r++] = binary.charAt(p);
		}

		StringBuilder outputBits = new StringBuilder();
		
		char[] keyBits = new char[64];
		String keyBitsString = toBinary(key);
		for(int i=0; i<64; i++)
			keyBits[i] = keyBitsString.charAt(i);
		char[][] subkeys = generateSubkeys(keyBits);

		for(int i=0; i<blocksNo; i++) {

			char[] message = new char[64];
			char[] temp = new char[64];
			char[] L = new char[32];
			char[] R = new char[32];
			char[] _L = new char[32];
			char[] _R = new char[32];
			char[] funcOutput = new char[32];

			// permute inputText using IP
			for(int j=0; j<64; j++) {
				message[j] = blocks[i][IP[j]-1];
				if(j<32)
					_L[j] = message[j];
				else
					_R[j-32] = message[j];
			}

			for(int j=0; j<16; j++) {

				int subkeyNumber;
				if(type.equals(ENCRYPT))
					subkeyNumber = j;
				else
					subkeyNumber = 15-j;

				for(int k=0; k<32; k++) 
					L[k] = _R[k];

				funcOutput = func(_R, subkeys[subkeyNumber]);
				try {
					for(int k=0; k<32; k++)
						R[k] = XOR(_L[k], funcOutput[k]);
				}
				catch(BinaryRepresentationException e) {
					System.out.println(e);
				}

				for(int k=0; k<32; k++) {
					_L[k] = L[k];
					_R[k] = R[k];
				}
			}

			for(int j=0; j<32; j++) {
				message[j] = R[j];
				message[j+32] = L[j];
			}

			for(int j=0; j<64; j++)
				temp[j] = message[j];

			// final permutation
			for(int j=0; j<64; j++)
				message[j] = temp[IPFinal[j]-1];

			outputBits.append(new String(message));
		}

		StringBuilder outputText = new StringBuilder();
		for(int i=0; i<outputBits.length(); i=i+8)
			outputText.append((char)(Integer.parseInt(outputBits.substring(i, i+8), 2)));

		String foo = outputText.toString();
		System.out.print("Decimal: ");
		for(int i=0; i<foo.length(); i++)
			System.out.print((int)foo.charAt(i)+" ");
		System.out.println();

		return outputText.toString();
	}
}