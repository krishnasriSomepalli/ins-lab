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

   // (a^b)%n = (((a^(b-1))%n)*a)%n
   public static int modExp(int a, int b, int n) {
    if(b == 1)
      return a%n;
    else
      return (modExp(a, b-1, n)*a)%n;
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

  public static int GCD(int a, int b) {
    if(a < b)
      return GCD(b, a);
    else if(b == 0)
      return a;
    else {
      return GCD(b, a%b);
    }
  }

  public static Boolean isPrime(int n) {
    if(n <= 1)
      return false;
    else if(n <= 3)
      return true;
    else {
      // Every prime number, other than 2 and 3, is of the form 6k+1 or 6k-1.
      for(int i=6; i*i<=n; i=i+6) 
        if(n%(i-1)==0 || n%(i+1)==0)
          return false;
      return true;
    }
  }
}