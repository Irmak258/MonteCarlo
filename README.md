# Monte Carlo Algorithma Projesi

# Proje Açıklması
Bu projede, büyük bir veri seti içerisinde belirli bir özelliği sağlayan elemanların oranını bulmak için Monte Carlo algoritması kullanılmıştır.

Öğrenci numarasının son iki hanesi çift olduğu için Monte Carlo yaklaşımı tercih edilmiştir.

---

# Problem Tanımı
1,000,000 elemanlı rastgele oluşturulmuş bir dizide:

> **7'ye tam bölünen sayıların oranı** hesaplanmaktadır.

---

# Kullanılan Yöntem

# Monte Carlo Yaklaşımı
- Tüm veri yerine rastgele örnekleme yapılır
- k adet rastgele indeks seçilir
- Oran tahmini yapılır

---

# Deney Parametreleri

| Parametre | Değer |
|----------|------|
| Veri Boyutu (n) | 1,000,000 |
| Örnek Sayısı (k) | 100,000 |
| Deney Sayısı | 100 |
| Seed | Öğrenci numarası |

---

# Sonuçlar

| Ölçüm | Değer |
|------|------|
| Gerçek Oran | 0.14286 |
| Tahmini Oran | 0.14276 |
| Hata | ~0.000095 |
| Ortalama Süre | ~4.4 ms |
| Standart Sapma | 0.001026 |

---

# Teorik Arka Plan

Bir sayının 7'ye bölünebilme olasılığı:

p ≈ 1/7 ≈ 0.142857

Monte Carlo hata davranışı:

P(error) ≈ e^(-2kε²)

Yaklaşık hata:

Hata ≈ 1 / √k

---

# Karmaşıklık Analizi

| Yöntem | Karmaşıklık |
|------|------------|
| Full Tarama | O(n) |
| Monte Carlo | O(k) |

k << n olduğu için Monte Carlo çok daha hızlıdır.

---

# Yorum

- Monte Carlo algoritması hızlı ve etkilidir
- Tahmini sonuç gerçek değere oldukça yakındır
- Örnek sayısı arttıkça hata azalmaktadır
- Standart sapmanın düşük olması algoritmanın kararlı olduğunu gösterir

---

# Çalıştırma

```bash
javac MonteCarloOdevi.java
java MonteCarloOdevi
