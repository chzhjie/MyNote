
package com.example.changzhenjie.mynote.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;

import com.example.changzhenjie.mynote.database.NoteDBHelper;
import com.example.changzhenjie.mynote.entity.Account;
import com.example.changzhenjie.mynote.entity.BillNote;
import com.example.changzhenjie.mynote.entity.BillNoteType;
import com.example.changzhenjie.mynote.entity.BillNoteType.NoteTypeColumns;
import com.example.changzhenjie.mynote.entity.MoneyStoreType;
import com.example.changzhenjie.mynote.entity.NoteContent;
import com.example.changzhenjie.mynote.util.LogUtils;

public class NoteProvider extends ContentProvider {
    public static final String Tag = LogUtils.makeLogTag(NoteProvider.class);

    public static final Object mDatabaseLock = new Object();
    public static final String DATABASE_NAME = "NoteProvider.db";
    private static final int ACCOUNT_BASE = 0x0000;
    private static final int ACCOUNT = ACCOUNT_BASE;
    private static final int ACCOUNT_ID = ACCOUNT_BASE + 1;

    private static final int BILLNOTE_BASE = 0x1000;
    private static final int BILLNOTE = BILLNOTE_BASE;
    private static final int BILLNOTE_ID = BILLNOTE_BASE + 1;

    private static final int MONEYSTORE_BASE = 0x2000;
    private static final int MONEYSTORE = MONEYSTORE_BASE;
    private static final int MONEYSTORE_ID = MONEYSTORE_BASE + 1;

    private static final int NOTETYPE_BASE = 0X3000;
    private static final int NOTETYPE = NOTETYPE_BASE;
    private static final int NOTETYPE_ID = NOTETYPE_BASE + 1;
    //use to get the Max TypeCode by MainTYpe
    private static final int NOTETYPE_GETMAINTYPE = NOTETYPE_BASE + 2;
    //use to get Max typeCode sub type by type
    private static final int NOTETYPE_GETMAXSUBTYPE = NOTETYPE_BASE + 3;

