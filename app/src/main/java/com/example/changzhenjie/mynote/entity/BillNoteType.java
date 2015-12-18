
package com.example.changzhenjie.mynote.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 该类用于表示消费或者收入的类别
 *
 * @author changzhenjie
 */
public class BillNoteType extends NoteContent {
    public static final String TABLE_NAME = "BillNoteType ";

    public static Uri CONTENT_URI_BILLNOTETYPE;
    public static Uri CONTENT_URI_GETBYTYPE;
    public static Uri CONTENT_URI_GETSUBTYPE;

    public String noteTypeName;
    public int noteTypeColor;
    public int inOrOut;
    public int noteTypeCode;
    public boolean isMainType;
    public int parentTypeCode;

    public static final int CONTENT_ID_COLUMN = 0;
    public static final int CONTENT_NOTETYPE_NAME_COLUMN = 1;
    public static final int CONTENT_NOTETYPE_COLOR_COLUMN = 2;
    public static final int CONTENT_NOTETYPE_INOROUT_COLUMN = 3;
    public static final int CONTENT_NOTETYPE_NOTECODE = 4;
    public static final int CONTENT_NOTETYPE_ISMAIN = 5;
    public static final int CONTENT_NOTETYPE_PARENTCODE = 6;

    public String getNoteTypeName() {
        return noteTypeName;
    }

    public void setNoteTypeName(String noteTypeName) {
        this.noteTypeName = noteTypeName;
    }

    public int getNoteTypeColor() {
        return noteTypeColor;
    }

    public void setNoteTypeColor(int noteTypeColor) {
        this.noteTypeColor = noteTypeColor;
    }

    public int getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(int inOrOut) {
        this.inOrOut = inOrOut;
    }

    public boolean isMainType() {
        return isMainType;
    }

    public void setIsMainType(boolean isMainType) {
        this.isMainType = isMainType;
    }

    public int getNoteTypeCode() {
        return noteTypeCode;
    }

    public void setNoteTypeCode(int noteTypeCode) {
        this.noteTypeCode = noteTypeCode;
    }

    public int getParentTypeCode() {
        return parentTypeCode;
    }

    public void setParentTypeCode(int parentTypeCode) {
        this.parentTypeCode = parentTypeCode;
    }


    public BillNoteType() {
        mBaseUri = CONTENT_URI_BILLNOTETYPE;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(NoteTypeColumns.NOTETYPE_NAME, noteTypeName);
        values.put(NoteTypeColumns.NOTETYPE_COLOR, noteTypeColor);
        values.put(NoteTypeColumns.NOTETYPE_INOROUR, inOrOut);
        values.put(NoteTypeColumns.NOTETYPE_CODE, noteTypeCode);
        values.put(NoteTypeColumns.NOTETYPE_ISMAIN, isMainType ? 1 : 0);
        values.put(NoteTypeColumns.NOTETYPE_PARENTCODE, parentTypeCode);
        return values;
    }

    @Override
    public void restore(Cursor cursor) {
        mId = cursor.getLong(CONTENT_ID_COLUMN);
        noteTypeName = cursor.getString(CONTENT_NOTETYPE_NAME_COLUMN);
        noteTypeColor = cursor.getInt(CONTENT_NOTETYPE_COLOR_COLUMN);
        inOrOut = cursor.getInt(CONTENT_NOTETYPE_INOROUT_COLUMN);
        noteTypeCode = cursor.getInt(CONTENT_NOTETYPE_NOTECODE);
        isMainType = cursor.getInt(CONTENT_NOTETYPE_ISMAIN) == 1 ? true : false;
        parentTypeCode = cursor.getInt(CONTENT_NOTETYPE_PARENTCODE);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (!TextUtils.isEmpty(noteTypeName)) {
            sb.append("noteTypeName : ").append(noteTypeName);
        }
        if (noteTypeColor != 0) {
            sb.append("noteTypeColor : ").append(noteTypeColor);
        }
        sb.append("NoteCode : ").append(noteTypeCode);
        sb.append("isMain : ").append(isMainType);
        sb.append("is in or out :").append(inOrOut);
        return sb.toString();
    }

    /**
     * 对BillNoteTYpe相关的变量进行初始化
     */
    public static void initBillNotes() {
        CONTENT_URI_BILLNOTETYPE = Uri.parse(CONTENT_URI + "/billnotetype");
        CONTENT_URI_GETBYTYPE = Uri.parse(CONTENT_URI + "/getbytype");
        CONTENT_URI_GETSUBTYPE = Uri.parse(CONTENT_URI + "/getsubtype");
    }

    public interface NoteTypeColumns {
        public static final String _ID = "_id";
        public static final String NOTETYPE_NAME = "noteTypeName";
        public static final String NOTETYPE_COLOR = "noteTypeColor";
        public static final String NOTETYPE_INOROUR = "noteTypeInorOut";
        public static final String NOTETYPE_CODE = "noteTypeCode";
        public static final String NOTETYPE_ISMAIN = "isMainType";
        public static final String NOTETYPE_PARENTCODE = "parentNoteCode";
    }

    public static final String[] CONTENT_PROJECTION = {
            NoteTypeColumns._ID, NoteTypeColumns.NOTETYPE_NAME, NoteTypeColumns.NOTETYPE_COLOR, NoteTypeColumns.NOTETYPE_INOROUR
    };

    /**
     * 根据ID获取BIllNoteType
     *
     * @param context
     * @param id
     * @return
     */
    public static BillNoteType restoreBillNoteWithID(Context context, long id) {
        return restoreBillNoteWithID(context, id, null);
    }

    /**
     * 根据ID获取BIllNoteType
     *
     * @param context
     * @param id
     * @return
     */
    public static BillNoteType restoreBillNoteWithID(Context context, long id, ContentObserver observer) {
        return NoteContent.restoreContentWithID(context, BillNoteType.class,
                BillNoteType.CONTENT_URI_BILLNOTETYPE, BillNoteType.CONTENT_PROJECTION, id);
    }
}
