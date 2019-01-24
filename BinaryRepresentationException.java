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