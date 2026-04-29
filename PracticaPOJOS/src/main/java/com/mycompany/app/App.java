/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.app;

import com.mycompany.Dao.mongo.ProductoDAO;
import com.mycompany.Exception.DaoException;
import com.mycompany.Exception.EntityNotFoundException;
import com.mycompany.Model.Producto;
import com.mycompany.Model.Proveedor;
import com.mycompany.app.Dao.IProductoDAO;
import java.util.Arrays;
import org.bson.types.ObjectId;

/**
 *
 * @author daya
 */
public class App {

    public static void main(String[] args) throws DaoException {
       try{
           IProductoDAO dao = new ProductoDAO();
           
           Producto producto = new Producto();
           producto.setNombre("Telefono celular");
           producto.setPrecio(21000.00);
           producto.setStock(10);
           producto.setProveedor(new Proveedor ("Samsung","samsungMexico@hotmail.com","Mexico"));
           producto.setCategorias(Arrays.asList("Tecnologia", "Celulares"));
           
           ObjectId id= dao.create(producto);
           
           
           
        System.out.println("Producto creado con ID: " + id);

            Producto foundProduct = dao.findById(id).orElse(null);
            System.out.println("Producto encontrado: " + foundProduct.getNombre());

            foundProduct.setPrecio(13999.00);
            foundProduct.setStock(8);
            dao.update(foundProduct);
            System.out.println("Producto actualizado");

            System.out.println("Lista de productos:");
            dao.findAll(10).forEach(p -> 
                System.out.println(p.getNombre() + " - $" + p.getPrecio())
            );

            dao.deleteById(id);
            System.out.println("Producto eliminado");

        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
