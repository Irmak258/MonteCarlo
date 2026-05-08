# Monte Carlo Algoritması — Randomize Algoritmalar Ödevi

**Öğrenci No:** 1240505006  
**Algoritma Tipi:** Monte Carlo (son iki hane çift → 06)  
**Veri Boyutu:** n = 1.000.000 (son rakam ≥ 5 → 10⁶)  
**Dil:** Java  

---

## Proje Hakkında

Bu proje, büyük bir veri setinde belirli bir koşulu sağlayan elemanların oranını **Monte Carlo yaklaşımıyla** tahmin etmeyi amaçlamaktadır.

**Problem:** 1.000.000 elemanlı rastgele bir dizide, **7'ye tam bölünen sayıların oranı** nedir?

Tüm diziyi gezmek yerine rastgele örnekler alınarak olasılıksal bir tahmin yapılır. Sonucun ne kadar doğru olduğu hem deneysel hem de teorik olarak ölçülür.

---

## Dosya Yapısı

```
├── Main.java        # Ana algoritma kodu
└── README.md        # Bu dosya
```

---

## Parametreler

| Parametre | Değer | Açıklama |
|---|---|---|
| `OGRENCI_NO` | 1240505006 | Seed kaynağı |
| `N` | 1.000.000 | Veri seti boyutu |
| `K` | 100.000 | Her deneyde alınan örnek sayısı |
| `RUNS` | 100 | Bağımsız deney sayısı |
| `EPSILON` | 0.005 | Hata toleransı (teorik hesap için) |

---

## Nasıl Çalışır?

### 1. Veri Üretimi
```java
Random dataRng = new Random(OGRENCI_NO);
```
Öğrenci numarası seed olarak verilir. Aynı seed → aynı veri → tekrarlanabilir sonuç.

### 2. Gerçek Oran (Referans)
Tüm dizi taranarak 7'ye bölünen elemanların oranı hesaplanır. Bu değer Monte Carlo tahmininin ne kadar doğru olduğunu ölçmek için kullanılır.

### 3. Monte Carlo Deneyleri
```java
Random rng = new Random(OGRENCI_NO + r + 1);
```
Her deney için **ayrı bir seed** kullanılır. Bu sayede 100 deney birbirinden istatistiksel olarak bağımsız olur.

Her deneyde:
- 100.000 rastgele indeks seçilir
- O indekslerdeki elemanların kaçı 7'ye bölünüyor sayılır
- `tahminiOran = sayac / K` hesaplanır

### 4. İstatistikler
- Ortalama tahmin ve standart sapması
- Ortalama çalışma süresi ve standart sapması
- Deneysel hata oranı (ε'dan fazla sapan deney sayısı / 100)

### 5. Teorik Hata — Chebyshev Eşitsizliği

```
P(|tahmin − p| > ε)  ≤  p(1−p) / (k · ε²)
```

Dağılımdan bağımsız çalışan bu formül, hata olasılığına bir **üst sınır** verir. Deneysel hata oranı bu sınırın altında çıkarsa algoritma başarılı demektir.

---

## Çalıştırma

```bash
javac Main.java
java Main
```

Java 8 veya üzeri yeterlidir, harici kütüphane gerekmez.

---

## Örnek Çıktı

```
=========================================
Öğrenci No    : 1240505006
Algoritma     : Monte Carlo
Veri boyutu n : 1.000.000
Örnek sayısı k: 100.000
Epsilon ε     : 0.0050
=========================================
Gerçek oran           : 0.142974
Ort. tahmini oran     : 0.142981
Mutlak hata           : 0.000007
-----------------------------------------
Tahmin std sapması    : 0.001102
-----------------------------------------
Ortalama süre         : 3.2418 ms
Süre std sapması      : 0.1843 ms
-----------------------------------------
Teorik P(hata) ≤      : 0.039700  (Chebyshev)
Deneysel hata oranı   : 0.00%
=========================================
```

---

## Teorik Arka Plan

### Neden ~0.1429?
0'dan 999.999'a kadar olan sayılarda her 7 sayıdan 1 tanesi 7'ye bölünür → oran ≈ 1/7 ≈ 0.1429.

### Neden Chebyshev?
Normal dağılım varsayımı gerektirmez. k yeterince büyük olduğu sürece güvenilir bir üst sınır verir. Kaba bir tahmindir ama **her zaman doğrudur**.

### Monte Carlo vs Las Vegas

| | Monte Carlo | Las Vegas |
|---|---|---|
| Doğruluk | Olasılıksal | %100 garanti |
| Süre | Sabit (k adım) | Değişken |
| Hata analizi | P(hata) formülü | E[X] beklenen adım |
| Bu ödev | ✅ | — |

---

## Notlar

- Veri üretimi ve deney `Random` nesneleri birbirinden ayrıdır — veri sabit, deneyler bağımsız.
- `kosulSagla()` metodu ayrı tutulmuştur; koşul değiştirilmek istenirse tek bir yer güncellenir.
- Chebyshev sonucu `Math.min(1.0, ...)` ile sınırlandırılmıştır; olasılık 1'i geçemez.
