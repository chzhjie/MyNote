package com.example.changzhenjie.mynote.category;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.changzhenjie.mynote.categories.CategoryUtils;
import com.example.changzhenjie.mynote.provider.TestProviderUtil;

/**
 * Created by changzhenjie on 11/10/15.
 */
public class CategoryUtilTest extends AndroidTestCase {
    private Context context;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        context = getContext();
    }

    @SmallTest
    public void testGenerateMailTypeCode(){
        TestProviderUtil.setupBillNoteType2(context);
        int mainTypeCode = CategoryUtils.generateMainTypeCode(context);
        assertEquals(mainTypeCode,300);
    }
}
