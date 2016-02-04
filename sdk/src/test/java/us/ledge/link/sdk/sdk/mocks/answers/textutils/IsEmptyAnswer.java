package us.ledge.link.sdk.sdk.mocks.answers.textutils;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Provides the {@link TextUtils#isEmpty(CharSequence)}'s mock answer.
 * @author Wijnand
 */
public class IsEmptyAnswer implements Answer<Boolean> {

    /** {@inheritDoc} */
    @Override
    public Boolean answer(InvocationOnMock invocation) throws Throwable {
        CharSequence text = (CharSequence) invocation.getArguments()[0];
        return text == null || text.length() == 0;
    }
}
