package com.example.security.models.DAO;

import com.example.security.models.MongoDB;
import com.example.security.models.entity.BaseEntity;
import org.bson.types.ObjectId;

import org.mongodb.morphia.dao.BasicDAO;


/**
 * The Class BaseDAO.
 *
 * @param <T> the generic type
 */

public abstract class BaseDAO<T extends BaseEntity> extends BasicDAO<T, ObjectId> {


    /**
     * The Constant CLASS_NAME.
     */

    static final String CLASS_NAME = "className";


    /**
     * Instantiates a new base dao.
     *
     * @param entityClass the entity class
     */

    public BaseDAO(Class<T> entityClass) {

        super(entityClass, MongoDB.instance().getDatabase());

    }


}