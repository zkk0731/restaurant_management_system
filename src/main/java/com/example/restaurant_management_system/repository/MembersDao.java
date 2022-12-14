package com.example.restaurant_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restaurant_management_system.entity.Members;
import com.example.restaurant_management_system.vo.MemberInfo;

@Repository
public interface MembersDao extends JpaRepository<Members, Integer> {

	public Members findByMemberAccount(String memberAccount);
	
	public Members findByMemberAccountAndPwd(String memberAccount, String pwd);

}
