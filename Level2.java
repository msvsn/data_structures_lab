import java.util.Arrays;

class HashTableLinear {
    private Trapezoid[] table;
    private int size;
    private int count;

    public HashTableLinear(int size) {
        this.size = size;
        this.count = 0;
        table = new Trapezoid[size];
    }

    private int hash(double key) {
        double A = 0.618;
        return (int) (size * ((key * A) % 1));
    }

    public boolean contains(Trapezoid trapezoid) {
        int index = hash(trapezoid.area());
        int originalIndex = index;
        
        do {
            if (table[index] != null && table[index].equals(trapezoid)) {
                return true;
            }
            index = (index + 1) % size;
        } while (table[index] != null && index != originalIndex);
        
        return false;
    }

    public void insert(Trapezoid trapezoid) {
        if (!trapezoid.isValid()) {
            System.out.println("Неможлива трапеція");
            return;
        }

        if (contains(trapezoid)) {
            System.out.println("Така трапеція вже існує");
            return;
        }

        if (count >= size) {
            System.out.println("Хеш-таблиця переповнена");
            return;
        }
        int originalIndex = hash(trapezoid.area());
        int index = originalIndex;
        while (table[index] != null) {
            System.out.println("Колізія на індексі " + index + ". Елемент буде на наступному індексі.");
            index = (index + 1) % size;
        }

        if (index != originalIndex) {
            System.out.println("Елемент вставлено за індексом " + index);
        }

        table[index] = trapezoid;
        count++;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                System.out.println("Індекс " + i + ": " + table[i]);
            }
        }
    }
}


public class Level2 {
    public static void main(String[] args) {
        HashTableLinear hashTable = new HashTableLinear(10);
        
        for (int i = 0; i < 5; i++) {
            Trapezoid trapezoid = Trapezoid.generateRandom();
            System.out.println("Спроба додати трапецію: " + trapezoid);
            hashTable.insert(trapezoid);
        }

        System.out.println("\nВміст хеш-таблиці:");
        hashTable.print();
    }
}