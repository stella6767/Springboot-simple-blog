package com.cos.blog.domain.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findAllByOrderByIdDesc(Pageable pageable);
	
	@Query(value ="select p.* FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%",
			countQuery = "SELECT count(*) FROM Post p WHERE p.title like %:keyword% OR p.content LIKE %:keyword%", nativeQuery = true)
    Page<Post> findByKeyword(String keyword,Pageable pageable);
	
	@Query(value ="select p.* FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%", nativeQuery = true)
    List<Post> findByKeyword(String keyword);
	
	
	
    @Modifying
    @Query("update Post p SET p.count=p.count+1 where p.id = :id")
    int mUpateCount(int id);
	
}
