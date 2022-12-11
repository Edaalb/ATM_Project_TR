package ATM;

import java.util.Scanner;

public class ATM_Project {

    /*
ATM
Kullanicidan giriş için kart numarasi ve şifresini isteyin, eger herhangi birini yanlis girerse tekrar isteyin.
Kart numarasi aralarda boşluk ile girerse de eger doğruysa kabul edin.
Kart numarasi ve sifre dogrulanirsa kullanicinin yapabileceği işlemleri ekrana yazdirin,

Menu listesinde Bakiye sorgula, para yatirma, para çekme, para gönderme, sifre değiştirme ve cikis islemleri olacaktır.

Para çekme ve para gonderme işleminde mevcut bakiyeden buyuk para çekilemez,
Para gönderme işleminde istenen iban TR ile baslamali ve toplam 26 karakterli olmali, eger değilse menü ekranina
geri donsun.
Sifre değiştirme işleminde mevcut şifreyi teyit ettikten sonra, sifre değişiklik işlemini yapmali,
 */
    final static String kartNo = "1234567890123456"; // sabit bir kart no belirleriz.
    // Kullanıcıdan aldığımız ile sistemdeikini karşılaştırıp sisteme kaydederiz.
    // Bu yüzden String bir kart no tanımlayabiliriz. Final olan hiçbir değişkende
    // sonradan değişiklik yapılamaz.
    //kart no’da bir değişiklik olmayacağı için final olarak tanımlayabiliriz
    static Scanner scan = new Scanner(System.in); // Scanner objectini oluşturulacak diğer
    // methodlarla kullanmak için class seviyesinde oluşturduk
    static String sifre = "1234";
    static double bakiye = 20000; //tüm methodlar static olmak zorunda yoksa method içinde çağıramayız.


    public static void main(String[] args) {
        giris(); //giriş için method oluştururuz.
        //Sağ clikck yapınca aşağıya otomatik metodu oluşturur.
        //oluşturduğumuz method static olduğu için Scanner objecti de static olmalı.
        //Böylece tümm oluşturulanlarda görülebilir.
    }

    private static void giris() {
        System.out.print("KART NUMARASI GIRINIZ:");

        String kKartno = scan.nextLine();

        System.out.print("SIFRE GIRINIZ: ");

        String kSifre = scan.next();
        kKartno = kKartno.replaceAll("\\s", ""); //aradaki boşlukları siler ve hiçlik ile değiştirir.
        // Yeni kart no olarak tanımlar.

        //sistemdeki şifre ile kullanıcın girdiği şifre ve kart noyu karşılaştırırız.
        if (kSifre.equals(sifre) && kKartno.equals(kartNo)) {
            //hem kart no hem şifre aynı olmak zorunda sisteme giriş yapabilmek için
            menu(); //menü methodu oluşturuduk, sağ click ile aşağıda oluştu
        } else {
            System.out.println("YANLIS GIRIS YAPILDI");
            giris();
        }
    }

    //default olarak privite method oluşturdu, tek class’ta çalışacağımız için değiştirmeye gerek yok.
    private static void menu() {
        System.out.println("******JAVABANKA HOSGELDİNİZ*****\n" +
                "YAPMAK ISTEDIGINIZ ISLEMİ SECINIZ\n" +
                "1. BAKIYE SORGULAMA\n" +
                "2. PARA YATIRMA\n" +
                "3. PARA CEKME\n" +
                "4. PARA GONDERME\n" +
                "5. SIFRE DEGISTIRME\n" +
                "6. CIKIS");
        //seçimimizi if veya switch ile yaptırabiliriz.
        int secim = scan.nextInt(); //switch case’i kullanmak için key oluştururuz.
        switch (secim) {
            case 1: {
                bakiyeSorgula(); //bu methodu oluştururken herhangi bir parametre göndermeye gerek yoktur,
                //sadece ne kadar var diye bakacağız. Sağ click ile methodu oluşturduk
                //aşağıda method içinde gerekli işlemleri yapalım
            }
            case 2: {
                System.out.print("YATIRILACAK MIKTARI GIRINIZ:");
                double miktar = scan.nextDouble();
                paraYatirma(miktar); //parametre gerekir, kullanıcıya ne kadar para yatıracağını sormamız gerekir
            }
            case 3: {
                System.out.println("CEKILECEK MIKTARI GIRINIZ:");
                double miktar = scan.nextDouble();
                paraCekme(miktar); //para yatırmadaki miktar ile çekmedeki miktar scopeları farklı olduğu için
                // farklı veraiblelardır.
            }
            case 4: {
                System.out.println("IBAN GIRINIZ: ");
                String iban = scan.nextLine();
                scan.nextLine();//Java’da ard arda next ve nextLine yaptığımızda ya da
                // scan objectlerini çağırdığınızda problem olabiliyor.
                // Bundan kurtulmak için araya dummy denilen scan objecti atılır.
                // Yani araya bir enter eklenmiş olur

                System.out.println("GONDERILECEK MIKTARI GIRINIZ:");
                double miktar = scan.nextDouble();

                paraGonderme(ibanKontrol(iban), miktar);
            }
            case 5: {
                sifreDegistir(); //parametreye gerek yoktur
            }
            case 6: {
                System.out.println("******HOSCAKALIN******");
                System.exit(0); //bu kullanım break ile neredeyse aynı yapıda çalışır.
                // Bu parametreyi gönderdiğimizde programı orada bitirir.
                //Bu satıra gelince program biter
            }

        }
    }

