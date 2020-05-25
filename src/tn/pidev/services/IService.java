/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.pidev.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ghada
 */
public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void delete(int id) throws SQLException;

    void update(T t, int id) throws SQLException;

    List<T> readAll() throws SQLException;

    T readById(int id) throws SQLException;
    List<T> readTrier()throws SQLException;
    

}
