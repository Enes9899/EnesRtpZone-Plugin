**Developer:** Enes9899  
**GitHub:** https://github.com/Enes9899  
**Modrinth:** https://modrinth.com/plugin/enesrtpzone  
**BuiltByBit:** https://builtbybit.com/resources/enes-rtpzone-plugin.106025/


---

## 🚀 Features / Özellikler

| English | Türkçe |
|---------|--------|
| RTP zone definition and management | RTP bölge tanımlama ve yönetimi |
| Easy zone selection with wand (left/right click) | Wand ile kolay bölge seçimi (sol/sağ tık) |
| Automatic random teleportation | Otomatik rastgele ışınlanma |
| Safe teleportation control (checks lava, water, cactus) | Güvenli ışınlanma kontrolü (lav, su, kaktüs vb. kontrol) |
| Folia support (global synchronized countdown - everyone sees same numbers) | Folia desteği (global senkronize geri sayım - herkes aynı sayıyı görür) |
| Countdown display with title messages | Title mesajları ile geri sayım gösterimi |
| Timer doesn't stop when leaving zone, starting player continues until finished | Bölgeden çıkınca sayaç durmaz, başlayan oyuncu bitirene kadar devam eder |

## 📋 Commands / Komutlar

| Command / Komut | Description / Açıklama | Permission / Yetki |
|-----------------|------------------------|------------------|
| `/rtpzone` | Shows help message / Yardım mesajını gösterir | `rtpzone.admin` |
| `/rtpzone wand` | Gives zone selection wand / Bölge seçim wand'ı verir | `rtpzone.admin` |
| `/rtpzone create <name>` | Saves selected zone / Seçilen bölgeyi kaydeder | `rtpzone.admin` |
| `/rtpzone delete <name>` | Deletes specified zone / Belirtilen bölgeyi siler | `rtpzone.admin` |
| `/rtpzone reset` | Resets selected positions / Seçilen pozisyonları sıfırlar | `rtpzone.admin` |
| `/rtpzone reload` | Reloads configuration / Konfigürasyonu yeniler | `rtpzone.admin` |

## 🔒 Permissions / Yetkiler

- `rtpzone.admin` - Access to all commands / Tüm komutlara erişim

## 📦 Installation / Kurulum

**English:**
1. Copy `enesrtpzone-1.0.jar` to the `plugins` folder
2. Start or restart your server
3. Edit `plugins/enesrtpzone/config.yml` to customize settings

**Türkçe:**
1. `enesrtpzone-1.0.jar` dosyasını `plugins` klasörüne kopyalayın
2. Sunucuyu başlatın veya yeniden başlatın
3. `plugins/enesrtpzone/config.yml` dosyasını düzenleyerek ayarları özelleştirin

## ⚙️ Configuration (config.yml) / Yapılandırma

```yaml
rtp:
  countdown: 30      # Countdown duration (seconds) / Geri sayım süresi (saniye)
  max-radius: 10000  # Maximum teleport distance / Maksimum ışınlanma mesafesi
  min-radius: 1000   # Minimum teleport distance / Minimum ışınlanma mesafesi

messages:
  prefix: "&8[&b&lRTP&f&lZone&8] &7"
  wand-received: "&aSelection wand received! / &aSeçim wand'ını aldınız!"
  select-first: "&cSelect two points first! (Left & Right click) / &cÖnce iki nokta seçin! (Sol ve Sağ tık)"
  pos1-set: "&aPoint 1 set! / &a1. nokta seçildi!"
  pos2-set: "&aPoint 2 set! / &a2. nokta seçildi!"
  zone-created: "&aZone '%name%' created! / &a'%name%' bölgesi oluşturuldu!"
  entered-zone: "&eYou entered RTP zone! &cCountdown started... / &eRTP bölgesine girdiniz! &cGeri sayım başladı..."
  left-zone: "&aYou left the RTP zone! / &aRTP bölgesinden çıktınız!"
  teleporting: "&aTeleporting to safe location... / &aGüvenli bir konuma ışınlanıyorsunuz..."
  countdown-title: "&6&l%time%"
  countdown-subtitle: "&e&nTeleporting in seconds / &e&nsaniye sonra ışınlanacaksınız"
```

