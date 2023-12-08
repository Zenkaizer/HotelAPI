package cl.ucn.codecrafters.controllerTest;

import cl.ucn.codecrafters.user.infraestructure.ClientController;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.dtos.ClientDto;
import cl.ucn.codecrafters.user.domain.UserError;
import cl.ucn.codecrafters.user.application.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClients() {
        // Simulando el servicio
        List<ClientDto> userList = Collections.singletonList(new ClientDto());
        when(userService.findAllClients()).thenReturn(userList);


        // Llamando al controlador
        ResponseEntity<?> responseEntity = clientController.getAllClients();

        // Verificando el resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userList, responseEntity.getBody());

        // Verificando que se llamó al método del servicio
        verify(userService, times(1)).findAllClients();
    }

    @Test
    void testGetOneClient() {
        // Simulando el servicio
        Integer userId = 1;
        ClientDto clientDto = new ClientDto();
        when(userService.findUserDtoById(userId)).thenReturn(clientDto);

        // Llamando al controlador
        ResponseEntity<?> responseEntity = clientController.getOneClient(userId);

        // Verificando el resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientDto, responseEntity.getBody());

        // Verificando que se llamó al método del servicio
        verify(userService, times(1)).findUserDtoById(userId);
    }

    @Test
    void testSave() throws Exception {
        // Simulando el servicio
        User userToSave = new User();
        when(userService.validateUserErrors(userToSave)).thenReturn(new UserError());

        // Llamando al controlador
        ResponseEntity<?> responseEntity = clientController.save(userToSave);

        // Verificando el resultado
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assert(responseEntity.getBody() instanceof String);

        // Verificando que se llamó al método del servicio
        verify(userService, times(1)).validateUserErrors(userToSave);
        verify(userService, never()).saveClient(userToSave);
    }

    @Test
    void testUpdate() throws Exception {
        // Simulando el servicio
        Integer userId = 1;
        User userToUpdate = new User();
        when(userService.validateUserErrors(userToUpdate)).thenReturn(new UserError());

        // Llamando al controlador
        ResponseEntity<?> responseEntity = clientController.updateClient(userId, userToUpdate);

        // Verificando el resultado
        assertEquals(HttpStatus.BAD_REQUEST , responseEntity.getStatusCode());
        assert(responseEntity.getBody() instanceof String);

        // Verificando que se llamó al método del servicio
        verify(userService, times(1)).validateUserErrors(userToUpdate);
        verify(userService, never()).update(userId, userToUpdate);
    }

    @Test
    void testDelete() throws Exception {
        // Simulando el servicio
        Integer userId = 1;
        when(userService.delete(userId)).thenReturn(true);

        // Llamando al controlador
        ResponseEntity<?> responseEntity = clientController.delete(userId);

        // Verificando el resultado
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verificando que se llamó al método del servicio
        verify(userService, times(1)).delete(userId);
    }


}