package com.mcp.server.service;

import com.mcp.server.dto.McpCallResponse;
import com.mcp.server.model.Customer;
import com.mcp.server.model.Order;
import com.mcp.server.model.Product;
import com.mcp.server.repository.CustomerRepository;
import com.mcp.server.repository.OrderRepository;
import com.mcp.server.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class McpService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public McpCallResponse buscarClientePorId(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return McpCallResponse.success(customer.get());
        } else {
            return McpCallResponse.failure("Cliente con ID " + id + " no encontrado.");
        }
    }

    public McpCallResponse listarProductosDisponibles(String category) {
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productRepository.findByStockGreaterThanAndCategoryContainingIgnoreCase(0, category);
        } else {
            products = productRepository.findByStockGreaterThan(0);
        }
        return McpCallResponse.success(products);
    }

    public McpCallResponse consultarPedidosPorCliente(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return McpCallResponse.failure("No se encontraron pedidos para el cliente con ID " + customerId + ".");
        } else {
            return McpCallResponse.success(orders);
        }
    }
}
