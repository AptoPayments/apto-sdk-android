package me.ledge.link.sdk.ui.tests.robolectric.tests.models.financialaccountselector;

import android.text.TextUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.mocks.answers.textutils.IsEmptyAnswer;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddBankAccountModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests the {@link AddBankAccountModel} class.
 * @author Adrian
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TextUtils.class })
public class AddBankAccountModelTest {

    private AddBankAccountModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new IsEmptyAnswer());

        mModel = new AddBankAccountModel();
    }

    /**
     * Given an empty Model.<br />
     * When fetching the activity title resource ID.<br />
     * Then the correct ID should be returned.
     */
    @Test
    public void hasCorrectActivityTitleResource() {
        Assert.assertThat("Incorrect resource ID.",
                mModel.getActivityTitleResource(),
                equalTo(R.string.add_financial_account_bank_title));
    }

    /**
     * Given an empty Model.<br />
     * When fetching the title resource ID.<br />
     * Then the correct ID should be returned.
     */
    @Test
    public void hasCorrectTitleResource() {
        Assert.assertThat("Incorrect resource ID.",
                mModel.getTitleResourceId(),
                equalTo(R.string.add_financial_account_bank));
    }

    /**
     * Given an empty Model.<br />
     * When fetching the description resource ID.<br />
     * Then the correct ID should be returned.
     */
    @Test
    public void hasCorrectDescriptionResource() {
        Assert.assertThat("Incorrect resource ID.",
                mModel.getDescription(),
                equalTo(R.string.add_financial_account_bank_description));
    }
}
