package com.bidsphere.bidsphere.repositories;

import com.bidsphere.bidsphere.entities.Users;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface UsersRepository extends CrudRepository<Users, UUID> {
}
