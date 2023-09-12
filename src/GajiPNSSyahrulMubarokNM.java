import java.text.DecimalFormat;
import java.util.Scanner;
/*
 * Created By : Syahrul Mubarok Nur Muhammad
 * Created At : 7.00 Selasa, 12 September 2023
 */
public class GajiPNSSyahrulMubarokNM {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    // deklarasi variable
    // regex
    String regexHuruf = "^[a-zA-Z ]+$", regexNumber = "^[0-9]+$", regexStatusMenikah = "Kawin|KAWIN|kawin|Belum Kawin|BELUM KAWIN|belum kawin|Cerai|CERAI|cerai";
    // variable input
    String namaPegawai, golonganRuangKerja, statusPernikahan;
    int golonganPangkat, masaKerja, jumlahAnak;

    // input
    namaPegawai = validasiInput("Masukan Nama Pegawai: ", "Inputan hanya menerima huruf saja!", regexHuruf);
    golonganPangkat = validasiNumberWithRange("Masukan Golongan Pangkat (1 s/d 4): ", "Inputan harus berupa angka dan sesuai dengan golongan pangkat 1 s/d 4!", regexNumber, 4, 1 );
    golonganRuangKerja = validasiGolonganPangkat("Masukan Golongan Ruang Kerja: ", "Inputan harus berupa huruf & golongan ruang kerja yang sesuai!", golonganPangkat);
    masaKerja = validasiNumberWithRange("Masukan Masa Kerja: ", "Inputan harus berupa angka (0 - 50)", regexNumber, 50, 0);
    statusPernikahan = validasiInput("Masukan Status Pernikahan: ", "Inputan harus berupa huruf & hanya boleh diisi dengan Kawin, Belum Kawin, dan Cerai", regexStatusMenikah);
    jumlahAnak = validasiNumberWithRange("Masukan Jumlah Anak: ", "Inputan harus berupa angka positif!", regexNumber, 999, 0);

//    proses
    double gajiPokok = kalkulasiGajiPokok(masaKerja, golonganRuangKerja, golonganPangkat);
    double tunjanganKeluarga = kalkulasiTunjanganKeluarga(statusPernikahan, (int) gajiPokok);
    double tunjanganAnak = kalkulasiTunjanganAnak(jumlahAnak, (int) gajiPokok);
    double tunjanganBeras = kalkulasiTunjanganBeras(statusPernikahan, jumlahAnak);
    double tunjanganUmumJabatan = kalkulasiTunjanganUmumJabatan(golonganPangkat);
    double gajiKotor = kalkulasiGajiKotor((int) gajiPokok, (int) tunjanganAnak, (int) tunjanganKeluarga, (int) tunjanganUmumJabatan, (int) tunjanganBeras);
    double potonganPPH = kalkulasiPotonganPPH((int) gajiPokok, statusPernikahan, (int) gajiKotor, jumlahAnak, (int) tunjanganKeluarga, (int) tunjanganAnak);
    double potonganIWP = kalkulasiPotonganIWP(gajiPokok, tunjanganAnak, tunjanganKeluarga);
    double potonganTaperum = kalkulasiPotonganTaperum(golonganPangkat);
    double gajiBersih = kalkulasiGajiBersih((int )gajiKotor, (int) potonganPPH, potonganIWP, potonganTaperum);

//    output
    displayOutput(namaPegawai, (int) gajiPokok, (int) tunjanganKeluarga, (int) tunjanganAnak, (int) tunjanganBeras, (int) tunjanganUmumJabatan, (int) gajiKotor, (int) potonganPPH, potonganIWP, (int) potonganTaperum, gajiBersih);
  }

  public static String formatDecimal(double value) {
    DecimalFormat df = new DecimalFormat("#,###.00");

    return df.format(value);
  }

  public static String validasiInput(String question, String errorMessage, String regex) {
    Scanner input = new Scanner(System.in);
    String result;
    boolean isLooping = true;
    do {
      System.out.print(question);
      result = input.nextLine();

      //validasi menggunakan matches
      if (result.matches(regex)) {
        isLooping = false;
      }else {
        System.out.println(errorMessage);
      }

    } while (isLooping);

    return result;
  }

  public static int validasiNumberWithRange(String question, String errorMessage, String regex, int max, int min) {
    int result;
    boolean isLooping = true;
    do {
      result = Integer.valueOf(validasiInput(question, errorMessage, regex));
      if (result >= min && result <= max) {
        isLooping = false;
      }else {
        System.out.println("Golongan Pangkat harus " + min + " s.d " + max);
      }
    } while (isLooping);

    return result;
  }

  public static String validasiGolonganPangkat(String question, String errorMessage, int golonganPangkat) {
    String result = "";
    String regexGolonganPangkat1sd3 = "A|a|B|b|C|c|D|d";
    String regexGolonganPangkat4 = "A|a|B|b|C|c|D|d|E|e";

    if (golonganPangkat <= 3) {
      result = validasiInput(question, errorMessage, regexGolonganPangkat1sd3);
    } else if (golonganPangkat == 4) {
      result = validasiInput(question, errorMessage, regexGolonganPangkat4);
    } else {
      result = "Golongan pangkat tidak sesuai!";
    }

    return result;
  }

  public static double kalkulasiGajiPokok(int masaKerja, String tipeGolongan,int golonganPangkat) {
    double gajiPokok = 0;

    double[][][] listGajiPokok = {
            {
              {1560800.00, 1560800.00, 1560800.00, 1560800.00},
              {1560800.00, 1560800.00, 1560800.00, 1560800.00},
              {1610000.00, 1610000.00, 1610000.00, 1610000.00},
              {1610000.00, 1704500.00, 1776600.00, 1851800.00},
              {1660700.00, 1704500.00, 1776600.00, 1851800.00},
              {1660700.00, 1758200.00, 1832600.00, 1910100.00},
              {1713000.00, 1758200.00, 1832600.00, 1910100.00},
              {1713000.00, 1813600.00, 1890300.00, 1970200.00},
              {1766900.00, 1813600.00, 1890300.00, 1970200.00},
              {1766900.00, 1870700.00, 1949800.00, 2032300.00},
              {1822600.00, 1870700.00, 1949800.00, 2032300.00},
              {1822600.00, 1929600.00, 2011200.00, 2096300.00},
              {1880000.00, 1929600.00, 2011200.00, 2096300.00},
              {1880000.00, 1990400.00, 2074600.00, 2162300.00},
              {1939200.00, 1990400.00, 2074600.00, 2162300.00},
              {1939200.00, 2053100.00, 2139900.00, 2230400.00},
              {2000300.00, 2053100.00, 2139900.00, 2230400.00},
              {2000300.00, 2117700.00, 2207300.00, 2300700.00},
              {2063300.00, 2117700.00, 2207300.00, 2300700.00},
              {2063300.00, 2184400.00, 2276800.00, 2373100.00},
              {2128300.00, 2184400.00, 2276800.00, 2373100.00},
              {2128300.00, 2253200.00, 2348500.00, 2447900.00},
              {2195300.00, 2253200.00, 2348500.00, 2447900.00},
              {2195300.00, 2324200.00, 2422500.00, 2525000.00},
              {2264400.00, 2324200.00, 2422500.00, 2525000.00},
              {2264400.00, 2397400.00, 2498800.00, 2604500.00},
              {2335800.00, 2397400.00, 2498800.00, 2604500.00},
              {2335800.00, 2472900.00, 2557500.00, 2686500.00}
            },{
                {2022200.00, 2022200.00, 2022200.00, 2022200.00},
                {2054100.00, 2054100.00, 2054100.00, 2054100.00},
                {2054100.00, 2054100.00, 2054100.00, 2054100.00},
                {2118800.00, 2208400.00, 2301800.00, 2399200.00},
                {2118800.00, 2208400.00, 2301800.00, 2399200.00},
                {2185500.00, 2277900.00, 2374300.00, 2474700.00},
                {2185500.00, 2277900.00, 2374300.00, 2474700.00},
                {2254300.00, 2349700.00, 2449100.00, 2552700.00},
                {2254300.00, 2349700.00, 2449100.00, 2552700.00},
                {2325300.00, 2423700.00, 2526200.00, 2633100.00},
                {2325300.00, 2423700.00, 2526200.00, 2633100.00},
                {2398600.00, 2500000.00, 2605800.00, 2716000.00},
                {2398600.00, 2500000.00, 2605800.00, 2716000.00},
                {2474100.00, 2578800.00, 2687800.00, 2801500.00},
                {2474100.00, 2578800.00, 2687800.00, 2801500.00},
                {2552000.00, 2660000.00, 2772500.00, 2889800.00},
                {2552000.00, 2660000.00, 2772500.00, 2889800.00},
                {2632400.00, 2743800.00, 2859800.00, 2980800.00},
                {2632400.00, 2743800.00, 2859800.00, 2980800.00},
                {2715300.00, 2830200.00, 2830200.00, 2830200.00},
                {2715300.00, 2830200.00, 2949900.00, 3074700.00},
                {2800800.00, 2919300.00, 3042800.00, 3171500.00},
                {2800800.00, 2919300.00, 3042800.00, 3171500.00},
                {2889100.00, 3011200.00, 3138600.00, 3271400.00},
                {2889100.00, 3011200.00, 3138600.00, 3271400.00},
                {2980000.00, 3106100.00, 3237500.00, 3374400.00},
                {2980000.00, 3106100.00, 3237500.00, 3374400.00},
                {3073900.00, 3203900.00, 3339400.00, 3480700.00},
                {3073900.00, 3203900.00, 3339400.00, 3480700.00},
                {3170700.00, 3304800.00, 3444600.00, 3590300.00},
                {3170700.00, 3304800.00, 3444600.00, 3590300.00},
                {3270600.00, 3408900.00, 3553100.00, 3703400.00},
                {3270600.00, 3408900.00, 3553100.00, 3703400.00},
                {3373600.00, 3516300.00, 3665000.00, 3820000.00}
            },{
                {2579400.00, 2688500.00, 2802300.00, 2920800.00},
                {2579400.00, 2688500.00, 2802300.00, 2920800.00},
                {2660700.00, 2773200.00, 2890500.00, 3012800.00},
                {2660700.00, 2773200.00, 2890500.00, 3012800.00},
                {2744500.00, 2860500.00, 2981500.00, 3107700.00},
                {2744500.00, 2860500.00, 2981500.00, 3107700.00},
                {2830900.00, 2950600.00, 3075500.00, 3205500.00},
                {2830900.00, 2950600.00, 3075500.00, 3205500.00},
                {2920100.00, 3043600.00, 3172300.00, 3306500.00},
                {2920100.00, 3043600.00, 3172300.00, 3306500.00},
                {3012000.00, 3139400.00, 3272200.00, 3410600.00},
                {3012000.00, 3139400.00, 3272200.00, 3410600.00},
                {3106900.00, 3238300.00, 3375300.00, 3518100.00},
                {3106900.00, 3238300.00, 3375300.00, 3518100.00},
                {3204700.00, 3340300.00, 3481600.00, 3628900.00},
                {3204700.00, 3340300.00, 3481600.00, 3628900.00},
                {3305700.00, 3445500.00, 3591200.00, 3743100.00},
                {3305700.00, 3445500.00, 3591200.00, 3743100.00},
                {3409800.00, 3554000.00, 3704300.00, 3861000.00},
                {3409800.00, 3554000.00, 3704300.00, 3861000.00},
                {3517200.00, 3665900.00, 3821000.00, 3982600.00},
                {3517200.00, 3665900.00, 3821000.00, 3982600.00},
                {3627900.00, 3781400.00, 3941400.00, 4108100.00},
                {3627900.00, 3781400.00, 3941400.00, 4108100.00},
                {3742200.00, 3900500.00, 4065500.00, 4237500.00},
                {3742200.00, 3900500.00, 4065500.00, 4237500.00},
                {3860100.00, 4023300.00, 4193500.00, 4370900.00},
                {3860100.00, 4023300.00, 4193500.00, 4370900.00},
                {3981600.00, 4150100.00, 4325600.00, 4508600.00},
                {3981600.00, 4150100.00, 4325600.00, 4508600.00},
                {4107000.00, 4280800.00, 4461800.00, 4650600.00},
                {4107000.00, 4280800.00, 4461800.00, 4650600.00},
                {4236400.00, 4415600.00, 4602400.00, 4797000.00}
            },{
                {3044300.00, 3173100.00, 3307300.00, 3447200.00, 3593100.00},
                {3044300.00, 3173100.00, 3307300.00, 3447200.00, 3593100.00},
                {3140200.00, 3272100.00, 3411500.00, 3555800.00, 3706200.00},
                {3140200.00, 3272100.00, 3411500.00, 3555800.00, 3706200.00},
                {3239100.00, 3376100.00, 3518900.00, 3667800.00, 3822900.00},
                {3239100.00, 3376100.00, 3518900.00, 3667800.00, 3822900.00},
                {3341100.00, 3482500.00, 3629800.00, 3783300.00, 3943300.00},
                {3341100.00, 3482500.00, 3629800.00, 3783300.00, 3943300.00},
                {3446400.00, 3592100.00, 3744100.00, 3902500.00, 4067500.00},
                {3446400.00, 3592100.00, 3744100.00, 3902500.00, 4067500.00},
                {3554900.00, 3705300.00, 3862000.00, 4025400.00, 4195700.00},
                {3554900.00, 3705300.00, 3862000.00, 4025400.00, 4195700.00},
                {3666900.00, 3822000.00, 3983600.00, 4152200.00, 4327800.00},
                {3666900.00, 3822000.00, 3983600.00, 4152200.00, 4327800.00},
                {3782400.00, 3942400.00, 4109100.00, 4282900.00, 4462100.00},
                {3782400.00, 3942400.00, 4109100.00, 4282900.00, 4462100.00},
                {3901500.00, 4066500.00, 4238500.00, 4417800.00, 4604700.00},
                {3901500.00, 4066500.00, 4238500.00, 4417800.00, 4604700.00},
                {4024400.00, 4194600.00, 4372000.00, 4557000.00, 4749700.00},
                {4024400.00, 4194600.00, 4372000.00, 4557000.00, 4749700.00},
                {4151100.00, 4326700.00, 4509700.00, 4700500.00, 4899300.00},
                {4151100.00, 4326700.00, 4509700.00, 4700500.00, 4899300.00},
                {4281800.00, 4463000.00, 4651800.00, 4848500.00, 5053600.00},
                {4281800.00, 4463000.00, 4651800.00, 4848500.00, 5053600.00},
                {4416700.00, 4603500.00, 4798300.00, 5001200.00, 5212800.00},
                {4416700.00, 4603500.00, 4798300.00, 5001200.00, 5212800.00},
                {4555800.00, 4748500.00, 4949400.00, 5158700.00, 5377000.00},
                {4555800.00, 4748500.00, 4949400.00, 5158700.00, 5377000.00},
                {4699300.00, 4898100.00, 5105300.00, 5321200.00, 5546300.00},
                {4699300.00, 4898100.00, 5105300.00, 5321200.00, 5546300.00},
                {4847300.00, 5052300.00, 5266100.00, 5488800.00, 5721000.00},
                {4847300.00, 5052300.00, 5266100.00, 5488800.00, 5721000.00},
                {5000000.00, 5211500.00, 5431900.00, 5661700.00, 5901200.00}
            },
    };
    String[] listTipeGolongan = {"A", "B", "C", "D", "E"};

    for (int i = 0; i < listGajiPokok.length; i++) {
      for (int j = 0; j < listGajiPokok[i].length; j++) {
        for (int k = 0; k < listGajiPokok[i][j].length; k++) {
          if (golonganPangkat == i + 1 && masaKerja > listGajiPokok[i].length&& tipeGolongan.equalsIgnoreCase(listTipeGolongan[k])) {
            gajiPokok = listGajiPokok[i][listGajiPokok[i].length - 1][k];
            break;
          } else if (golonganPangkat == i + 1 && masaKerja == j && tipeGolongan.equalsIgnoreCase(listTipeGolongan[k])) {
            gajiPokok = listGajiPokok[i][j][k];
            break;
          }
        }
      }
    }

    return gajiPokok;
  }

  public static double kalkulasiTunjanganKeluarga(String statusPernikahan, int gajiPokok) {
    double tunjanganKeluarga = 0;

    if (statusPernikahan.equalsIgnoreCase("Kawin")) {
      tunjanganKeluarga = 0.1 * gajiPokok;
    } else {
      tunjanganKeluarga = 0;
    }

    return tunjanganKeluarga;
  }

  public static double kalkulasiTunjanganAnak(int jumlahAnak, int gajiPokok) {
    double tunjanganAnak = 0;

    if (jumlahAnak > 2) {
      tunjanganAnak = 0.02 * 2 * gajiPokok;
    } else if (jumlahAnak >= 1 && jumlahAnak <= 2) {
      tunjanganAnak = 0.02 * jumlahAnak * gajiPokok;
    } else {
      tunjanganAnak = 0;
    }

    return tunjanganAnak;
  }

  public static double kalkulasiTunjanganBeras(String statusPernikahan, int jumlahAnak) {
    double tunjanganBeras = 0;
    int hargaBerasPerKG = 15000; // 15rebu
    int jumlahBerasPerAnggotaKeluarga = 10; //10kg

    if (statusPernikahan.equalsIgnoreCase("Kawin") && jumlahAnak > 2) {
      tunjanganBeras = (2 + 2) * (jumlahBerasPerAnggotaKeluarga * hargaBerasPerKG);
    } else if (statusPernikahan.equalsIgnoreCase("Kawin") && jumlahAnak >= 0 && jumlahAnak <= 2) {
      tunjanganBeras = (2 + jumlahAnak) * (jumlahBerasPerAnggotaKeluarga * hargaBerasPerKG);
    } else if (statusPernikahan.equalsIgnoreCase("Cerai") && jumlahAnak > 2) {
      tunjanganBeras = (1 + 2) * (jumlahBerasPerAnggotaKeluarga * hargaBerasPerKG);
    } else if (statusPernikahan.equalsIgnoreCase("Cerai") && jumlahAnak >= 0 && jumlahAnak <= 2) {
      tunjanganBeras = (1 + jumlahAnak) * (jumlahBerasPerAnggotaKeluarga * hargaBerasPerKG);
    } else {
      tunjanganBeras = (jumlahBerasPerAnggotaKeluarga * hargaBerasPerKG);
    }

    return tunjanganBeras;
  }

  public static double kalkulasiTunjanganUmumJabatan(int golonganPangkat) {
    double tunjanganUmumJabatan = 0;
    int[] listGolonganPangkat = {1, 2, 3, 4};
    int[] listTunjanganUmumJabatan = {175000, 180000, 185000, 190000};

    for (int i = 0; i < listGolonganPangkat.length; i++) {
      if (golonganPangkat == listGolonganPangkat[i]) {
        tunjanganUmumJabatan = listTunjanganUmumJabatan[i];
        break;
      }
    }
    return tunjanganUmumJabatan;
  }

  public static double kalkulasiGajiKotor(int gajiPokok, int tunjanganAnak, int tunjanganKeluarga, int tunjanganUmumJabatan, int tunjanganBeras) {
    return gajiPokok + tunjanganAnak + tunjanganKeluarga + tunjanganUmumJabatan + tunjanganBeras;
  }

  public static double kalkulasiPotonganPPH(int gajiPokok, String statusPernikahan, int gajiKotor, int jumlahAnak, int tunjanganKeluarga, int tunjanganAnak) {

    double potonganPPH = 0;

    int wajibPajak = 4500000;
    int wajibPajakDiriSenridi = 36000000;
    int wajibPajakPasangan = 3000000;
    int wajibPajakPerAnak = 3000000;
    double biayaJabatan = 0.05 * gajiKotor;
    double iuranPensiun = 0.0475 * (gajiPokok + tunjanganKeluarga + tunjanganAnak);

    if (gajiKotor > wajibPajak) {
      double penghasilanNetoPerBulan = gajiKotor - biayaJabatan - iuranPensiun;
      double penghasilanNetoDisetahunkan = penghasilanNetoPerBulan * 12;
      double jumlahPTKP = wajibPajakDiriSenridi + (statusPernikahan.equalsIgnoreCase("Kawin") ? wajibPajakPasangan : 0) + (wajibPajakPerAnak * (jumlahAnak > 3 ? 3 : jumlahAnak));
      double PKP = penghasilanNetoDisetahunkan - jumlahPTKP;
      double pph21Setahun = 0.05 * PKP;

      potonganPPH = pph21Setahun / 12;
    }

    return potonganPPH;
  }

  public static double kalkulasiPotonganIWP(double gajiPokok, double tunjanganAnak, double tunjanganKeluarga) {
    return 0.1 * (gajiPokok + tunjanganAnak + tunjanganKeluarga);
  }

  public static double kalkulasiPotonganTaperum(int golonganPangkat) {
    double potonganTaperum = 0;
    int[] listPotonganTaperum = {3000, 5000, 7000, 10000};

    for (int i = 0; i < listPotonganTaperum.length; i++) {
      if (golonganPangkat == i + 1) {
        potonganTaperum = listPotonganTaperum[i];
        break;
      }
    }

    return potonganTaperum;
  }

  public static double kalkulasiGajiBersih(int gajiKotor, int potonganPPH, double potonganIWP, double potonganTaperum) {
    return gajiKotor - potonganPPH - potonganIWP - potonganTaperum;
  }

  public static void displayOutput(String nama, int gajiPokok, int tunjanganKeluarga, int tunjanganAnak, int tunjanganBeras, int tunjanganUmumJabatan, int gajiKotor, int potonganPPH, double potonganIWP, int potonganTaperum, double gajiBersih) {
    System.out.println("|=================================================|");
    System.out.println("|=============== Slip Gaji PNS ===================|");
    System.out.println("|=================================================|");
    System.out.println("   Nama Pegawai           : " + nama);
    System.out.println("   Gaji Pokok             : " + formatDecimal(gajiPokok));
    System.out.println("   Tunjangan Keluarga     : " + (tunjanganKeluarga != 0 ? formatDecimal(tunjanganKeluarga) : 0));
    System.out.println("   Tunjangan Anak         : " + (tunjanganAnak != 0 ? formatDecimal(tunjanganAnak) : 0));
    System.out.println("   Tunjangan Beras        : " + formatDecimal(tunjanganBeras));
    System.out.println("   Tunjangan Umum Jabatan : " + formatDecimal(tunjanganUmumJabatan));
    System.out.println("   Gaji Kotor             : " + formatDecimal(gajiKotor));
    System.out.println("   Potongan PPH           : " + (potonganPPH != 0 ? formatDecimal(potonganPPH) : 0));
    System.out.println("   Potongan IWP           : " + formatDecimal(potonganIWP));
    System.out.println("   Potongan Taperum       : " + formatDecimal(potonganTaperum));
    System.out.println("   Gaji Bersih            : " + formatDecimal(gajiBersih));
    System.out.println("|=================================================|");
  }
}
