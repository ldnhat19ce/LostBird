package com.ldnhat.Utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

public class TimeAgo {
    
    private String handleTime(Timestamp time){

        String result = "";

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        long countTime = time.getTime();
        Date date = new Date();
        long currentTime = date.getTime();

        long second = (currentTime - countTime);
        int minute = Math.round(second / 60);
        int hours = Math.round(second / 3600);
        int months = Math.round(second / 2600640);

        if(second <= 60){
            if (second == 0){
                result = "now";
            }else{
                result = second + "s";
            }
        }else if(minute <= 60){
            result = minute+"m";
        }else if(hours <= 24){
            result = hours+"h";
        }else if(months <= 12){
            result = "s";
        }else{
            result = "sd";
        }
        return result;
    }
}
