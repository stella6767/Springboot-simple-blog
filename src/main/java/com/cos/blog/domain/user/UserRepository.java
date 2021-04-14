package com.cos.blog.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);  //이건 옵셔널 안 걸거임 
	
	 @Query(value =" select * from user where email like %?1%", nativeQuery = true)
	 List<User> mFindByEmail(String email);
}
