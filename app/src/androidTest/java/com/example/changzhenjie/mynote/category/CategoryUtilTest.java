package com.example.changzhenjie.mynote.category;

import android.content.Context;
import android.graphics.Color;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.changzhenjie.mynote.categories.CategoryUtils;
import com.example.changzhenjie.mynote.entity.BillNoteType;

/**
 * Created by changzhenjie on 11/10/15.
 */
public class CategoryUtilTest extends AndroidTestCase {
    private Context context;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        context = getContext();
        BillNoteType noteType = new BillNoteType();
        noteType.noteTypeName = "娱乐";
        noteType.noteTypeColor= Color.BLUE;
        noteType.inOrOut = 0;
        noteType.isMainType = true;
        noteType.noteTypeCode = 100;
        noteType.save(context);
        BillNoteType noteType2 = new BillNoteType();
        noteType2.noteTypeName = "交通";
        noteType2.noteTypeColor=Color.BLUE;
        noteType2.inOrOut = 1;
        noteType2.isMainType = false;
        noteType2.noteTypeCode = 101;
        noteType2.parentTypeCode = 100;
        noteType2.save(context);
        BillNoteType noteType3 = new BillNoteType();
        noteType3.noteTypeName = "交通";
        noteType3.noteTypeColor=Color.BLUE;
        noteType3.inOrOut = 1;
        noteType3.isMainType = false;
        noteType3.noteTypeCode = 102;
        noteType3.parentTypeCode = 100;
        noteType3.save(context);
        BillNoteType noteType4 = new BillNoteType();
        noteType4.noteTypeName = "娱乐";
        noteType4.noteTypeColor=Color.BLUE;
        noteType4.inOrOut = 0;
        noteType4.isMainType = true;
        noteType4.noteTypeCode = 200;
        noteType4.save(context);
    }

    @SmallTest
    public void testGenerateSubTypeCode(){
        int mainTypeCode = CategoryUtils.generateSubTypeCode(100, context);
        assertEquals(mainTypeCode,103);
    }
    @SmallTest
    public void testGenerateMailTypeCode(){
        int mainTypeCode = CategoryUtils.generateMainTypeCode(context);
        assertEquals(mainTypeCode,300);
    }
    @SmallTest
    public void testCheckNoteTypeValid(){
        BillNoteType noteType = new BillNoteType();
        noteType.noteTypeCode = 101;
        boolean isvalid = CategoryUtils.checkNoteTypeIsValid(noteType,context);
        assertTrue(!isvalid);
    }
}