    private static SparseArray<String> TABLE_NAMES;
    private static int BASE_SHIFT = 12;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        SparseArray<String> arrays = new SparseArray<String>(4);
        arrays.put(ACCOUNT_BASE >> BASE_SHIFT, Account.TABLE_NAME);
        arrays.put(MONEYSTORE_BASE >> BASE_SHIFT, MoneyStoreType.TABLE_NAME);
        arrays.put(BILLNOTE_BASE >> BASE_SHIFT, BillNote.TABLE_NAME);
        arrays.put(NOTETYPE_BASE >> BASE_SHIFT, BillNoteType.TABLE_NAME);
        TABLE_NAMES = arrays;
    }

    private SQLiteDatabase mSqliteDataBase;

    public NoteProvider() {

    }

    /**
     * 初始化UriMatcher
     *
     * @param context
     */
    private static void init(Context context) {
        uriMatcher.addURI(NoteContent.AUTHORITY, "account", ACCOUNT);
        uriMatcher.addURI(NoteContent.AUTHORITY, "account/#", ACCOUNT_ID);
        uriMatcher.addURI(NoteContent.AUTHORITY, "billnote", BILLNOTE);
        uriMatcher.addURI(NoteContent.AUTHORITY, "billnote/#", BILLNOTE_ID);
        uriMatcher.addURI(NoteContent.AUTHORITY, "moneystoretype", MONEYSTORE);
        uriMatcher.addURI(NoteContent.AUTHORITY, "moneystoretype/#",
                MONEYSTORE_ID);
        uriMatcher.addURI(NoteContent.AUTHORITY, "billnotetype", NOTETYPE);
        uriMatcher.addURI(NoteContent.AUTHORITY, "billnotetype/#",
                NOTETYPE_ID);
        uriMatcher.addURI(NoteContent.AUTHORITY, "getbytype",
                NOTETYPE_GETMAINTYPE);
        uriMatcher.addURI(NoteContent.AUTHORITY, "getsubtype",NOTETYPE_GETMAXSUBTYPE);
    }

    private static int findMatch(Uri uri, String methodName) {
        int match = uriMatcher.match(uri);
        if (match < 0) {

        } else {
            LogUtils.LOGD(Tag, methodName + " Uri:" + uri + " match is " + match);
        }
        return match;
    }

    /**
     * 根据给定的要查询的列，返回SELECT* 语句
     *
     * @param uiProjection
     * @return
     */
    private static StringBuilder genSelect(String[] uiProjection) {
        final StringBuilder sb = new StringBuilder("SELECT ");
        boolean first = true;
        for (final String colums : uiProjection) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(" " + colums);
        }
        return sb;
    }

    /**
     * 获取所有的账户的sql语句
     *
     * @param uiProjection
     * @return
     */
    private static String getQueryAccountsSql(String[] uiProjection) {
        StringBuilder sb = genSelect(uiProjection);
        sb.append(" FROM " + Account.TABLE_NAME);
        return sb.toString();
    }

    /**
     * 获取所有的MoneyStore的sql语句
     *
     * @param uiProjection
     * @return
     */
    private static String getQueryMoneyStoreSql(String[] uiProjection) {
        StringBuilder sb = genSelect(uiProjection);
        sb.append(" FROM " + MoneyStoreType.TABLE_NAME);
        return sb.toString();
    }

    /**
     * 根据ID从获取MoneyStore的sql语句
     *
     * @param uiProjection
     * @param id
     * @return
     */
    private static String getQueryMoneyStoreSqlByID(String[] uiProjection, String id) {
        StringBuilder sb = genSelect(uiProjection);
        sb.append(" FROM " + MoneyStoreType.TABLE_NAME + " WHERE _id = " + id);
        LogUtils.LOGD(Tag, "sql : " + sb.toString());
        return sb.toString();
    }

    /**
     * 获得BillNotes的sql语句
     *
     * @param uiProjection
     * @return
     */
    private static String getQueryBillNoteSql(String[] uiProjection) {
        StringBuilder sb = genSelect(uiProjection);
        sb.append(" FROM " + BillNote.TABLE_NAME);
        return sb.toString();
    }

    /**
     * 根据ID获取BillNote的sql语句
     *
     * @param uiProjection
     * @param id
     * @return
     */
    private static String getQueryBillNoteSqlById(String[] uiProjection, String id) {
        StringBuilder sb = genSelect(uiProjection);
        sb.append(" FROM " + BillNote.TABLE_NAME + " WHERE _id = " + id);
        LogUtils.LOGD(Tag, "sql : " + sb.toString());
        return sb.toString();
    }

    /**
     * 获取所有BillNoteType的sql语句
     *
     * @param uiProjection
     * @return
     */
    private static String getQueryNoteTypeSql(String[] uiProjection) {
        StringBuilder sb = genSelect(uiProjection);
        sb.append(" FROM " + BillNoteType.TABLE_NAME);
        return sb.toString();
    }

    /**
     * 根据ID获取NoteType的Sql语句
     *
     * @param uiProjection
     * @param id
     * @return
     */
    private static String getQueryNoteTypeSqlById(String[] uiProjection, String id) {
        StringBuilder sb = genSelect(uiProjection);
        sb.append(" FROM " + BillNoteType.TABLE_NAME + " WHERE _id = " + id);
        LogUtils.LOGD(Tag, "sql : " + sb.toString());
        return sb.toString();
    }

    private String getMaxMainNoteTypeSql() {
        String sql = "select * from " + BillNoteType.TABLE_NAME + " where " + NoteTypeColumns.NOTETYPE_CODE + " = ( select max(" + NoteTypeColumns.NOTETYPE_CODE + ") from " + BillNoteType.TABLE_NAME + " where " + NoteTypeColumns.NOTETYPE_ISMAIN + " = 1 )";
        LogUtils.LOGD(Tag,"getNaxMainTypeSql = " + sql);
        return sql;
    }
    private String getMaxSubNoteTypeSql(){
        String sql = "select * from " + BillNoteType.TABLE_NAME + " where " + NoteTypeColumns.NOTETYPE_CODE + " = ( select max(" + NoteTypeColumns.NOTETYPE_CODE + ") from " + BillNoteType.TABLE_NAME + " where " + NoteTypeColumns.NOTETYPE_PARENTCODE + " = ? )";
        LogUtils.LOGD(Tag,"getNaxSubTypeSql = " + sql);
        return sql;
    }

    private static String whereWithId(String id, String selection) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("_id=");
        sb.append(id);
        if (selection != null) {
            sb.append(" AND (");
            sb.append(selection);
            sb.append(')');
        }
        return sb.toString();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = findMatch(uri, "delete");
        Context context = getContext();
        ContentResolver resolver = context.getContentResolver();
        SQLiteDatabase database = getDataBase(context);
        int result = 0;
        switch (match) {
            case ACCOUNT:
                break;
            case ACCOUNT_ID:
                break;
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LogUtils.LOGD(Tag, "insert:" + uri);
        int match = findMatch(uri, "insert");
        Context context = getContext();
        ContentResolver resolver = context.getContentResolver();
        SQLiteDatabase database = getDataBase(context);
        Uri resultUri = null;
        long id;
        switch (match) {
            case ACCOUNT:
                LogUtils.LOGD(Tag, "insert account");
                id = database.insert(Account.TABLE_NAME, null, values);
                resultUri = ContentUris.withAppendedId(uri, id);
                break;
            case MONEYSTORE:
                LogUtils.LOGD(Tag, "insert moneystore");
                id = database.insert(MoneyStoreType.TABLE_NAME, null, values);
                resultUri = ContentUris.withAppendedId(uri, id);
                break;
            case BILLNOTE:
                LogUtils.LOGD(Tag, "insert billnotes");
                id = database.insert(BillNote.TABLE_NAME, null, values);
                resultUri = ContentUris.withAppendedId(uri, id);
                break;
            case NOTETYPE:
                LogUtils.LOGD(Tag, "insert noteType");
                id = database.insert(BillNoteType.TABLE_NAME, null, values);
                resultUri = ContentUris.withAppendedId(uri, id);
                break;

        }
        return resultUri;

    }

    @Override
    public boolean onCreate() {
        Context mContext;
        mContext = getContext();
        NoteContent.init();
        init(mContext);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        int match = 0;
        Context context = getContext();
        SQLiteDatabase database = getDataBase(context);

        match = findMatch(uri, "query");

        int table = match >> BASE_SHIFT;
        String id;
        String tableName = TABLE_NAMES.valueAt(table);

        switch (match) {
            case ACCOUNT:
            case MONEYSTORE:
            case BILLNOTE:
            case NOTETYPE:
                c = database.query(tableName, projection, selection, selectionArgs, null, null, null, null);
                break;

            case ACCOUNT_ID:
            case MONEYSTORE_ID:
            case BILLNOTE_ID:
            case NOTETYPE_ID:
                id = uri.getPathSegments().get(1);
                c = database.query(tableName, projection, whereWithId(id, selection), selectionArgs, null, null, null, null);
                break;
            case NOTETYPE_GETMAINTYPE:
                c = database.rawQuery(getMaxMainNoteTypeSql(),null);
                break;
            case NOTETYPE_GETMAXSUBTYPE:
                c = database.rawQuery(getMaxSubNoteTypeSql(),selectionArgs);
                break;
        }

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int match = findMatch(uri, "update");
        Context context = getContext();
        ContentResolver resolver = context.getContentResolver();
        SQLiteDatabase database = getDataBase(context);
        int result = 0;
        switch (match) {
            case ACCOUNT:
                result = database.update(Account.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case ACCOUNT_ID:

                break;
        }
        return result;

    }

    private SQLiteDatabase getDataBase(Context context) {
        synchronized (mDatabaseLock) {
            if (mSqliteDataBase == null) {
                NoteDBHelper noteDBHelper = new NoteDBHelper(context,
                        DATABASE_NAME);
                mSqliteDataBase = noteDBHelper.getWritableDatabase();
            }
            return mSqliteDataBase;
        }
    }
}
