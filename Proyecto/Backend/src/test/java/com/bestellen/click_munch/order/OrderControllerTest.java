package com.bestellen.click_munch.order;

import com.bestellen.click_munch.menu.DessertService;
import com.bestellen.click_munch.menu.DrinkService;
import com.bestellen.click_munch.menu.PlateService;
import com.bestellen.click_munch.user.User;
import com.bestellen.click_munch.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private PlateService plateService;

    @Mock
    private DrinkService drinkService;

    @Mock
    private DessertService dessertService;

    @InjectMocks
    private OrderController orderController;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // Configura MockMvc con el controlador inyectado
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        // Inicializa el usuario de prueba con los parámetros esperados:
        testUser = new User(
                1,
                "testUser",
                "testuser@example.com",  // Email válido
                "password",
                "ROLE_USER",
                "SomeValue",             // Valor para el sexto parámetro
                new HashSet<>()          // Set<Order> vacío
        );
    }

    @Test
    public void testFindOrdersByUser() throws Exception {
        // Crea una orden de prueba
        Order testOrder = new Order(1, 1, 1, Set.of(), Set.of(), Set.of(), 10.0, Status.PENDING, Payment.CASH);

        // Define el comportamiento de los mocks
        when(userService.findByUsername("testUser")).thenReturn(testUser);
        when(orderService.findByUserId(1)).thenReturn(List.of(testOrder));

        // Realiza la petición GET y verifica la respuesta
        mockMvc.perform(get("/api/orders/testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void testFindAllOrders() throws Exception {
        Order testOrder = new Order(1, 1, 1, Set.of(), Set.of(), Set.of(), 20.0, Status.PENDING, Payment.CASH);
        when(orderService.findAll()).thenReturn(List.of(testOrder));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Ejemplo de JSON para crear una nueva orden
        String jsonRequest = """
                {
                    "userId": 1,
                    "storeId": 1,
                    "plateIds": [],
                    "drinkIds": [],
                    "dessertIds": [],
                    "total": 20.0,
                    "paymentMethod": "CASH"
                }
                """;


        when(userService.findById(1)).thenReturn(testUser);

        mockMvc.perform(post("/api/orders/new-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }
}
