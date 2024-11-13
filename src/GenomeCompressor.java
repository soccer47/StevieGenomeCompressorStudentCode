/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    // Power that 2 will to be raised to in order to be greater than length of sequence
    private static final int BITS_PER_LENGTH = 13;
    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // Read in the string
        String sequence = BinaryStdIn.readString();
        // Write the length of the sequence with the first BITS_PER_LENGTH bits in the binary file
        int strLength = sequence.length();
        BinaryStdOut.write(strLength, BITS_PER_LENGTH);

        // Write out every character into binary
        for (int i = 0; i < strLength; i++) {
            // A = 00, C = 01, G = 10, t = 11
            if (sequence.charAt(i) == 'A') {
                BinaryStdOut.write(false);
                BinaryStdOut.write(false);
            }
            else if (sequence.charAt(i) == 'C') {
                BinaryStdOut.write(false);
                BinaryStdOut.write(true);
            }
            else if (sequence.charAt(i) == 'G') {
                BinaryStdOut.write(true);
                BinaryStdOut.write(false);
            }
            else {
                BinaryStdOut.write(true);
                BinaryStdOut.write(true);
            }
        }
        // Close the file
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        // Find how many characters are in the sequence
        int sequenceLength = BinaryStdIn.readInt(BITS_PER_LENGTH);
        // Read out all the characters from the 2 bit form back to letters
        for (int i = 0; i < sequenceLength; i++) {
            // Set c dependent on the next two bits
            int c = 0;
            if (BinaryStdIn.readBoolean()) {
                c += 3;
            }
            if (BinaryStdIn.readBoolean()) {
                c += 1;
            }

            // Write out the letter dependent on the value of c
            if (c == 0) {
                BinaryStdOut.write('A');
            }
            else if (c == 1) {
                BinaryStdOut.write('C');
            }
            else if (c == 3) {
                BinaryStdOut.write('G');
            }
            else {
                BinaryStdOut.write('T');
            }
        }
        // Close the file
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}