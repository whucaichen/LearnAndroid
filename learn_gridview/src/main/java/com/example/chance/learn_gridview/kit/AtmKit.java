package com.example.chance.learn_gridview.kit;

import android.util.Log;

import java.util.Random;

/**
 * Created by Chance on 2016/7/4.
 */
public class AtmKit {
    final static String TAG = "LOG_ATM_KIT";

    public static void initATM() {
        Log.e(TAG, "initATM");
        Log.e(TAG, "正在对ATM进行初始化操作……");
    }

    public static boolean prepareCash(String account, int cash) {
        Log.e(TAG, "prepareCash");
        Log.e(TAG, "配钞，正在检查账户" + account + "余额是否超过" + cash + "元");
        return cash<1001;
    }

    public static boolean popoutCash(String account, int cash) {
        Log.e(TAG, "popoutCash");
        Log.e(TAG, "出钞，已为账户" + account + "准备好现金" + cash + "元");
        return true;
    }

    public static boolean fetchCash() {
        Log.e(TAG, "fetchCash");
        Log.e(TAG, "取款成功！");
        return true;
    }

    public static String getInfo(String account) {
        Log.e(TAG, "getInfo");
        return "账户余额为1111.11元";
    }

    public static String getInfoDetail(String account, String date) {
        Log.e(TAG, "getInfo");
        String info = "账户" + account + "从" + date + "开始的交易记录为：\n" +
                "29号，入账11111元\n" + "4号，支出5555元\n" +
                "14号，支出5555元\n" + getInfo(account);
        return info;
    }

    public static boolean verifyTransInfo(String cardNum, String name) {
        Log.e(TAG, "verifyTransInfo");
        Log.e(TAG, "正在验证账号为" + cardNum + "的用户姓名是否为" + name);
        return true;
    }

    public static boolean doTransfer(String from, String to, int cash) {
        Log.e(TAG, "doTransfer");
        Log.e(TAG, "你将要从" + from + "账户转账" + cash + "元给" + to);
        return true;
    }

    public static void otherBiz() {
        Log.e(TAG, "otherBiz");
    }

    public static boolean receiveCard() {
        Log.e(TAG, "receiveCard");
        return true;
    }

    public static String getCardNum() {
        Log.e(TAG, "getCardNum");
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 16; i++) {
            result += random.nextInt(10);
        }
        Log.e(TAG, "插入的银行卡号为：" + result);
        return result;
    }

    public static boolean verifyInfo(String account, String password) {
        Log.e(TAG, "verifyInfo");
        Log.e(TAG, "验证信息，账户：" + account + "，密码：" + password);
        return password.equals("123456");
    }

    public static void popoutCard() {
        Log.e(TAG, "popoutCard");
        Log.e(TAG, "银行卡已经退出");
    }

    public static void handlError() {
        Log.e(TAG, "handlError");
    }

}
