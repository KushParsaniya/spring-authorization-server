package dev.kush.authorizationserver.service;

import dev.kush.authorizationserver.models.ResponseDto;
import dev.kush.authorizationserver.models.clients.dtos.CreateClientDto;

public interface ClientService {

    ResponseDto saveClient(CreateClientDto createClientDto);
}
