package com.gfg.minor1.repository;

import com.gfg.minor1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    // 1st way of writing query, here mysql is running the query
    @Query(value = "select * from author where email =:email", nativeQuery = true) //native query means hibernate is not looking at your query
    Author getAuthor(String email);

    //2nd way of writing query which is take care by hibernate by using alias lika a or capital lettaer Authoe
    @Query("select a from Author a where a.email =:email")
    Author getAuthorWithoutNative(String email);

    // 3rd way of writing the query
    Author findByEmail(String email);  // we have to follow some(way of writing the method name) rules, hibernate will create my query


}
