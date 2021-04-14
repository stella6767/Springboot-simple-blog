package com.cos.blog.domain.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	@Query(value ="select * FROM reply WHERE content LIKE %:content%", nativeQuery = true)
    List<Reply> findByContent(String content);
	
	
}
