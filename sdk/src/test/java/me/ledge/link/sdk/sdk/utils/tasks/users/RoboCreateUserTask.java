package me.ledge.link.sdk.sdk.utils.tasks.users;

import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.users.CreateUserTask;

/**
 * Custom {@link CreateUserTask} to make Robolectric testing of the aforementioned class easier.
 * @author Wijnand
 */
public class RoboCreateUserTask extends CreateUserTask {

    /**
     * @param requestData See {@link CreateUserTask#CreateUserTask}.
     * @param apiWrapper See {@link CreateUserTask#CreateUserTask}.
     * @param responseHandler See {@link CreateUserTask#CreateUserTask}.
     * @see CreateUserTask#CreateUserTask
     */
    public RoboCreateUserTask(CreateUserRequestVo requestData, LinkApiWrapper apiWrapper,
            ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /**
     * Fakes execution of the Task and returns the result.
     * @return Execution result.
     */
    public CreateUserResponseVo execute() {
        CreateUserResponseVo result = doInBackground();
        onPostExecute(result);

        return result;
    }

}
