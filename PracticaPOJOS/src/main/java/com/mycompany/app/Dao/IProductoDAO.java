/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.app.Dao;

import com.mycompany.Exception.DaoException;
import com.mycompany.Exception.EntityNotFoundException;
import com.mycompany.Model.Producto;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author daya
 */
public interface IProductoDAO {
    
    ObjectId create(Producto entity) throws DaoException;

    Optional<Producto> findById(ObjectId _id) throws DaoException;

    List<Producto> findAll(int limit) throws DaoException;

    boolean update(Producto entity) throws DaoException, EntityNotFoundException;

    boolean deleteById(ObjectId _id) throws DaoException, EntityNotFoundException;

    boolean deleteAll() throws DaoException;

    List<Producto> findByNombre(String nombre) throws DaoException;

    List<Producto> findByCategoria(String categoria) throws DaoException;
}
