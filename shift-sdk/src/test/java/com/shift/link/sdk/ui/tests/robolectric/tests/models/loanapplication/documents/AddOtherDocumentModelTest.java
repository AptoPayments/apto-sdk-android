package com.shift.link.sdk.ui.tests.robolectric.tests.models.loanapplication.documents;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddOtherDocumentModel;
import com.shift.link.sdk.ui.vos.DocumentVo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link AddOtherDocumentModel} class.
 * @author Wijnand
 */
public class AddOtherDocumentModelTest {

    private AddOtherDocumentModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new AddOtherDocumentModel(null);
    }

    /**
     * Given a new {@link AddOtherDocumentModel}.<br />
     * When determining what icon to use on the card.<br />
     * Then the correct icon should be returned.
     */
    @Test
    public void iconIsCorrect() {
        Assert.assertThat("Incorrect icon.",
                mModel.getIconResourceId(), equalTo(R.drawable.icon_add_doc_generic));
    }

    /**
     * Given a new {@link AddOtherDocumentModel}.<br />
     * When no document has been added yet.<br />
     * Then the unchecked state text should be used.
     */
    @Test
    public void uncheckedResourceId() {
        Assert.assertThat("Incorrect unchecked text.",
                mModel.getTitleResourceId(), equalTo(R.string.add_documents_title_unchecked_other));
    }

    /**
     * Given a new {@link AddOtherDocumentModel}.<br />
     * When a document has been added.<br />
     * Then the checked state text should be used.
     */
    @Test
    public void checkedResourceId() {
        mModel.addDocument(new DocumentVo(null, null));

        Assert.assertThat("Incorrect checked text.",
                mModel.getTitleResourceId(), equalTo(R.string.add_documents_title_checked_other));
    }
}
