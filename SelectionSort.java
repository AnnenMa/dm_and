public class SelectionSort { 

    public static String[] strselsort (String []a) { //Aufgabe 2. Selection sort Algorithmus fuer Array von String
        int n = a.length;
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (0 > a[j].compareTo(a[min_idx])) { //compareTo vergleicht zwei Strings lexikographisch
                    min_idx = j;
                }
                // Swap the found minimum element with the first
                // element
            }
                String temp = a[min_idx];
                a[min_idx] = a[i];
                a[i] = temp;
        }
        return a;
    }


    public static void  showstr (String[]a){     //Die Funktion gibt array von Strings aus.
        for (int i=0;i<a.length; i++) {
            System.out.println(a[i]);
        }
    }
    public static void main(String[] args) {
        String[] result;
        String path = args [0];                  //Speichere Pfad
        result = IO.readFileToStringArray(path); //Lese aus
        result = strselsort(result);             //Sortiere
        showstr(result);                         // Zeige
    }
}
