package com.example.changzhenjie.mynote.provider;

import android.content.Context;

import com.example.changzhenjie.mynote.entity.BillNote;
import com.example.changzhenjie.mynote.entity.BillNoteType;
import com.example.changzhenjie.mynote.entity.MoneyStoreType;
import com.example.changzhenjie.mynote.entity.NoteContent;
import android.graphics.Color;

import junit.framework.Assert;

import java.util.Date;

/**
 * Created by changzhenjie on 11/5/15.
 */
public class TestProviderUtil extends Assert{
    public TestProviderUtil() {
        // TODO Auto-generated constructor stub
    }

    public static MoneyStoreType setupMoneyStoreType(){
        MoneyStoreType moneyStoreType = new MoneyStoreType();
        moneyStoreType.storeAccountColor = "#AAAAAA";
        moneyStoreType.storeAccountID="6222021703006197394";
        moneyStoreType.storeAccountName="工商银行";
        moneyStoreType.storeAccountMoney=50000;
        moneyStoreType.storeAccountType="银行卡";
        moneyStoreType.storeAccountDesc="123";
        return moneyStoreType;
    }

    public static BillNote setupBillNote(){
        BillNote billNote = new BillNote();
        billNote.billMoney = 247;
        billNote.billType = 0;
        billNote.billMoneyStoreTypeID="1";
        billNote.billMoneyType="支付宝";
        billNote.billTime = new Date();
        billNote.billDesc = "购买VPN";
        return billNote;
    }

    public static BillNoteType setupBillNoteType(){
        BillNoteType noteType = new BillNoteType();
        noteType.noteTypeName = "娱乐";
        noteType.noteTypeColor=Color.BLUE;
        noteType.inOrOut = 0;
        return noteType;
    }

    public static BillNoteType setupBillNoteType2(Context context){
        BillNoteType noteType = new BillNoteType();
        noteType.noteTypeName = "娱乐";
        noteType.noteTypeColor=Color.BLUE;
        noteType.inOrOut = 0;
        noteType.isMainType = true;
        noteType.noteTypeCode = 100;
        noteType.save(context);
        BillNoteType noteType2 = new BillNoteType();
        noteType2.noteTypeName = "交通";
        noteType2.noteTypeColor=Color.BLUE;
        noteType2.inOrOut = 1;
        noteType.isMainType = true;
        noteType.noteTypeCode = 200;
        noteType2.save(context);
        return noteType2;
    }

    private static void assertNoteContentEqual(String caller, NoteContent expect,
                                               NoteContent actual) {
        if (expect == actual) {
            return;
        }

        assertEquals(caller + " mId", expect.mId, actual.mId);
        assertEquals(caller + " mBaseUri", expect.mBaseUri, actual.mBaseUri);
    }
    public static void assertMoneyStoreTypeEqual(String caller, MoneyStoreType expect,
                                                 MoneyStoreType actual) {
        if (expect == actual) {
            return;
        }
        assertNoteContentEqual(caller, expect, actual);
        assertEquals(caller + " moneyStoreID", expect.storeAccountID, actual.storeAccountID);
        assertEquals(caller + " storeAccountName", expect.storeAccountName, actual.storeAccountName);
        assertEquals(caller + " storeAccountColor", expect.storeAccountColor, actual.storeAccountColor);
        assertEquals(caller + " storeAccountType", expect.storeAccountType, actual.storeAccountType);
        assertEquals(caller + " storeAccountMoney", expect.storeAccountMoney, actual.storeAccountMoney);
        assertEquals(caller + " storeAccountDesc", expect.storeAccountDesc, actual.storeAccountDesc);

    }

    public static void assertBillNoteEqual(String caller, BillNote expect,
                                           BillNote actual) {
        if (expect == actual) {
            return;
        }
        assertNoteContentEqual(caller, expect, actual);
        assertEquals(caller + " billMoney", expect.billMoney, actual.billMoney);
        assertEquals(caller + " billTYpe", expect.billType, actual.billType);
        assertEquals(caller + " billStoreID", expect.billMoneyStoreTypeID, actual.billMoneyStoreTypeID);
        assertEquals(caller + " billStoreTYpe", expect.billMoneyType, actual.billMoneyType);
        //assertEquals(caller + " billTime", expect.billTime, actual.billTime);
        assertEquals(caller + " billDesc", expect.billDesc, actual.billDesc);

    }

    public static void assertBillNoteTypeEqual(String caller, BillNoteType expect,
                                               BillNoteType actual) {
        if (expect == actual) {
            return;
        }
        assertNoteContentEqual(caller, expect, actual);
        assertEquals(caller + " noteName", expect.noteTypeName, actual.noteTypeName);
        assertEquals(caller + " noteColor", expect.noteTypeColor, actual.noteTypeColor);
        assertEquals(caller + " inOrOut", expect.inOrOut, actual.inOrOut);

    }
}
