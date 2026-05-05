import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Parametreler
        final long ogrenciNo = 1240505006L;
        final int n = 1_000_000; // veri boyutu
        final int k = 100_000;   // örnek sayısı
        final int RUNS = 100;    // deney sayısı

        Random dataRandom = new Random(ogrenciNo);       // veri üretimi
        Random testRandom = new Random(ogrenciNo + 1);   // deney için ayrı

        // Veri Seti Oluşturma
        int[] veriSeti = new int[n];
        for (int i = 0; i < n; i++) {
            veriSeti[i] = dataRandom.nextInt(1000000); // 0 - 1M
        }

        // Gerçek Oran
        int gercekSayac = 0;
        for (int x : veriSeti) {
            if (x % 7 == 0) {
                gercekSayac++;
            }
        }
        double gercekOran = (double) gercekSayac / n;

        // Monte Carlo
        List<Double> tahminler = new ArrayList<>();
        List<Double> sureler = new ArrayList<>();

        System.out.println("Deney başlatılıyor (100 tekrar)...");

        for (int deneme = 0; deneme < RUNS; deneme++) {

            long baslangic = System.nanoTime();

            int sayac = 0;

            for (int i = 0; i < k; i++) {
                int indeks = testRandom.nextInt(n);

                if (veriSeti[indeks] % 7 == 0) {
                    sayac++;
                }
            }

            double tahminiOran = (double) sayac / k;

            long bitis = System.nanoTime();
            double sureMs = (bitis - baslangic) / 1_000_000.0;

            tahminler.add(tahminiOran);
            sureler.add(sureMs);
        }

        // Ortalama Hesaplar
        double ortalamaTahmin = 0;
        for (double t : tahminler) ortalamaTahmin += t;
        ortalamaTahmin /= RUNS;

        double ortalamaSure = 0;
        for (double s : sureler) ortalamaSure += s;
        ortalamaSure /= RUNS;

        // Standart Sapma
        double varyans = 0;
        for (double t : tahminler) {
            varyans += Math.pow(t - ortalamaTahmin, 2);
        }
        varyans /= RUNS;
        double stdSapma = Math.sqrt(varyans);

        // Hata
        double hata = Math.abs(gercekOran - ortalamaTahmin);

        // Sonuç
        System.out.println("-----------------------------------------");
        System.out.println("Öğrenci No: " + ogrenciNo);
        System.out.println("Algoritma: Monte Carlo");
        System.out.println("Veri Boyutu (n): " + n);
        System.out.println("Örnek Sayısı (k): " + k);
        System.out.println("-----------------------------------------");
        System.out.println("Gerçek Oran: " + gercekOran);
        System.out.println("Tahmini Ortalama Oran: " + ortalamaTahmin);
        System.out.println("Hata: " + hata);
        System.out.println("-----------------------------------------");
        System.out.println("Ortalama Süre: " + String.format("%.4f", ortalamaSure) + " ms");
        System.out.println("Standart Sapma: " + String.format("%.6f", stdSapma));
    }
}