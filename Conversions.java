class Conversions {

	public static String asciiToBinary(String text) {
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

	public static String hexToAscii(String hex) {
		StringBuilder ascii = new StringBuilder();
	    for (int i = 0; i <= hex.length()-2; i=i+2) {
	        int val = Integer.parseInt(hex.substring(i,i+2), 16);
	        ascii.append((char)val);
	    }
	    
	    return ascii.toString();
	}

	public static String hexToBinary(String hex) {
	    StringBuilder binary = new StringBuilder();
	    for (int i = 0; i <= hex.length()-2; i=i+2) {
	        int val = Integer.parseInt(hex.substring(i,i+2), 16);
	        for (int j = 0; j < 8; j++) {
	            binary.append((val & 128) == 0 ? 0 : 1);
	            val <<= 1;
	        }
	    }
	    
	    return binary.toString();
	}
}