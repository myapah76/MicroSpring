package com.microservice.IdentityService.Infrastucture.Persistences.Repositories;

import com.microservice.IdentityService.Application.Persistences.Repositories.OutboxRepository;
import com.microservice.IdentityService.Domain.Entities.OutboxMessage;
import com.microservice.IdentityService.Infrastucture.Persistences.JpaRepositories.OutBoxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutBoxRepositoryImpl implements OutboxRepository {
    private final OutBoxJpaRepository outBoxJpaRepository;

    @Override
    public List<OutboxMessage> findByIsProcessedFalse() {
        return outBoxJpaRepository.findByIsProcessedFalse();
    }

    @Override
    public void saveAll(List<OutboxMessage> messages) {
        outBoxJpaRepository.saveAll(messages);
    }

    @Override
    public void save(OutboxMessage message) {
        outBoxJpaRepository.save(message);
    }
}
