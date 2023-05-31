package util;


public class PersianNumberUtil {

    public static String convertNumbersToPersian(String str) {
        return str.replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹");
    }

    public static String convertNumbersToEnglish(String str) {
        return str.replace('۰', '0').replace('٠', '0')
                .replace('۱', '1').replace('١', '1')
                .replace('۲', '2').replace('٢', '2')
                .replace('۳', '3').replace('٣', '3')
                .replace('۴', '4').replace('٤', '4')
                .replace('۵', '5').replace('٥', '5')
                .replace('۶', '6').replace('٦', '6')
                .replace('۷', '7').replace('٧', '7')
                .replace('۸', '8').replace('٨', '8')
                .replace('۹', '9').replace('٩', '9');
    }
}
