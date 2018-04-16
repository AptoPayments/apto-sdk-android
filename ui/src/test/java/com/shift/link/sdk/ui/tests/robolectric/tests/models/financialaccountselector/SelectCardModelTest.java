package com.shift.link.sdk.ui.tests.robolectric.tests.models.financialaccountselector;

import android.text.TextUtils;

import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.mocks.answers.textutils.IsEmptyAnswer;
import com.shift.link.sdk.ui.models.financialaccountselector.SelectCardModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests the {@link SelectCardModel} class.
 * @author Adrian
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TextUtils.class })
public class SelectCardModelTest {

    private SelectCardModel mModel;
    private Card mCard;
    private static final String EXPECTED_ACCOUNT_ID = "123";
    private static final String EXPECTED_EXPIRATION_DATE = "01/99";
    private static final String EXPECTED_LAST_FOUR_DIGITS = "4444";
    private static final Card.CardNetwork EXPECTED_CARD_TYPE = Card.CardNetwork.AMEX;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new IsEmptyAnswer());
        mCard = new Card(EXPECTED_ACCOUNT_ID, EXPECTED_LAST_FOUR_DIGITS, EXPECTED_CARD_TYPE, null,
                null, EXPECTED_EXPIRATION_DATE, null, null,  null, null, null, false);
        mModel = new SelectCardModel(mCard);
    }

    /**
     * Given an empty Model.<br />
     * When fetching the description.<br />
     * Then the correct description should be returned.
     */
    @Test
    public void hasCorrectDescriptionResource() {
        Assert.assertThat("Description should be populated.", mModel.getDescription(), not(nullValue()));
        Assert.assertThat("Description should card type.", mModel.getDescription(), containsString(EXPECTED_CARD_TYPE.name()));
        Assert.assertThat("Description should contain last four digits.", mModel.getDescription(), containsString(EXPECTED_LAST_FOUR_DIGITS));
    }

    /**
     * Given an empty Model.<br />
     * When fetching the financial account.<br />
     * Then the card should be returned.
     */
    @Test
    public void returnsCorrectFinancialAccount() {
        Assert.assertThat("Financial account should be stored.", mModel.getFinancialAccount(), not(nullValue()));
        Assert.assertThat("Card is not correct.", mModel.getFinancialAccount(), equalTo(mCard));
    }

    /**
     * Given an empty Model.<br />
     * When fetching the icon resource ID.<br />
     * Then the correct ID should be returned.
     */
    @Test
    public void hasCorrectTitleResource() {
        Assert.assertThat("Incorrect resource ID.",
                mModel.getIconResourceId(),
                equalTo(R.drawable.icon_amex));
    }
}
