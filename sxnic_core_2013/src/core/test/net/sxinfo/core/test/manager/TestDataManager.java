package net.sxinfo.core.test.manager;

import net.sxinfo.core.spring25.Manager;
import net.sxinfo.core.test.entity.TestData;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TestDataManager extends Manager<TestData, String> {

}
