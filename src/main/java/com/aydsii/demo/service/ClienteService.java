package com.aydsii.demo.service;

import com.aydsii.demo.dto.ClienteDTO;
import com.aydsii.demo.model.Cliente;
import com.aydsii.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    //Alta simple sin validaciones extras
    public ClienteDTO registrarClienteSimple(ClienteDTO dto){
        return guardarClienteBD(dto);
    }

    //Alta con validacion en la BD
    public ClienteDTO registrarClienteValidado(ClienteDTO dto){
        if(clienteRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("El email ya esta registrado");
        }

        return guardarClienteBD(dto);
    }

    private ClienteDTO guardarClienteBD(ClienteDTO dto){
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setFechaRegistro(LocalDateTime.now());

        //Delego al repository  la operacion de INSERT INTO
        Cliente clienteGuardado = clienteRepository.save(cliente);

        //Devolvemos el mismo dto pero con el id que genero la BD
        dto.setId(clienteGuardado.getId());
        return dto;
    }
}
