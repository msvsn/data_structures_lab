import java.util.Arrays;
import java.util.Random;

class Trapezoid {
    double x1, y1, x2, y2, x3, y3, x4, y4;

    public Trapezoid(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        this.x3 = x3; this.y3 = y3;
        this.x4 = x4; this.y4 = y4;
    }

    double area() {
        double a = Math.abs(x2 - x1);
        double b = Math.abs(x4 - x3);
        double h = Math.abs(y3 - y1);
        return 0.5 * (a + b) * h;
    }

    double perimeter() {
        return distance(x1, y1, x2, y2) + distance(x2, y2, x3, y3) +
                distance(x3, y3, x4, y4) + distance(x4, y4, x1, y1);
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public String toString() {
        return String.format("Trapezoid[Area=%.2f, Perimeter=%.2f]", area(), perimeter());
    }

    public boolean isValid() {
        if ((x1 == x2 && y1 == y2) || (x2 == x3 && y2 == y3) ||
            (x3 == x4 && y3 == y4) || (x4 == x1 && y4 == y1)) {
            return false;
        }

        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = (y4 - y3) / (x4 - x3);
        
        return Math.abs(k1 - k2) < 0.0001;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Trapezoid other = (Trapezoid) obj;
        return Math.abs(this.area() - other.area()) < 0.0001 &&
               Math.abs(this.perimeter() - other.perimeter()) < 0.0001;
    }

    public static Trapezoid generateRandom() {
        double minCoord = 0;
        double maxCoord = 10;
        Random rand = new Random();
        
        double x1 = minCoord + rand.nextDouble() * (maxCoord - minCoord);
        double x2 = x1 + 2 + rand.nextDouble() * 3;
        double x3 = x2 - 1 - rand.nextDouble() * 2;
        double x4 = x1 + 1 + rand.nextDouble() * 2;
        
        double y1 = minCoord;
        double y2 = y1;
        double y3 = y1 + 2 + rand.nextDouble() * 3;
        double y4 = y3;
        
        return new Trapezoid(x1, y1, x2, y2, x3, y3, x4, y4);
    }
}

class HashTable {
    private Trapezoid[] table;
    private int size;
    private int count;

    public HashTable(int size) {
        this.size = size;
        this.count = 0;
        table = new Trapezoid[size];
    }

    private void resize() {
        int newSize = size * 2;
        Trapezoid[] newTable = new Trapezoid[newSize];
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                int newIndex = hash(table[i].area(), newSize);
                newTable[newIndex] = table[i];
            }
        }
        table = newTable;
        size = newSize;
    }

    private int hash(double key, int tableSize) {
        double A = 0.6180339887;
        return (int) (tableSize * ((key * A) % 1));
    }

    private int hash(double key) {
        return hash(key, size);
    }

    public boolean contains(Trapezoid trapezoid) {
        for (int i = 0; i < size; i++) {
            if (table[i] != null && table[i].equals(trapezoid)) {
                return true;
            }
        }
        return false;
    }

    public void insert(Trapezoid trapezoid) {
        if (!trapezoid.isValid()) {
            System.out.println("Неможлива трапеція");
            return;
        }

        if (contains(trapezoid)) {
            System.out.println("Така трапеція вже існує!");
            return;
        }

        if (count >= size * 0.75) {
            resize();
        }

        int index = hash(trapezoid.area());
        if (table[index] != null) {
            System.out.println("Колізія за індексом " + index + ". Елемент не може бути доданий.");
            return;
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

public class Level1 {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(10);
        
        for (int i = 0; i < 5; i++) {
            Trapezoid trapezoid = Trapezoid.generateRandom();
            System.out.println("Спроба додати трапецію: " + trapezoid);
            hashTable.insert(trapezoid);
        }

        System.out.println("\nВміст хеш-таблиці:");
        hashTable.print();
    }
}
