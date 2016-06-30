package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks.loanapplication;

import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.tasks.loanapplication.ListLoanApplicationsTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

public class ListLoanApplicationsTaskTest {

    private RoboLinkApiTaskWrapper<LoanApplicationsListResponseVo, ListRequestVo> mTask;

    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new ListLoanApplicationsTask(new ListRequestVo(), new MockApiWrapper(), new MockResponseHandler())
        );
    }

    @Test
    public void apiResponseIsNotEmpty() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }
}