## 📖 Usage / Nasıl Kullanılır

**English:**
1. Get wand with `/rtpzone wand`
2. Use wand to select point 1 (left click) and point 2 (right click)
3. Save zone with `/rtpzone create <zone_name>`
4. Players entering the zone will automatically teleport to random locations

**Türkçe:**
1. `/rtpzone wand` komutu ile wand alın
2. Wand'i kullanarak sol tık ile 1. noktayı, sağ tık ile 2. noktayı seçin
3. `/rtpzone create <bölge_ismi>` komutu ile bölgeyi kaydedin
4. Artık oyuncular bu bölgeye girdiklerinde otomatik olarak rastgele konumlara ışınlanacaklar

## ⚙️ System Logic / Sistem Çalışma Mantığı

| English | Türkçe |
|---------|--------|
| **Global Timer:** All players on server see same countdown (e.g. 30, 29, 28...) | **Global Sayaç:** Sunucudaki tüm oyuncular aynı geri sayımı görür (örn. 30, 29, 28...) |
| **Entering Zone:** Countdown displays, title messages appear | **Bölgeye Girince:** Geri sayım gösterilir, title mesajları çıkar |
| **Leaving Zone:** Countdown display stops, but global timer continues | **Bölgeden Çıkınca:** Geri sayım göstermeyi bırakır, ama kullanılan global sayaç devam eder |
| **Teleport Condition:** When timer hits 0, player teleports if still in zone | **Işınlanma Şartı:** Sayaç 0 olduğunda oyuncu hala bölgedeyse ışınlanır |

## 📋 Requirements / Gereksinimler

| English | Türkçe |
|---------|--------|
| Minecraft Server: Paper 1.21+, Folia, or Purpur | Minecraft Sunucu: Paper 1.21+, Folia, veya Purpur |
| Java: 21+ | Java: 21+ |

## 🔨 Building / Derleme

### Automatic Method (All Platforms) / Otomatik Yöntem (Tüm Platformlar)

**Windows:**
```bash
build-all.bat
```

**Linux/Mac:**
```bash
chmod +x build-all.sh
./build-all.sh
```

Output: 3 JAR files will be created / Çıktı: 3 adet JAR dosyası oluşturulacaktır:
- `EnesRtpZone-paper.jar`
- `EnesRtpZone-folia.jar`
- `EnesRtpZone-purpur.jar`

### Manual Method (Single Platform) / Manuel Yöntem (Tek Platform)

**Paper:**
```bash
mvn package -Ppaper
```

**Folia:**
```bash
mvn package -Pfolia
```

**Purpur:**
```bash
mvn package -Ppurpur
```

### Platform Differences / Platform Farkı

| Platform | English | Türkçe |
|----------|---------|--------|
| **Paper** | Standard Paper servers | Standart Paper sunucuları için |
| **Folia** | Folia multithreaded server (global synchronization support) | Folia multithreaded sunucusu için (global senkronizasyon desteği) |
| **Purpur** | Purpur server | Purpur sunucusu için |

**Important / Önemli:** Use the JAR file appropriate for your server. For example, if using Folia, copy the `-folia.jar` file.

## 📝 Changelog / Yapılan Değişiklikler

### v1.0

| English | Türkçe |
|---------|--------|
| Two-point zone selection system | İki çıkarılı bölge seçim sistemi |
| Global countdown system | Global geri sayım sistemi |
| Automatic random teleportation | Otomatik rastgele ışınlanma |
| Safe teleportation control | Güvenli ışınlanma kontrolü |
| Folia support (global synchronization) | Folia desteği (global senkronizasyon) |
| Countdown display with title messages | Title mesajları ile geri sayım gösterimi |
| **Bug Fix:** Instant teleport when timer ends (async methods made synchronous) | **Bug Fix:** Timer dolduğunda anında ışınlanma (async yöntemlerin senkron değiştirilmesi) |

## 📄 License / Lisans

```
MIT License

Copyright (c) 2026 Enes9899

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

This project was developed by Enes9899. / Bu proje Enes9899 tarafından geliştirilmiştir.
