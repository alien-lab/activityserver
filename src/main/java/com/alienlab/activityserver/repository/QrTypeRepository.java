package com.alienlab.activityserver.repository;

import com.alienlab.activityserver.domain.QrType;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the QrType entity.
 */
@SuppressWarnings("unused")
public interface QrTypeRepository extends MongoRepository<QrType,String> {

}
