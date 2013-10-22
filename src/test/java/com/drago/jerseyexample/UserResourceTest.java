package com.drago.jerseyexample;

import com.drago.jerseyexample.model.User;
import com.drago.jerseyexample.payload.UserPayload;
import com.drago.jerseyexample.resource.UserResource;
import com.drago.jerseyexample.service.UserService;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 18/10/13
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {


    private UserResource mockUserResource;

    private UserService mockUserService;

    private UriInfo mockUriInfo;

    private static final Long USER_ID = new Long(1);

    @Before
    public void setUp(){
        mockUserResource = new UserResource();
        mockUserService = mock(UserService.class);
        mockUriInfo = mock(UriInfo.class);
        mockUserResource.setUserService(mockUserService);
        mockUserResource.setUriInfo(mockUriInfo);
    }

    @Test
    public void shouldCreateUser(){

        when(mockUserService.create(argThat(isUser(new User.Builder(USER_ID, "Juan", "Quintana").build())))).thenReturn(USER_ID);
        when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(UriBuilder.fromPath("http://localhost:8080/services/webapi/users"));

        Response response = mockUserResource.createUser(new UserPayload.Builder("Juan", "Quintana")
                .withPhonePrefix("+44")
                .withPhone("7716773413")
                .build());

        assertThat(response.getLocation().toString(), is("http://localhost:8080/services/webapi/users/1"));
        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));

    }


    @Test
    public void shouldUpdateUser(){

        User user = new User.Builder(new Long(1), "Pepe", "Sanchez").build();

        UserPayload userPayload = user.toPayload();

        when(mockUserService.get(argThat(isLong(USER_ID)))).thenReturn(new User.Builder(USER_ID, "Juan", "Quintana").build());

        when(mockUserService.update(argThat(isUser(user)))).thenReturn(user);

        Response response = mockUserResource.updateUser(USER_ID, userPayload);

        assertThat(response.getStatus(), is(HttpStatus.NOT_MODIFIED.value()));

    }

    @Test
    public void shouldGetAll(){

        when(mockUserService.getAll()).thenReturn(new ArrayList<User>());

        Response response = mockUserResource.getAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

        List<UserPayload> list = (List<UserPayload>) response.getEntity();

        assertThat(list.size(), is(0));

    }

    @Test
    public void shouldGetUser(){

        when(mockUserService.get(argThat(isLong(USER_ID)))).thenReturn(new User.Builder(USER_ID, "Juan", "Quintana").build());

        Response response = mockUserResource.getUser(USER_ID);

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

        UserPayload userPayload = (UserPayload) response.getEntity();

        assertThat(userPayload.getFirstName(), is("Juan"));

    }

    @Test
    public void shouldDeleteUser(){

        when(mockUserService.get(argThat(isLong(USER_ID)))).thenReturn(new User.Builder(USER_ID, "Juan", "Quintana").build());

        Response response = mockUserResource.deleteUser(USER_ID);

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));

    }

    @Test
    public void shouldReturnNotFoundWhenGettingAnUserThatDoesNotExist(){

        when(mockUserService.get(argThat(isLong(USER_ID)))).thenReturn(null);

        Response response = mockUserResource.getUser(USER_ID);

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));

        UserPayload userPayload = (UserPayload) response.getEntity();

        assertThat(userPayload, is(nullValue()));

    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingAnUserThatDoesNotExist(){

        when(mockUserService.get(argThat(isLong(USER_ID)))).thenReturn(null);

        Response response = mockUserResource.updateUser(USER_ID, new UserPayload.Builder("Juan", "Quintana").build());

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));

    }


    @Test
    public void shouldReturnNotFoundWhenDeletingAnUserThatDoesNotExist(){

        when(mockUserService.get(argThat(isLong(USER_ID)))).thenReturn(null);

        Response response = mockUserResource.deleteUser(USER_ID);

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));

    }


    private TypeSafeMatcher<Long> isLong(final Long userId) {
        return new TypeSafeMatcher<Long>() {
            @Override
            protected boolean matchesSafely(Long aLong) {
                return aLong.equals(userId);
            }

            @Override
            public void describeTo(Description description) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

    private TypeSafeMatcher<User> isUser(final User user1) {
        return new TypeSafeMatcher<User>() {
            @Override
            protected boolean matchesSafely(User user) {
                return user1.getFirstName().equals("Juan") || user1.getId().equals(USER_ID);
            }

            @Override
            public void describeTo(Description description) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

}