    private static void sifreDegistir() {
        System.out.println("ESKI SIFRENIZI GIRINIZ: ");
        String kSifre = scan.next(); //kSifre kullanıcıdan istediğimiz şifre
        if (kSifre.equals(sifre)) { //kullanıcının girdiği şifre ile mevcut şifre eşleşiyorsa
            System.out.println("YENI SIFRE GIRINIZ:");
            sifre = scan.next(); //yeni şifre oluşmuş olur
            scan.nextLine();
            giris(); //şifre değiştikten sonra tekrardan sisteme girmesi gerek bu yüzden ilk giriş methodunu çağırdık
        } else {
            System.out.println("DOGRU SIFRE GIRINIZ: ");
            sifreDegistir(); //şifreyi yanlış girerse yeniden şifre oluşturmaya döndürür
        }
    }

    private static void paraGonderme(String iban, double miktar) {
        if (miktar <= bakiye) {
            bakiye -= miktar;
            System.out.println(iban + " NOLU IBANA " + miktar + " GONDERILDI");
            bakiyeSorgula();
        } else {
            System.out.println("GECERLI MIKTAR GIRINIZ:");
            paraGonderme(iban, scan.nextDouble());
        }
    }

    //yukarıdan iban’ı gönderdik. Boşlukları aldırdık. If içinde kontrolleri yaptık karakter
    // ve TR ile başlıyor mu diye. Doğruysa if bloğuna girer, içinde bir şey yok, return ile döndürür.
    // Eğer kriterleri sağlamıyorsa tekrardan iban ister ve geri döndürür, return’u göstermez bu durumda
    private static String ibanKontrol(String iban) {
        iban = iban.replaceAll("\\s", ""); //boşluklu ifadeleri siler
        if (iban.startsWith("TR") && iban.length() == 26) { //lenght’i 26 olan ve TR ile başlayan iban döndürür


        } else {
            System.out.println("GECERLI IBAN GIRINIZ: ");
            //String iban2 = scan.nextLine();
            //ibanKontrol(iban2);
            scan.nextLine(); //yanlış girdiği için tekrar iban alırız
            ibanKontrol(scan.nextLine());
        }
        return iban;
    }


    private static void paraCekme(double miktar) {
//şartlarda mevcut baiyeden büyük miktarda para çekilemez var.
        if (miktar <= bakiye) { //mevcut bakiye ile aynı yada küçük miktar
            bakiye -= miktar; //bakiyeyi azalt
            bakiyeSorgula(); //işlemden sonra bakiye sorgula tekrardan


        } else {
            System.out.println("GECERLI MIKTAR GIRINIZ"); //işlememiz miktardan küçük ise uyarı verir
            paraCekme(scan.nextDouble()); //recursive method uyguladık, tekrardan para çekme ekranına gönderecek
        }
    }

    private static void paraYatirma(double miktar) {
        bakiye += miktar; //bakiyeye ekleme yaparız
        bakiyeSorgula(); //para yattıktan sonra bakiye sorgularız tekrardan İşlem bittikten
        //sonra tekrar tekrar başa dönmesine recursive method denir. Kendini tekrar etmesidir.

    }

    //en yukarıda bakiye için bir variable oluşturduk ve değer atadık
    private static void bakiyeSorgula() {
        System.out.println("BAKIYENIZ: " + bakiye);
        menu(); // menü methodunu buraya yazma sebebimiz bakiyeyi öğrendikten sonra tekrardan menüye dönebilmek için
    }
}
