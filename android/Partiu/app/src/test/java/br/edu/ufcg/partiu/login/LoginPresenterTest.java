package br.edu.ufcg.partiu.login;

import com.android.volley.NetworkResponse;

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

import static org.junit.Assert.*;

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
    }

}