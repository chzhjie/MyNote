
package com.example.changzhenjie.mynote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.changzhenjie.mynote.entity.Account;
import com.example.changzhenjie.mynote.entity.BillNote;
import com.example.changzhenjie.mynote.entity.BillNote.BillNoteColumns;
import com.example.changzhenjie.mynote.entity.BillNoteType;
import com.example.changzhenjie.mynote.entity.BillNoteType.NoteTypeColumns;
import com.example.changzhenjie.mynote.entity.MoneyStoreType;
import com.example.changzhenjie.mynote.entity.MoneyStoreType.MoneyStoreTypeColumns;
import com.example.changzhenjie.mynote.util.LogUtils;

/**
 * Created by zhenjie on 2015/5/9.
 */
public class NoteDBHelper extends SQLiteOpenHelper {
    public static final String LogTag = LogUtils.makeLogTag(NoteDBHelper.class);
    public static final int DATABASE_VERSION = 1;
    Context mContext;

    public NoteDBHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.LOGD(LogTag, "NoteDBHelper onCreate Table");
        // 创建账户表
        createAccountTable(db);
        // 创建资金存储类型表
        createMoneyStoreTable(db);
        //创建流水帐目表
        createBillNoteTable(db);
        //创建帐目类型表
        createBillNoteTypeTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static void createAccountTable(SQLiteDatabase sqLiteDatabase) {
        LogUtils.LOGD(LogTag, "createAccountTable start");
        String accountColumns = " (" + Account.AccountColumns._ID
                + " integer primary key autoincrement, "
                + Account.AccountColumns.ACCOUNT_AVATAR + " text, "
                + Account.AccountColumns.ACCOUNT_BACKGROUND + " text, "
                + Account.AccountColumns.ACCOUNT_CREATE_TIME + " text, "
                + Account.AccountColumns.ACCOUNT_NAME + " text, "
                + Account.AccountColumns.ACCOUNT_NOTE_COUNT + " integer, "
                + Account.AccountColumns.ACCOUNT_PWD + " text " + " )";
        sqLiteDatabase.execSQL("create table " + Account.TABLE_NAME
                + accountColumns);
        LogUtils.LOGD(LogTag, "createAccountTable finished : " + accountColumns);
    }

    static void createMoneyStoreTable(SQLiteDatabase sqLiteDatabase) {
        LogUtils.LOGD(LogTag, "createMoneyStoreTable start");
        String moneyStoreColumnsString = " ("
                + MoneyStoreType.MoneyStoreTypeColumns._ID
                + " integer primary key autoincrement, "
                + MoneyStoreTypeColumns.MONEY_STORE_ID + " text, "
                + MoneyStoreTypeColumns.MONEY_STORE_NAME + " text, "
                + MoneyStoreTypeColumns.MONEY_STORE_COLOR + " text, "
                + MoneyStoreTypeColumns.MONEY_STORE_TYPE + " text, "
                + MoneyStoreTypeColumns.MONEY_STORE_MONEY + " integer, "
                + MoneyStoreTypeColumns.MONEY_STORE_DESC + " text " + " )";
        sqLiteDatabase.execSQL("create table " + MoneyStoreType.TABLE_NAME
                + moneyStoreColumnsString);
        LogUtils.LOGD(LogTag, "createMoneyStoreTable finished : " + moneyStoreColumnsString);
    }

    static void createBillNoteTable(SQLiteDatabase sqLiteDatabase) {
        LogUtils.LOGD(LogTag, "createBillNoteTable start");
        String billNoteColumnsString = " ("
                + BillNoteColumns._ID
                + " integer primary key autoincrement, "
                + BillNoteColumns.BILL_NOTE_MONEY + " integer, "
                + BillNoteColumns.BILL_NOTE_TYPE + " integer, "
                + BillNoteColumns.BILL_NOTE_PHOTO + " text, "
                + BillNoteColumns.BILL_NOTE_STOREID + " text, "
                + BillNoteColumns.BILL_NOTE_MONEY_TYPE + " text, "
                + BillNoteColumns.BILL_NOTE_TIME + " text, " 
                + BillNoteColumns.BILL_NOTE_DESC + " text " + " )";
        sqLiteDatabase.execSQL("create table " + BillNote.TABLE_NAME
                + billNoteColumnsString);
        LogUtils.LOGD(LogTag, "createBillNoteTable finished : " + billNoteColumnsString);
    }
    
    static void createBillNoteTypeTable(SQLiteDatabase sqLiteDatabase) {
        LogUtils.LOGD(LogTag, "createBillNoteTypeTable start");
        String noteTypeColumnsString = " ("
                + NoteTypeColumns._ID
                + " integer primary key autoincrement, "
                + NoteTypeColumns.NOTETYPE_NAME + " text, "
                + NoteTypeColumns.NOTETYPE_COLOR + " integer, "
                + NoteTypeColumns.NOTETYPE_INOROUR + " integer, "
                + NoteTypeColumns.NOTETYPE_CODE + " integer, "
                + NoteTypeColumns.NOTETYPE_ISMAIN + " integer, "
                + NoteTypeColumns.NOTETYPE_PARENTCODE + " integer " + " )";
        sqLiteDatabase.execSQL("create table " + BillNoteType.TABLE_NAME
                + noteTypeColumnsString);
        LogUtils.LOGD(LogTag, "createBillNoteTypeTable finished : " + noteTypeColumnsString);
    }
}
