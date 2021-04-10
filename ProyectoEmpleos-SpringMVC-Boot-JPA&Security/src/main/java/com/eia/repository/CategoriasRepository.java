/**
 
 JPARepository<T, ID>
 
  @param <T> the domain type the repository manages
  @param <ID> the type of the id of the entity the repository manages
  
  implementa la intefaz PagingAndSortingRepository que a su vez implementa la intefaz CrudRepository
 
 * */

package com.eia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.eia.model.Categoria;

//public interface CategoriasRepository extends CrudRepository<Categoria, Integer>

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {

}
