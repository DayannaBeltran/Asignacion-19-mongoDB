/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.Config.MongoClientProvider;
import com.mycompany.Exception.DaoException;
import com.mycompany.Exception.EntityNotFoundException;
import com.mycompany.Model.Producto;
import com.mycompany.app.Dao.IProductoDAO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author daya
 */
public class ProductoDAO implements IProductoDAO{
    
      private final MongoCollection<Producto> col;

    public ProductoDAO() {
        this.col = MongoClientProvider.INSTANCE.getCollection("productos", Producto.class);
    }

    @Override
    public ObjectId create(Producto entity) throws DaoException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }

            Instant ahora = Instant.now();
            entity.setCreatedAt(ahora);
            entity.setUpdatedAt(ahora);

            col.insertOne(entity);
            return entity.getId();

        } catch (MongoException e) {
            throw new DaoException("No se logró insertar el producto", e);
        }
    }

    @Override
    public Optional<Producto> findById(ObjectId _id) throws DaoException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new DaoException("No se logró buscar el producto", e);
        }
    }

    @Override
    public List<Producto> findAll(int limit) throws DaoException {
        try {
            return col.find().limit(limit).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new DaoException("No se logró listar los productos", e);
        }
    }
      @Override
    public boolean update(Producto entity) throws DaoException, EntityNotFoundException {
        try {
            if (entity.getId() == null) {
                throw new EntityNotFoundException("El producto no tiene ID");
            }

            Instant ahora = Instant.now();
            entity.setUpdatedAt(ahora);

            var result = col.updateOne(
                    Filters.eq("_id", entity.getId()),
                    Updates.combine(
                            Updates.set("nombre", entity.getNombre()),
                            Updates.set("precio", entity.getPrecio()),
                            Updates.set("stock", entity.getStock()),
                            Updates.set("proveedor", entity.getProveedor()),
                            Updates.set("categorias", entity.getCategorias()),
                            Updates.set("updatedAt", entity.getUpdatedAt())
                    )
            );

            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("No se encontró el producto para actualizar");
            }

            return result.getModifiedCount() > 0;

        } catch (MongoException e) {
            throw new DaoException("No se logró actualizar el producto", e);
        }
    }
     @Override
    public boolean deleteById(ObjectId _id) throws DaoException, EntityNotFoundException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));

            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("No se encontró el producto para eliminar");
            }

            return true;

        } catch (MongoException e) {
            throw new DaoException("No se logró eliminar el producto", e);
        }
    }

    @Override
    public boolean deleteAll() throws DaoException {
        try {
            col.deleteMany(Filters.empty());
            return true;
        } catch (MongoException e) {
            throw new DaoException("No se lograron eliminar los productos", e);
        }
    }

    @Override
    public List<Producto> findByNombre(String nombre) throws DaoException {
        try {
            return col.find(Filters.eq("nombre", nombre)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new DaoException("No se logró buscar por nombre", e);
        }
    }
      @Override
    public List<Producto> findByCategoria(String categoria) throws DaoException {
        try {
            return col.find(Filters.eq("categorias", categoria)).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new DaoException("No se logró buscar por categoría", e);
        }
    }
}