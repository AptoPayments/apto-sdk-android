package me.ledge.link.sdk.ui.tests.robolectric.tests.models.loanapplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.loanapplication.AddDocumentsListActivity;
import me.ledge.link.sdk.ui.models.loanapplication.BigButtonModel;
import me.ledge.link.sdk.ui.models.loanapplication.PendingDocumentsModel;

import static org.hamcrest.CoreMatchers.equalTo;

public class PendingDocumentsModelTest {

    private PendingDocumentsModel mModel;

    @Before
    public void setUp() {
        // TODO
        mModel = new PendingDocumentsModel(null);
    }

    @Test
    public void hasCorrectCloudImageResource() {
        Assert.assertThat(
                "Incorrect image resource.",
                mModel.getCloudImageResource(),
                equalTo(R.drawable.icon_cloud_arrow_up));
    }

    @Test
    public void hasCorrectButtonModel() {
        BigButtonModel buttonModel = mModel.getBigButtonModel();

        Assert.assertTrue("Button should be visible.", buttonModel.isVisible());
        Assert.assertThat(
                "Incorrect button label resource.",
                buttonModel.getLabelResource(),
                equalTo(R.string.loan_application_button_documents_pending)
        );
        Assert.assertThat(
                "Incorrect button action.",
                buttonModel.getAction(),
                equalTo(BigButtonModel.Action.UPLOAD_DOCUMENTS)
        );
    }

    @Test
    public void hasCorrectNextActivity() {
        Assert.assertThat(
                "Incorrect next Activity.",
                (Class<AddDocumentsListActivity>) mModel.getNextActivity(null),
                equalTo(AddDocumentsListActivity.class)
        );
    }
}
