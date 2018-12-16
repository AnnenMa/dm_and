public class RandomArray  {                        //Aufgabe 1
    public static void fillRandomArray (int []a) { //fuellt das gegebene Array mit Randomzahlen auf und gibt es aus
        int maxrand = 100;
        for (int i=0; i<a.length; i++) {
            a[i] = (int) (maxrand * Math.random());
            System.out.print(a[i] + " ");
        }
    }

    public static  int minIntegerArray (int []a) { //liefert Index vom kleinsten Wert
        int min = 0;
        for (int i=1; i<a.length; i++) {
            if (a[i]<a[min]) {
                min = i;
            }
        }
        return min;
    }
    public static double averageIntegerArray (int []a) { //berechnet arithmetisches Mittel
        int sumofelem = 0;
        for (int i=0;i<=a.length;i++) {
            sumofelem+=a[i];
        }
        double result;
        result = sumofelem/a.length;
        return result;
    }

    public static void main(String[] args) {
        int []b = new int [10];
        fillRandomArray(b);
        System.out.println("Das Index vom kleinsten Element ist: " + minIntegerArray(b));
        
    }

}
