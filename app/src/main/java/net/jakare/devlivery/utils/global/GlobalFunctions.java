package net.jakare.devlivery.utils.global;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andresvasquez on 10/2/15.
 */
public class GlobalFunctions {
    final static String LOG = GlobalFunctions.class.getName();
    public final static String DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    public final static String DEFAULT_USA_DATE_FORMAT="MM-dd-yyyy HH:mm";
    public final static String DEFAULT_INVENTORY_LIST_FORMAT="h:mm a";

    /***
     * Hide KeyBoard
     * @param view View of Parent
     * @param context
     */
    public static final void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * ------------------------------------
     * Date & Time Functions
     * ------------------------------------
     */

    /**
     * Get date in milisecons Timestamp
     * @return
     */
    public static long getDateMiliseconds()
    {
        return new Date().getTime();
    }

    /**
     * Get Date from Milisecons
     * @param milisecons
     * @return
     */
    public static String getInventoryListDate(long milisecons)
    {
        String strResult="";
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_INVENTORY_LIST_FORMAT);
        strResult = sdf.format(new Date(milisecons));
        return strResult;
    }

    /***
     * Get phone String Date
     * @return
     */
    public static String getDate()
    {
        String strResult="";
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        strResult = sdf.format(new Date());
        return strResult;
    }


    /***
     * Get String date with format
     * @param format String with format ex: yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDate(String format)
    {
        String strResult="";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        strResult = sdf.format(new Date());
        return strResult;
    }

    /**
     * Get date from String in default format
     * @param strDate
     * @return
     */
    public static Date getCustomDate(String strDate)
    {
        String strCurrentDate = strDate;
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date newDate = null;
        try
        {
            newDate = format.parse(strCurrentDate);
            return newDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG, e.getMessage());
        }
        return null;
    }

    /**
     * Get date from String in specific format
     * @param strDate
     * @param strFormat
     * @return
     */
    public static Date getCustomDate(String strDate,String strFormat)
    {
        String strCurrentDate = strDate;
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        Date newDate = null;
        try
        {
            newDate = format.parse(strCurrentDate);
            return newDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG, e.getMessage());
        }
        return null;
    }

    /**
     * Get Milisecons from Date
     * @param strDate
     * @param strFormat
     * @return
     */
    public static long getCustomDateMilisecs(String strDate,String strFormat)
    {
        String strCurrentDate = strDate;
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        Date newDate = null;
        try
        {
            newDate = format.parse(strCurrentDate);
            return newDate.getTime();
        }
        catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG, e.getMessage());
        }
        return 0;
    }

    /**
     * Get custom date for show results
     * @param timestamp
     * @param strFormat
     * @return
     */
    public static String getCustomDateFromTimestamp(long timestamp,String strFormat)
    {
        String strResult="";
        try
        {
            Date newDate = new Date(timestamp);
            SimpleDateFormat format = new SimpleDateFormat(strFormat);
            strResult = format.format(newDate);
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.e(LOG, ""+e.getMessage());
        }
        return strResult;
    }



    /**
     *  Get String from Date in default format
     * @param date
     * @return
     */
    public static String dateToString(Date date)
    {
        String strResult="";
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        strResult = format.format(date);
        return strResult;
    }

    public static String intToDate(int year,int month,int day,int hour,int minute){
        String strResult="";
        strResult=year+"-"+format2Digits(month)+"-"+format2Digits(day)+" "+format2Digits(hour)+":"+format2Digits(minute)+":"+"00";
        return strResult;
    }

    /**
     * Change date format
     * @param inputFormat String with format ex: yyyy-MM-dd HH:mm:ss
     * @param outputFormat String with format ex: yyyy-dd-MM HH:mm:ss
     * @param date Date in string
     * @return
     */
    public static String changeDateFormat(String inputFormat, String outputFormat, String date)
    {
        String strCurrentDate = date;
        SimpleDateFormat format = new SimpleDateFormat(inputFormat);
        Date newDate = null;
        try
        {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat(outputFormat);
            String outDate = format.format(newDate);
            return outDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG, e.getMessage());
        }
        return "";
    }

    /***
     * Get date from String
     * @param inputFormat String with format ex: yyyy-dd-MM HH:mm:ss
     * @param date Date in String
     * @return
     */
    public static Date dateFromString(String inputFormat, String date)
    {
        String strCurrentDate = date;
        SimpleDateFormat format = new SimpleDateFormat(inputFormat);
        Date newDate = null;
        try
        {
            newDate = format.parse(strCurrentDate);
            return newDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG, e.getMessage());
        }
        return null;
    }

    /**
     * Check if date is valid with specific format
     * @param inDate
     * @param format
     * @return
     */
    public static boolean isValidDate(String inDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }



    /**
     * ------------------------------------
     * Other Functions
     * ------------------------------------
     */

    /**
     * Put 2 decimal places
     * @param value
     * @return
     */
    public static String format2Digits(int value)
    {
        return String.format("%02d", value);
    }

    /**
     * Get only 2 decimals from double value
     * @param value
     * @return
     */
    public static String format2Digits(double value)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return String.valueOf(df.format(value));
    }

    /**
     * Copy Raw Resource to SD path
     * @param context
     * @param res
     * @param path
     * @return
     */
    public static boolean copyRawToSD(Context context, int res, String path) {
        boolean result=false;
        try
        {
            InputStream in = context.getResources().openRawResource(res);
            FileOutputStream out = new FileOutputStream(path);
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG+".copyRawToSD",""+e.getMessage());
        }
        return result;
    }

    /**
     * Converts boolean to int to save in database
     * @param value
     * @return
     */
    public static int booleanToInt(boolean value){
        if(value){
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     * Converst int to boolean to get database boolean data
     * @param value
     * @return
     */
    public static boolean intToBoolean(int value){
        if(value==1){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Get double from EditText
     * @param txtField
     * @return
     */
    public final static double getDouble(EditText txtField) {
        try {
            double value= Double.parseDouble(txtField.getText().toString());
            return value;
        }catch (NumberFormatException ex) {
            return 0;
        }
    }

    public final static int getInt(EditText txtField) {
        try {
            int value= Integer.parseInt(txtField.getText().toString());
            return value;
        }catch (NumberFormatException ex) {
            return 0;
        }
    }
}
