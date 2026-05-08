import java.util.Random;

public class Main {

    // Koşul: veri elemanının 7'ye bölümünden kalan 0 mı?
    static boolean kosulSagla(int x) {
        return x % 7 == 0;
    }

    public static void main(String[] args) {

        //Parametreler
        final long OGRENCI_NO = 1240505006L;
        final int  N          = 1_000_000;   // veri boyutu (son 2 hane ≥ 5)
        final int  K          = 100_000;     // her denemede örnek sayısı
        final int  RUNS       = 100;         // deney sayısı
        final double EPSILON  = 0.005;       // hata toleransı (teorik hesap için)

        //Veri seti oluştur
        Random dataRng = new Random(OGRENCI_NO);
        int[] veri = new int[N];
        for (int i = 0; i < N; i++) {
            veri[i] = dataRng.nextInt(1_000_000);
        }

        //Gerçek oran (tam tarama)
        int gercekSayac = 0;
        for (int x : veri) {
            if (kosulSagla(x)) gercekSayac++;
        }
        double gercekOran = (double) gercekSayac / N;

        //Monte Carlo deneyleri
        double[] tahminler = new double[RUNS];
        double[] surelerMs = new double[RUNS];

        for (int r = 0; r < RUNS; r++) {
            // Her deneme bağımsız: ayrı seed
            Random rng = new Random(OGRENCI_NO + r + 1);

            long t0 = System.nanoTime();

            int sayac = 0;
            for (int i = 0; i < K; i++) {
                if (kosulSagla(veri[rng.nextInt(N)])) sayac++;
            }

            surelerMs[r] = (System.nanoTime() - t0) / 1_000_000.0;
            tahminler[r] = (double) sayac / K;
        }

        //İstatistikler
        double ortTahmin = ort(tahminler);
        double ortSure   = ort(surelerMs);
        double stdTahmin = std(tahminler, ortTahmin);
        double stdSure   = std(surelerMs, ortSure);

        //Teorik P(hata) — Chebyshev eşitsizliği
        //P(|X̄ - p| > ε) ≤ p(1-p) / (k · ε²)
        double p           = gercekOran;
        double teorikHata  = Math.min(1.0, p * (1 - p) / (K * EPSILON * EPSILON));

        //Deneysel hata oranı
        int hataliSayac = 0;
        for (double t : tahminler) {
            if (Math.abs(t - gercekOran) > EPSILON) hataliSayac++;
        }
        double deneyselHata = (double) hataliSayac / RUNS;

        //Sonuçlar
        System.out.println("=========================================");
        System.out.println("Öğrenci No    : " + OGRENCI_NO);
        System.out.println("Algoritma     : Monte Carlo");
        System.out.printf ("Veri boyutu n : %,d%n", N);
        System.out.printf ("Örnek sayısı k: %,d%n", K);
        System.out.printf ("Epsilon ε     : %.4f%n", EPSILON);
        System.out.println("=========================================");
        System.out.printf ("Gerçek oran           : %.6f%n", gercekOran);
        System.out.printf ("Ort. tahmini oran     : %.6f%n", ortTahmin);
        System.out.printf ("Mutlak hata           : %.6f%n", Math.abs(gercekOran - ortTahmin));
        System.out.println("-----------------------------------------");
        System.out.printf ("Tahmin std sapması    : %.6f%n", stdTahmin);
        System.out.println("-----------------------------------------");
        System.out.printf ("Ortalama süre         : %.4f ms%n", ortSure);
        System.out.printf ("Süre std sapması      : %.4f ms%n", stdSure);
        System.out.println("-----------------------------------------");
        System.out.printf ("Teorik P(hata) ≤      : %.6f  (Chebyshev)%n", teorikHata);
        System.out.printf ("Deneysel hata oranı   : %.2f%%%n", deneyselHata * 100);
        System.out.println("=========================================");
    }

    //ortalama
    static double ort(double[] dizi) {
        double toplam = 0;
        for (double v : dizi) toplam += v;
        return toplam / dizi.length;
    }

    //standart sapma
    static double std(double[] dizi, double ortalama) {
        double toplam = 0;
        for (double v : dizi) toplam += (v - ortalama) * (v - ortalama);
        return Math.sqrt(toplam / dizi.length);
    }
}
