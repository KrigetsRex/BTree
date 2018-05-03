import java.io.*;

/**
 * Wrapper for a long that serializes more efficiently
 */
public class Sequence implements Comparable<Sequence>, Serializable {
    public long sequence;

    public Sequence(long sequence) {
        this.sequence = sequence;
    }

    @Override
    public int compareTo(Sequence sequence) {
        return (int) (this.sequence - sequence.sequence);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeLong(sequence);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        sequence = in.readLong();
    }

    private void readObjectNoData() throws ObjectStreamException {
        sequence = 0;
    }

    @Override
    public String toString() {
        return Long.toString(sequence);
    }
}
