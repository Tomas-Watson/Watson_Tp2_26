package com.aydsii.demo.controller;

import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.dto.ClienteDTO;
import com.aydsii.demo.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteDTO>> altaSimple(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.registrarClienteSimple(clienteDTO);
        
        ApiResponse<ClienteDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(), 
                "Cliente registrado con éxito (Alta Simple)", 
                nuevoCliente
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/validado")
    public ResponseEntity<ApiResponse<ClienteDTO>> altaValidada(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.registrarClienteValidado(clienteDTO);
        
        ApiResponse<ClienteDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(), 
                "Cliente registrado con éxito (Alta Validada)", 
                nuevoCliente
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
