package com.example.changzhenjie.mynote.categories;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.Snackbar;

import com.example.changzhenjie.mynote.entity.BillNote;
import com.example.changzhenjie.mynote.entity.BillNoteType;
import com.example.changzhenjie.mynote.entity.NoteContent;
import com.example.changzhenjie.mynote.util.LogUtils;

/**
 * Created by changzhenjie on 11/10/15.
 */
public class CategoryUtils {
    private static final String TAG = LogUtils.makeLogTag(CategoryUtils.class);

    private static Object syncLock = new Object();

    /**
     * generate the main Type Code by the type in the dateBase
     *
     * @return mainTypeCode
     */
    public static int generateMainTypeCode(Context context) {
        LogUtils.LOGD(TAG, "-generateMainTypeCode-");
        ContentResolver resolver = context.getContentResolver();
        int mainTypeCode = 0;
        synchronized (syncLock) {
            Cursor c = resolver.query(BillNoteType.CONTENT_URI_GETBYTYPE, null, null, null, null);
            BillNoteType noteType = null;
            if (c.moveToFirst()) {
                noteType = NoteContent.getContent(c, BillNoteType.class);
            }
            if (noteType != null) {
                LogUtils.LOGD(TAG, noteType.toString());
                mainTypeCode = noteType.noteTypeCode + 100;
            } else {
                mainTypeCode = 100;
            }
        }
        LogUtils.LOGD(TAG, "new MainTypeCode = " + mainTypeCode);
        return mainTypeCode;
    }

    /**
     * generate the subTypeCode by the parent Type code and subCode in the datebase
     *
     * @param parentCode
     * @return
     */
    public static int generateSubTypeCode(int parentCode, Context context) {
        LogUtils.LOGD(TAG, "-generateSubTypeCode-");
        ContentResolver resolver = context.getContentResolver();
        int subTypeCode = 0;
        synchronized (syncLock) {
            Cursor c = resolver.query(BillNoteType.CONTENT_URI_GETSUBTYPE, null, null, new String[]{String.valueOf(parentCode)}, null);
            BillNoteType noteType = null;
            if (c.moveToFirst()) {
                noteType = NoteContent.getContent(c, BillNoteType.class);
            }
            if (noteType != null) {
                LogUtils.LOGD(TAG, noteType.toString());
                subTypeCode = noteType.noteTypeCode + 1;
            } else {
                subTypeCode = parentCode + 1;
            }
        }
        LogUtils.LOGD(TAG, "new SubType Code : " + subTypeCode);
        return subTypeCode;
    }

    /**
     * @param noteType
     * @return
     */
    public static boolean checkNoteTypeIsValid(BillNoteType noteType, Context context) {
        boolean isValid = false;
        ContentResolver resolver = context.getContentResolver();
        String selection = BillNoteType.NoteTypeColumns.NOTETYPE_CODE + " = ? OR " + BillNoteType.NoteTypeColumns.NOTETYPE_NAME + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(noteType.noteTypeCode), noteType.noteTypeName};
        Cursor c = resolver.query(BillNoteType.CONTENT_URI_BILLNOTETYPE, null, selection, selectionArgs, null);
        if (c.getCount() != 0) {
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }



}
