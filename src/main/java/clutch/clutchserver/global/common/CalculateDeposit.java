package clutch.clutchserver.global.common;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CalculateDeposit {

    public int calculate(int deposit, String address, LocalDateTime confirmDate) {
        // Your existing logic to calculate the deposit based on the provided parameters.
        // You can continue the implementation of this method here.
        // ...
        String region;
        // Convert the address to lowercase for case-insensitive comparison
        String lowerAddress = address.toLowerCase();
        int firstPayment = 0;


        LocalDateTime date1 = LocalDateTime.of(2022, 2, 14, 0, 0);
        LocalDateTime date2 = LocalDateTime.of(2021, 5, 11, 0, 0);
        LocalDateTime date3 = LocalDateTime.of(2018, 9, 18, 0, 0);
        LocalDateTime date4 = LocalDateTime.of(2016, 3, 31, 0, 0);

        boolean has_smallLease=false;

        if (confirmDate.isAfter(date1)) {
            // After 2022-02-14
            if (lowerAddress.contains("서울")) {
                if(deposit<=165000000){
                    firstPayment = 55000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if(deposit<=145000000){
                    firstPayment = 48000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if(deposit<=85000000){
                    firstPayment = 28000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if(deposit<=145000000){
                    firstPayment = 48000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else {
                if(deposit<=75000000){
                    firstPayment = 25000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            }
        } else if (confirmDate.isAfter(date2)) {
            // After 2021-05-11

            if (lowerAddress.contains("서울")) {
                if(deposit<=150000000){
                    firstPayment = 50000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if(deposit<=130000000){
                    firstPayment = 43000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if(deposit<=70000000){
                    firstPayment = 23000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if(deposit<=130000000){
                    firstPayment = 43000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else {
                if(deposit<=60000000){
                    firstPayment = 20000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            }

        } else if (confirmDate.isAfter(date3)) {
            // After 2018-09-18

            if (lowerAddress.contains("서울")) {
                if(deposit<=110000000){
                    firstPayment = 37000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if(deposit<=100000000){
                    firstPayment = 34000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if(deposit<=60000000){
                    firstPayment = 20000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if(deposit<=100000000){
                    firstPayment = 34000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else {
                if(deposit<=50000000){
                    firstPayment = 17000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            }

        } else if (confirmDate.isAfter(date4)) {
            // After 2016-03-31
            if (lowerAddress.contains("서울")) {
                if(deposit<=100000000){
                    firstPayment = 34000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("광역시")) {
                if(deposit<=80000000){
                    firstPayment = 27000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("안산") || lowerAddress.contains("광주") || lowerAddress.contains("파주") || lowerAddress.contains("이천")) {
                if(deposit<=60000000){
                    firstPayment = 20000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else if (lowerAddress.contains("세종") || lowerAddress.contains("용인") || lowerAddress.contains("화성") || lowerAddress.contains("김포")) {
                if(deposit<=80000000){
                    firstPayment = 27000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            } else {
                if(deposit<=50000000){
                    firstPayment = 17000000;
                    has_smallLease = true;
                } else{
                    firstPayment = 0;
                    has_smallLease = false;
                }
            }


        } else {
            // For dates before 2016-03-31
            firstPayment = 0;
            has_smallLease =false;
        }


        // Your logic to calculate the deposit based on the region, year, and deposit value.
        // ...

        return firstPayment;
    }


}
