package net.sxinfo.core.test.manager;

import org.springframework.transaction.annotation.Transactional;

import net.sxinfo.core.spring25.Manager;
import net.sxinfo.core.test.entity.TestUser;

@Transactional
public interface TestUserManager extends Manager<TestUser, String>{

}
