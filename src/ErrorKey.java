/**
 * Created by sam on 10/30/14.
 */
public class ErrorKey implements Comparable<ErrorKey> {
    private char key;
    private int frequency;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public char getKey() {
        return key;
    }

    public ErrorKey(char key) {
        this.key = key;
        this.frequency = 1;
    }
    //so we can use a Collections.sort() on it.
    public int compareTo(ErrorKey o) {
        if (this.frequency < o.getFrequency()) {
            return 1;
        } else if (this.frequency == o.getFrequency()) {
            if (this.getKey() > o.getKey()) {
                return 1;
            }
            if (this.getKey() == o.getKey()) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
