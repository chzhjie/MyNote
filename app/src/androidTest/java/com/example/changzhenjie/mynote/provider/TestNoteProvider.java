package com.example.changzhenjie.mynote.provider;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.changzhenjie.mynote.entity.BillNote;
import com.example.changzhenjie.mynote.entity.BillNoteType;
import com.example.changzhenjie.mynote.entity.MoneyStoreType;
import com.example.changzhenjie.mynote.entity.NoteContent;

import junit.framework.Test;

/**
 * Created by changzhenjie on 11/5/15.
 */
public class TestNoteProvider extends ProviderTestCase2<NoteProvider> {

    private MockContentResolver mMockContentResolver;
    private SQLiteDatabase mSqLiteDatabase;
    private NoteProvider noteProvider;
    private Context mMockContext;

    public TestNoteProvider() {
        super(NoteProvider.class, NoteContent.AUTHORITY);
    }
    /**
     * Private context wrapper used to add back getPackageName() for these tests.
     */
    private static class MockContext2 extends ContextWrapper {

        private final Context mRealContext;

        public MockContext2(Context mockContext, Context realContext) {
            super(mockContext);
            mRealContext = realContext;
        }

        @Override
        public Context getApplicationContext() {
            return this;
        }

        @Override
        public String getPackageName() {
            return mRealContext.getPackageName();
        }

        @Override
        public Object getSystemService(String name) {
            return mRealContext.getSystemService(name);
        }
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        mMockContentResolver = getMockContentResolver();
        noteProvider = getProvider();
        mMockContext = new MockContext2(getMockContext(), getContext());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @SmallTest
    public void testMoneyStoreTypeSave(){
        MoneyStoreType moneyStoreType = TestProviderUtil.setupMoneyStoreType();
        moneyStoreType.save(mMockContext);
        long moneyStoreId = moneyStoreType.mId;
        MoneyStoreType moneyStoreType2 = MoneyStoreType.restoreMoneyStoreTypeWithID(mMockContext, moneyStoreId);
        TestProviderUtil.assertMoneyStoreTypeEqual("testMoneyStoreSave:",moneyStoreType,moneyStoreType2);
    }

    @SmallTest
    public void testBillNoteSave(){
        BillNote billNote = TestProviderUtil.setupBillNote();
        billNote.save(mMockContext);
        long billNoteID = billNote.mId;
        BillNote billNote2 = BillNote.restoreBillNoteWithID(mMockContext, billNoteID);
        TestProviderUtil.assertBillNoteEqual("testBillNoteSave", billNote, billNote2);
    }

    @SmallTest
    public void testBillNoteTypeSave(){
        BillNoteType noteType = TestProviderUtil.setupBillNoteType();
        noteType.save(mMockContext);
        long noteTypeId = noteType.mId;
        BillNoteType noteType2 = BillNoteType.restoreBillNoteWithID(mMockContext, noteTypeId);
        TestProviderUtil.assertBillNoteTypeEqual("testBillNoteSave", noteType, noteType2);
    }

    @SmallTest
    public void testNoteTypeQuery(){
        BillNoteType noteType = TestProviderUtil.setupBillNoteType2(mMockContext);
        String selectionString = BillNoteType.NoteTypeColumns.NOTETYPE_INOROUR + " = ?";
        Cursor cursor = mMockContentResolver.query(BillNoteType.CONTENT_URI_BILLNOTETYPE, BillNoteType.CONTENT_PROJECTION,selectionString ,new String[]{"1"}, null);
        int size = cursor.getCount();
        assertEquals(size, 1);
    }
}
