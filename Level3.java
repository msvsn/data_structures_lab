import java.util.Arrays;

class HashTableWithDelete {
    private Trapezoid[] table;
    private int size;
    private int count;

    public HashTableWithDelete(int size) {
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

    public void deleteByPerimeter(double min, double max) {
        for (int i = 0; i < size; i++) {
            if (table[i] != null && table[i].perimeter() >= min && table[i].perimeter() <= max) {
                table[i] = null;
                count--;
            }
        }
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                System.out.println("Індекс " + i + ": " + table[i]);
            }
        }
    }
}

public class Level3 {
    public static void main(String[] args) {
        HashTableWithDelete hashTable = new HashTableWithDelete(10);
        
        for (int i = 0; i < 5; i++) {
            Trapezoid trapezoid = Trapezoid.generateRandom();
            System.out.println("Спроба додати трапецію: " + trapezoid);
            hashTable.insert(trapezoid);
        }

        System.out.println("\nВміст хеш-таблиці перед видаленням:");
        hashTable.print();

        System.out.println("\nВидаляємо трапеції з периметром від 10 до 20:");
        hashTable.deleteByPerimeter(10, 20);

        System.out.println("\nВміст хеш-таблиці після видалення:");
        hashTable.print();

        System.out.println("\nДодаємо ще 3 випадкові трапеції:");
        for (int i = 0; i < 3; i++) {
            Trapezoid trapezoid = Trapezoid.generateRandom();
            System.out.println("Спроба додати трапецію: " + trapezoid);
            hashTable.insert(trapezoid);
        }

        System.out.println("\nФінальний вміст хеш-таблиці:");
        hashTable.print();
    }
}