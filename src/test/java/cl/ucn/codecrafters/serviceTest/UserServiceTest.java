package cl.ucn.codecrafters.serviceTest;


import cl.ucn.codecrafters.user.Role;
import cl.ucn.codecrafters.user.User;
import cl.ucn.codecrafters.user.dto.AdministrativeDto;
import cl.ucn.codecrafters.user.dto.ClientDto;
import cl.ucn.codecrafters.user.IUserRepository;
import cl.ucn.codecrafters.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllUsers() throws Exception {
        // Simulando el repositorio
        List<User> userList = Collections.singletonList(new User());
        when(userRepository.findAll()).thenReturn(userList);

        // Llamando al servicio
        List<User> result = userService.findAllUsers();

        // Verificando el resultado
        assertEquals(userList, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindAllClients() {
        // Simulando el repositorio
        List<User> userList = Collections.singletonList(new User());
        when(userRepository.findByRole(Role.CLIENT)).thenReturn(userList);

        // Llamando al servicio
        List<ClientDto> result = userService.findAllClients();

        // Verificando el resultado
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(userRepository, times(1)).findByRole(Role.CLIENT);
    }

    @Test
    void testFindAllAdministratives() {
        // Simulando el repositorio
        List<User> userList = Collections.singletonList(new User());
        when(userRepository.findByRole(Role.ADMINISTRATIVE)).thenReturn(userList);

        // Llamando al servicio
        List<AdministrativeDto> result = userService.findAllAdministratives();

        // Verificando el resultado
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(userRepository, times(1)).findByRole(Role.ADMINISTRATIVE);
    }

    @Test
    void testFindUserById() throws Exception {
        // Simulando el repositorio
        Integer userId = 1;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Llamando al servicio
        User result = userService.findUserById(userId);

        // Verificando el resultado
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindUserDtoById() {
        // Simulando el repositorio
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setRole(Role.CLIENT);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Llamando al servicio
        ClientDto clientDto = userService.findUserDtoById(userId);

        // Verificando el resultado
        assertNotNull(clientDto);
        assertEquals(user.getDni(), clientDto.getDni());
        assertEquals(user.getFirstName(), clientDto.getFirstName());
        assertEquals(user.getLastName(), clientDto.getLastName());
        assertEquals(user.getNationality(), clientDto.getNationality());
        assertEquals(user.getPhone(), clientDto.getPhone());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testSaveClient() throws Exception {
        // Simulando el repositorio y encoder de contraseñas
        User userToSave = new User();
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(userToSave)).thenReturn(userToSave);

        // Llamando al servicio
        User result = userService.saveClient(userToSave);

        // Verificando el resultado
        assertNotNull(result);
        assertEquals(Role.CLIENT, result.getRole());
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void testSaveAdministrative() throws Exception {
        // Simulando el repositorio y encoder de contraseñas
        User userToSave = new User();
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(userToSave)).thenReturn(userToSave);

        // Llamando al servicio
        User result = userService.saveAdministrative(userToSave);

        // Verificando el resultado
        assertNotNull(result);
        assertEquals(Role.ADMINISTRATIVE, result.getRole());
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void testUpdate() throws Exception {
        // Simulando el repositorio
        Integer userId = 1;
        User userToUpdate = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        // Llamando al servicio
        User result = userService.update(userId, userToUpdate);

        // Verificando el resultado
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userToUpdate);
    }

    @Test
    void testDelete() throws Exception {
        // Simulando el repositorio
        Integer userId = 1;
        User userToDelete = new User();
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userToDelete));
        when(userRepository.save(userToDelete)).thenReturn(userToDelete);

        // Llamando al servicio
        boolean result = userService.delete(userId);

        // Verificando el resultado
        assertTrue(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userToDelete);
    }

}