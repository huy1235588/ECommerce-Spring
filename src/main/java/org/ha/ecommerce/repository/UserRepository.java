package org.ha.ecommerce.repository;

import org.ha.ecommerce.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel getUserByEmailIgnoreCase(String regex);
    UserModel getUserByUsername(String username);

    Optional<UserModel> findByEmail(String email);

}
