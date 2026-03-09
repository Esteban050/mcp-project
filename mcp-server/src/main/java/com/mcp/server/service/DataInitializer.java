package com.mcp.server.service;

import com.mcp.server.model.Customer;
import com.mcp.server.model.Order;
import com.mcp.server.model.Product;
import com.mcp.server.repository.CustomerRepository;
import com.mcp.server.repository.OrderRepository;
import com.mcp.server.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        // Clientes
        Customer c1 = customerRepository.save(new Customer(null, "Juan Perez", "juan.perez@email.com", LocalDate.of(2023, 1, 15)));
        Customer c2 = customerRepository.save(new Customer(null, "Maria Garcia", "maria.garcia@email.com", LocalDate.of(2023, 2, 20)));
        customerRepository.save(new Customer(null, "Carlos Lopez", "carlos.lopez@email.com", LocalDate.of(2023, 3, 10)));

        // Productos
        productRepository.save(new Product(null, "Laptop Pro", "Electronica", 1200.0, 10));
        productRepository.save(new Product(null, "Smartphone X", "Electronica", 800.0, 25));
        productRepository.save(new Product(null, "Silla Ergonomica", "Oficina", 250.0, 15));
        productRepository.save(new Product(null, "Monitor 4K", "Electronica", 450.0, 0));
        productRepository.save(new Product(null, "Teclado Mecanico", "Accesorios", 100.0, 50));

        // Pedidos
        orderRepository.save(new Order(null, c1.getId(), LocalDateTime.of(2023, 5, 1, 10, 30), "ENTREGADO", 1200.0));
        orderRepository.save(new Order(null, c1.getId(), LocalDateTime.of(2023, 6, 15, 14, 45), "PROCESANDO", 100.0));
        orderRepository.save(new Order(null, c2.getId(), LocalDateTime.of(2023, 7, 20, 9, 15), "ENTREGADO", 800.0));

        System.out.println("--- Datos iniciales cargados en H2 correctamente ---");
    }
}
