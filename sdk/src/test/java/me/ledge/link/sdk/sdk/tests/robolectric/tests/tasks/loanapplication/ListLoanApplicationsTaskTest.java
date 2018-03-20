package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks.loanapplication;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.sdk.api.vos.requests.base.ListRequestVo;
import me.ledge.link.sdk.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.tasks.loanapplication.ListPendingLoanApplicationsTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

public class ListLoanApplicationsTaskTest {

    private RoboLinkApiTaskWrapper<LoanApplicationsSummaryListResponseVo, ListRequestVo> mTask;

    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new ListPendingLoanApplicationsTask(new ListRequestVo(), new MockApiWrapper(), new MockResponseHandler())
        );
    }

    @Test
    public void apiResponseIsNotEmpty() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }
}
