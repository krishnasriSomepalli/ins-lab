public class Arithmetic {

	// return A(mod C)
   public static int mod(int A, int C) {
        while(A<0)
            A = A+C;
        return A%C;
   }

   // Find A^-1 such that (A*A^-1)mod C = 1
   public static int modInverse(int A, int C) {
        for(int i=0; i<C-1; i++) 
            if(mod(A*i,C) == 1)
                return i;
        return -1;
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
}