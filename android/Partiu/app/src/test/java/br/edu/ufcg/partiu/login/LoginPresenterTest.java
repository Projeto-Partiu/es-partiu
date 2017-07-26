package br.edu.ufcg.partiu.login;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.UserService;
import okhttp3.Headers;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * Created by lucas on 17/07/17.
 */
public class LoginPresenterTest {

    /**
     * Necessário para poder rodar o mockito junto com o JUnit
     */
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    LoginContract.LoginPresenter presenter;

    @Mock
    LoginContract.LoginView loginView;

    @Mock
    UserService userService;

    @Mock
    User userMock;

    @Before
    public void setUp() {
        presenter = new LoginPresenter(loginView, userService);
    }

    @Test
    public void onSuccessfulLogin() throws Exception {
        Mockito.when(userService.createUser(
                any(User.class), any(ServiceCallback.class)
        )).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                User user = invocation.getArgument(0);

                Response<User> response = Response.success(user);

                ((ServiceCallback<User>) invocation.getArgument(1)).onSuccess(
                        user, response
                );
                return null;
            }
        });

        presenter.onSuccessfulLogin(mock(JSONObject.class));

        Mockito.verifyZeroInteractions(loginView);
    }
}