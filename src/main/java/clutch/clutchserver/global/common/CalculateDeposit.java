package clutch.clutchserver.global.common;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;

@Component
public class CalculateDeposit {

    public int calculate(BigInteger deposit, String address, LocalDate confirmDate) {
        // Your existing logic to calculate the deposit based on the provided parameters.
        // You can continue the implementation of this method here.
        // ...
        String region;
        // Convert the address to lowercase for case-insensitive comparison
        String lowerAddress = address.toLowerCase();
        int firstPayment = 0;


        LocalDate date1 = LocalDate.of(2022, 2, 14);
        LocalDate date2 = LocalDate.of(2021, 5, 11);
        LocalDate date3 = LocalDate.of(2018, 9, 18);
        LocalDate date4 = LocalDate.of(2016, 3, 31);

        boolean hasSmallLease = false;

        if (confirmDate.isAfter(date1)) {
            // After 2022-02-14
            if (lowerAddress.contains("서울")) {
                if (deposit.compareTo(new BigInteger("165000000")) <= 0) {
                    firstPayment = 55000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if (deposit.compareTo(new BigInteger("145000000")) <= 0) {
                    firstPayment = 48000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if (deposit.compareTo(new BigInteger("85000000")) <= 0) {
                    firstPayment = 28000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if (deposit.compareTo(new BigInteger("145000000")) <= 0) {
                    firstPayment = 48000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else {
                if (deposit.compareTo(new BigInteger("75000000")) <= 0) {
                    firstPayment = 25000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            }
        } else if (confirmDate.isAfter(date2)) {
            // After 2021-05-11

            if (lowerAddress.contains("서울")) {
                if (deposit.compareTo(new BigInteger("150000000")) <= 0) {
                    firstPayment = 50000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if (deposit.compareTo(new BigInteger("130000000")) <= 0) {
                    firstPayment = 43000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if (deposit.compareTo(new BigInteger("70000000")) <= 0) {
                    firstPayment = 23000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if (deposit.compareTo(new BigInteger("130000000")) <= 0) {
                    firstPayment = 43000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else {
                if (deposit.compareTo(new BigInteger("60000000")) <= 0) {
                    firstPayment = 20000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            }

        } else if (confirmDate.isAfter(date3)) {
            // After 2018-09-18

            if (lowerAddress.contains("서울")) {
                if (deposit.compareTo(new BigInteger("110000000")) <= 0) {
                    firstPayment = 37000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if (deposit.compareTo(new BigInteger("100000000")) <= 0) {
                    firstPayment = 34000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if (deposit.compareTo(new BigInteger("60000000")) <= 0) {
                    firstPayment = 20000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if (deposit.compareTo(new BigInteger("100000000")) <= 0) {
                    firstPayment = 34000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else {
                if (deposit.compareTo(new BigInteger("50000000")) <= 0) {
                    firstPayment = 17000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            }

        } else if (confirmDate.isAfter(date4)) {
            // After 2016-03-31
            if (lowerAddress.contains("서울")) {
                if (deposit.compareTo(new BigInteger("100000000")) <= 0) {
                    firstPayment = 34000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if (deposit.compareTo(new BigInteger("80000000")) <= 0) {
                    firstPayment = 27000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if (deposit.compareTo(new BigInteger("60000000")) <= 0) {
                    firstPayment = 20000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if (deposit.compareTo(new BigInteger("80000000")) <= 0) {
                    firstPayment = 27000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            } else {
                if (deposit.compareTo(new BigInteger("50000000")) <= 0) {
                    firstPayment = 17000000;
                    hasSmallLease = true;
                } else {
                    firstPayment = 0;
                    hasSmallLease = false;
                }
            }


        } else {
            // For dates before 2016-03-31
            firstPayment = 0;
            hasSmallLease = false;
        }


        // Your logic to calculate the deposit based on the region, year, and deposit value.
        // ...

        return firstPayment;
    }


}
