package com.lyh.xbiaoshop.dao;

import com.lyh.xbiaoshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * @Description user 数据访问接口
 * @date 2020-12-06 10:30:30
*/
public interface UserDao extends JpaRepository<User,Long>,
		JpaSpecificationExecutor<User>{
	
	
	@Query(value = "update  user set password=?1 where email=?2 and " +
			"user_name=?3",
			nativeQuery =
			true)
	@Modifying
	void updateByEmailAndUserName(String password,String email,String username);
	
	
	@Query(value = "update  user set pic=?2 where user_id=?1",nativeQuery = true)
	@Modifying
	void updatePicByUserId(Long userId,String picUrl);
}

