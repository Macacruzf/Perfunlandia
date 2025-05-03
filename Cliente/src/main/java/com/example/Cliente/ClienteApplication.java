package com.example.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClienteApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
	}
	private ClienteRepository clienteRepository;

    //funcion para obtener todos los pacientes
    public List<Cliente> listarCliente(){
        return clienteRepository.findAll();
    }

    //funcion para obtener un paciente mediante su id
    public Cliente buscarClientePorId(long id){
        return clienteRepository.findById(id).get();
    }

    //funcion para guardar un cliente
    public Cliente saveCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }


	public void borrarCliente(long id){  //nunca es bueno borrar para tener historial, no?
        clienteRepository.deleteById(id);
    }
}

