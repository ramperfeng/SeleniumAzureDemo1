package web.UI.automation.helper;

import java.util.Calendar;

public class CommonFunctions {

    public static String getRandomString(int n) {
        String alphaNum = "abcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (alphaNum.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(alphaNum
                    .charAt(index));
        }
        return sb.toString();
    }

    public static String getRandomNumber(int n) {
        String alphaNum = "0123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (alphaNum.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(alphaNum
                    .charAt(index));
        }
        return sb.toString();
    }

    /**************************************
     * Function Name: getCurrentAndReturnDates
     * Author: Kavitha
     * Created Date: 2019-07-10
     * Purpose: Get the current date and another date that is x(from property file) days from today
     * Prerequisites: numeric value for x
     * Change History:
     * getCurrentAndReturnDates() should be called to assign the values to departureDate and returnDate
     *
     **************************************/
    public static String departureDate;
    public static String returnDate;

    public static CommonFunctions getCurrentAndReturnDates() {
        CommonFunctions date = new CommonFunctions();
        Calendar cal=Calendar.getInstance();

        cal.add(Calendar.DATE, 1); // please delete this line. added for test the tomorrow date.

        String[] dateArr=cal.getTime().toString().split(" ");
        CommonFunctions.departureDate=dateArr[0]+" "+dateArr[1]+" "+dateArr[2]+" "+dateArr[5];
        cal.add(Calendar.DATE, Integer.parseInt("2"));
        dateArr=cal.getTime().toString().split(" ");
        CommonFunctions.returnDate=dateArr[0]+" "+dateArr[1]+" "+dateArr[2]+" "+dateArr[5];
        return date;
    }

}
