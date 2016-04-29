package me.ledge.link.sdk.ui.tests.robolectric.tests.models.loanapplication.documents;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationActionVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AbstractAddDocumentModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddOtherDocumentModel;
import me.ledge.link.sdk.ui.vos.DocumentVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link AbstractAddDocumentModel} class.
 * @author Wijnand
 */
public class AbstractAddDocumentModelTest {

    private static final String EXPECTED_EMPTY_DESCRIPTION = "";
    private static final String EXPECTED_DESCRIPTION = "All your base are belong to us.";

    private AbstractAddDocumentModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        LoanApplicationActionVo action = new MockApiWrapper().createLoanApplication(0).required_actions.data[0];
        action.message = EXPECTED_DESCRIPTION;

        mModel = new AddOtherDocumentModel(action);
    }

    /**
     * Given no pending loan application action.<br />
     * When creating any new {@link AbstractAddDocumentModel}.<br />
     * Then the description should be empty.
     */
    @Test
    public void emptyDescription() {
        mModel = new AddOtherDocumentModel(null);
        Assert.assertThat("Incorrect description", mModel.getDescription(), equalTo(EXPECTED_EMPTY_DESCRIPTION));
    }

    /**
     * Given a pending loan application action.<br />
     * When creating any new {@link AbstractAddDocumentModel}.<br />
     * Then the description should be set.
     */
    @Test
    public void descriptionIsCorrect() {
        Assert.assertThat("Incorrect description", mModel.getDescription(), equalTo(EXPECTED_DESCRIPTION));
    }

    /**
     * Given a new {@link AbstractAddDocumentModel}.<br />
     * When not having added any documents.<br />
     * Then the Model should be flagged as such.
     */
    @Test
    public void noDocuments() {
        Assert.assertFalse("No documents should be available.", mModel.hasDocument());
    }

    /**
     * Given a new {@link AbstractAddDocumentModel}.<br />
     * When adding a new document.<br />
     * Then the Model should be flagged as containing documents.
     */
    @Test
    public void documentIsAdded() {
        mModel.addDocument(new DocumentVo(null, null));
        Assert.assertTrue("A document should be stored.", mModel.hasDocument());
    }
}
